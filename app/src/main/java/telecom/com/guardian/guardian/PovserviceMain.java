package telecom.com.guardian.guardian;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shayvidas on 24/07/2017.
 */

public class PovserviceMain extends Service {

    private static Timer timer = new Timer();
    int SERVICE_TIMER_DELEY = 10;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, R.string.service_service_started, Toast.LENGTH_SHORT).show();

        timer = new Timer();
        timer.scheduleAtFixedRate(new PovServiceMainTimerClass(), 0, 1000*SERVICE_TIMER_DELEY);

        return START_STICKY;
    }

    void takeScreenshot () {


    }


    @Override
    public void onDestroy() {

        super.onDestroy();

        //try to cancel the timer
        try {
            timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendBroadcast(new Intent("serviceKillerBlockedLol"));

        Toast.makeText(this, R.string.service_service_stopped, Toast.LENGTH_SHORT).show();
    }

    private class PovServiceMainTimerClass extends TimerTask
    {
        public void run() {
            toastHandler.sendEmptyMessage(0);
        }

    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Toast.makeText(getApplicationContext(), R.string.service_service_timer_messages, Toast.LENGTH_SHORT).show();
        }
    };
}
