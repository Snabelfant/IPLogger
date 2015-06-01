package dag.iplogger;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Dag on 15.12.2014.
 */
public class IpFinder {
//    private static final int DELAY = 10 * 60 * 1000;
    private static final int DELAY = 1 * 60 * 1000;
    //    private static final int DELAY = 7 * 1000;
    private Context context;

    public IpFinder(Context context) {
        this.context = context;
    }

    public dag.iplogger.IpAddress find() throws IOException {
        String content = readUrl("http://minip.no");
        Document document = Jsoup.parse(content);
        Elements tbody = document.select("tbody");
        check(tbody.size() == 1, 1);
        Elements trs = tbody.get(0).select("tr");
        check(trs.size() == 2, 2);
        Elements tdsIpAddress = trs.get(0).select("td");
        check(tdsIpAddress.size() == 2, 3);
        Elements tdsDnsname = trs.get(1).select("td");
        check(tdsDnsname.size() == 2, 4);
        check(tdsIpAddress.get(1) != null, 5);
        check(tdsDnsname.get(1) != null, 6);
        return new dag.iplogger.IpAddress(tdsIpAddress.get(1).text(), tdsDnsname.get(1).text());
    }

    private void check(boolean condition, int pos) {
        if (!condition) {
            throw new RuntimeException(String.valueOf(pos));
        }
    }

    private String readUrl(String urlString) throws IOException {
        URL url = new URL(urlString);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openConnection().getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

    public void loopAndFind() throws IOException {
        FileLogger logger = new FileLogger(context, "ip.log");

        IpAddress lastIp = null;
        int count = 0;
        while (true) {
            try {
                IpAddress ipAddress = find();
//                if (!ipAddress.equals(lastIp)) {
                    logger.log(ipAddress.toString());
//                    Log.i("ZZZ", ipAddress.toString());
//                } else {
//                    if (count % 20 == 0) {
//                        logger.log(ipAddress.toString() + " (R)");
//                        Log.i("ZZZ", ipAddress.toString() + " (R)");
//                    }
//                }

                lastIp = ipAddress;
                count++;

            } catch (IOException e) {
                logger.log(e.toString());
                lastIp = null;
            } catch (Exception e) {
                logger.log("Krasj: " + e.toString());
                return;
            }

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                logger.log(e.toString());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Context context = null;
        IpFinder ipFinder = new IpFinder(context);
        ipFinder.loopAndFind();
    }
}
