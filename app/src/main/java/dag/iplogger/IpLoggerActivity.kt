package dag.iplogger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class IpLoggerActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.iploggeractivity)
        val intent = Intent(this, IpLoggerService::class.java)
        startService(intent)
        Toast.makeText(baseContext, "Ferdig", Toast.LENGTH_LONG).show()

    }

    //    @Override
    //    protected void onDestroy() {
    //        FileLogger.log(this,"onDestroy");
    //        Log.i("ZZZ", "onDestroy");
    //        super.onDestroy();
    //    }
    //
    //    @Override
    //    protected void onStop() {
    //        Log.i("ZZZ", "onStop");
    //        FileLogger.log(this,"onStop");
    //        super.onStop();
    //    }
    //
    //    @Override
    //    protected void onRestart() {
    //        Log.i("ZZZ", "onRestart");
    //        FileLogger.log(this,"onRestart");
    //        super.onRestart();
    //    }
    //
    //    @Override
    //    protected void onStart() {
    //        Log.i("ZZZ", "onStart");
    //        FileLogger.log(this,"onStart");
    //        super.onStart();
    //    }
    //
    //    @Override
    //    protected void onResume() {
    //        Log.i("ZZZ", "onResume");
    //        FileLogger.log(this,"onResume");
    //
    //        super.onResume();
    //    }
    //
    //    @Override
    //    protected void onPause() {
    //        Log.i("ZZZ", "onPause");
    //        FileLogger.log(this,"onPause");
    //        super.onPause();
    //    }
}