package telecom.com.guardian.guardian;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by shayvidas on 26/07/2017.
 */

public class RestartServiceReceiver extends BroadcastReceiver
{

    private static final String TAG = "RestartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "restarting the service entered in the reciever");
        context.startService(new Intent(context.getApplicationContext(), PovserviceMain.class));

    }

}

