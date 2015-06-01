package dag.iplogger;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * l
 * Created by Dag on 17.12.2014.
 */
public class FileLogger {

    private File logFile;

    public FileLogger(Context context, String fileName) {
        if (context != null) {
            logFile = new File(context.getExternalFilesDir(null), fileName);
        } else {
            logFile = new File("C:\\temp\\iplogg\\" + fileName);
        }
    }

    private String now() {
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return format.format(Calendar.getInstance().getTime());
    }

    public void log(String s) throws IOException {
        FileWriter log = null;
        try {
            log = new FileWriter(logFile, true);
            log.append(now() + ": " + s + "\n");
        } catch (IOException e) {
            throw new RuntimeException("skriv: ", e);
        } finally {
            if (log != null) {
                log.close();
            }
        }

    }

    public static void  log(Context context, String s ) {
        try {
            new FileLogger(context, "ip.log").log(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
