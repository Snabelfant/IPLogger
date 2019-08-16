package dag.iplogger

/**
 * Created by Dag on 15.12.2014.
 */
data class IpAddress(private val ipNo: String, private val dnsName: String) {

    override fun toString(): String {
        return "$ipNo/$dnsName"
    }

    override fun equals(o: Any?): Boolean {
        return o != null && toString() == o.toString()
    }


}
