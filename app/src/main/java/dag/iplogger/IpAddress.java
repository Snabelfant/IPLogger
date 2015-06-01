package dag.iplogger;

/**
 * Created by Dag on 15.12.2014.
 */
public class IpAddress {
    private String ipNo;
    private String dnsName;

    @Override
    public String toString() {
        return ipNo + "/" + dnsName;
    }

    public IpAddress(String ipNo, String dnsName) {
        this.ipNo = ipNo;
        this.dnsName = dnsName;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && toString().equals(o.toString());
    }


}
