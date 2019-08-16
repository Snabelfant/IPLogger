package dag.iplogger

import dag.podkast.util.UrlDownloader
import org.jsoup.Jsoup
import java.io.IOException

class MinIpIpSource : IpSource {
    override val name: String
        get() = "minip.no"

    @Throws(IOException::class)
    override fun findIp(): IpAddress {
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
}
class IplocationIpSource : IpSource {
    override fun findIp(): IpAddress {
        val content = UrlDownloader.download("https://www.iplocation.net/find-ip-address")
        val document = Jsoup.parse(content)

        val ipTableElements = document.getElementsByTag("table").filter { it.hasClass("iptable") }
        check(ipTableElements.size==1, { "${ipTableElements.size} iptable"})
        val table = ipTableElements[0]
        val trs = table.getElementsByTag("tr")
        val ipAddress = trs[0].getElementsByTag("td")[0].text()
        val host = trs[2].getElementsByTag("td")[0].text()
        return IpAddress(ipAddress,host)
    }

    override val name: String
        get() = "iplocation.net"

}
private fun check(condition: Boolean, pos: Int) {
    if (!condition) {
        throw RuntimeException(pos.toString())
    }
}

