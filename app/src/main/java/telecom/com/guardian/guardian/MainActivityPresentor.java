package telecom.com.guardian.guardian;

/**
 * Created by shayvidas on 23/07/2017.
 */

public class MainActivityPresentor {

    //input functions
    public void onSeekBarChange(MainActivityInterface interfaceClass, int i) {
        updateSeekBarText(interfaceClass, String.valueOf(i));
    }

    public void onFabButtonPressed(MainActivity mainActivity) {
        startPovService(mainActivity);


    }

    public void picGalleryBtnClicked() {
    }

    //output functions
    private void updateSeekBarText(MainActivityInterface interfaceClass, String s) {
        interfaceClass.updateScrollText(s);

    }

    private void startPovService(MainActivity mainActivity) {
        mainActivity.startService();

    }


}
