package dag.iplogger

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log
import java.io.File

import java.io.IOException


class IpLoggerService : Service() {


    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Logger.info("service onStartCommand")
        val task = IpLoggerTask()
        task.execute()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        Logger.info("service onDestroy")
        super.onDestroy()
    }

    private inner class IpLoggerTask : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            val logFile = File(applicationContext.getExternalFilesDir(null), "ip.log")
            val ipFinder = IpFinder(logFile)
            try {
                ipFinder.loopAndFind()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }
    }
}
