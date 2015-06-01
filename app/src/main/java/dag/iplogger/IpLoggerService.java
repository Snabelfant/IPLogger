package dag.iplogger;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;


public class IpLoggerService extends IntentService {


    public IpLoggerService() {
        super("IpLoggerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        IpLoggerTask task = new IpLoggerTask();
        task.execute();
    }

    private class IpLoggerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            IpFinder ipFinder = new IpFinder(getApplicationContext());
            try {
                ipFinder.loopAndFind();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
