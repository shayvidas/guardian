package telecom.com.guardian.myapplication;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class FloatingFaceBubbleService extends Service {
    private WindowManager windowManager;
    private ImageView floatingFaceBubble;
    public void onCreate() {
        super.onCreate();
        floatingFaceBubble = new ImageView(this);
        //a face floating bubble as imageView
        floatingFaceBubble.setImageResource(R.drawable.ic_android_black_24dp);

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        //here is all the science of param
        final LayoutParams myParams = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        myParams.gravity = Gravity.TOP | Gravity.LEFT;
        myParams.x=0;
        myParams.y=100;
        // add a floatingfacebubble icon in window
        windowManager.addView(floatingFaceBubble, myParams);
        try{
            //for moving the picture on touch and slide
            floatingFaceBubble.setOnTouchListener(new View.OnTouchListener() {
                WindowManager.LayoutParams paramsT = myParams;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private long touchStartTime = 0;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //remove face bubble on long press

                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            touchStartTime = System.currentTimeMillis();
                            initialX = myParams.x;
                            initialY = myParams.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d("touchup", "onTouch: touch up detected");

                            if(System.currentTimeMillis()-touchStartTime> ViewConfiguration.getLongPressTimeout()){
                                windowManager.removeView(floatingFaceBubble);
                                stopSelf();
                                return false;
                            } else {



                                Date now = new Date();
                                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                                try {
                                    // image naming and path  to include sd card  appending name you choose for file
                                    String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

                                    // image naming and path  to include sd card  appending name you choose for file
                                    String mPathNew = Environment.getExternalStorageDirectory().toString() + "/" + "shay" + ".jpg";

                                    // create bitmap screen capture
                                    View v1 = floatingFaceBubble.getRootView();

                                    v1.setDrawingCacheEnabled(true);
                                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                                    v1.setDrawingCacheEnabled(false);


                                    File imageFile = new File(mPathNew);


                                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                                    int quality = 100;
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                                    outputStream.flush();
                                    outputStream.close();

//                                    openScreenshot(imageFile);
                                } catch (Throwable e) {
                                    // Several error may come out with file handling or OOM
                                    e.printStackTrace();
                                }


                            }


                            break;
                        case MotionEvent.ACTION_MOVE:
                            myParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                            myParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(v, myParams);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}