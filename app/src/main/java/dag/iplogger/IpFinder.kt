package dag.iplogger

import java.io.File
import java.io.IOException

private const val DELAY = 1 * 60 * 1000

class IpFinder(logFile: File) {

    val fileLogger = FileLogger(logFile)
//    val ipSources: List<IpSource> = listOf(MinIpIpSource(), IplocationIpSource())
    val ipSources: List<IpSource> = listOf(IplocationIpSource())

    fun loopAndFind() {
        var lastIp: IpAddress? = null
        var count = 0
        while (true) {
            try {
                val ipSource = ipSources.random()
                val ipAddress = ipSource.findIp()
                if (ipAddress != lastIp) {
                    fileLogger.log(ipAddress.toString())
                    Logger.info("Ip=$ipAddress (${ipSource.name})")
                } else {
//            /        if (count % 20 == 0) {
                    fileLogger.log("$ipAddress (${ipSource.name})(R)")
                    Logger.info("Ip=$ipAddress (${ipSource.name})(R)")
//                    }
                }

                lastIp = ipAddress
                count++

            } catch (e: IOException) {
                fileLogger.log(e.toString())
                Logger.error(e.toString())

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

        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val ipFinder = IpFinder(File("C:\\temp\\ip.log"))
            ipFinder.loopAndFind()
        }
    }
}
