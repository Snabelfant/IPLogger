package dag.iplogger

interface IpSource {
    fun findIp(): IpAddress
    val name: String
}