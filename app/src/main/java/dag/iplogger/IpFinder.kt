package dag.iplogger

import android.content.Context
import dag.podkast.util.UrlDownloader
import org.jsoup.Jsoup
import java.io.File
import java.io.IOException

class IpFinder(logFile : File) {

    val fileLogger = FileLogger(logFile)
        init {
            if (context != null) {
                logFile = File(context.getExternalFilesDir(null), fileName)
            } else {
                logFile = File("C:\\temp\\iplogg\\$fileName")
            }
        }


        @Throws(IOException::class)
        fun find(): IpAddress {
            val content = UrlDownloader.download("http://minip.no")
            val document = Jsoup.parse(content)
            val tbody = document.select("tbody")
            check(tbody.size == 1, 1)
            val trs = tbody[0].select("tr")
            check(trs.size == 2, 2)
            val tdsIpAddress = trs[0].select("td")
            check(tdsIpAddress.size == 2, 3)
            val tdsDnsname = trs[1].select("td")
            check(tdsDnsname.size == 2, 4)
            check(tdsIpAddress[1] != null, 5)
            check(tdsDnsname[1] != null, 6)
            return IpAddress(tdsIpAddress[1].text(), tdsDnsname[1].text())
        }

        private fun check(condition: Boolean, pos: Int) {
            if (!condition) {
                throw RuntimeException(pos.toString())
            }
        }

        fun loopAndFind() {
            var lastIp: IpAddress? = null
            var count = 0
            while (true) {
                try {
                    val ipAddress = find()
                    if (ipAddress != lastIp) {
                        fileLogger.log(ipAddress.toString())
                        Logger.info(ipAddress.toString())
                    } else {
                        if (count % 20 == 0) {
                            fileLogger.log("$ipAddress (R)")
                            Logger.info("$ipAddress (R)")
                        }
                    }

                    lastIp = ipAddress
                    count++

                } catch (e: IOException) {
                    fileLogger.log(e.toString())
                    lastIp = null
                } catch (e: Exception) {
                    fileLogger.log("Krasj: $e")
                    return
                }

                try {
                    Thread.sleep(DELAY.toLong())
                } catch (e: InterruptedException) {
                    fileLogger.log(e.toString())
                }
            }
        }

        companion object {
        private val DELAY = 1 * 60 * 1000

        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val ipFinder = IpFinder(File("C:\\temp\\ip.log"))
            ipFinder.loopAndFind()
        }
    }
    }
