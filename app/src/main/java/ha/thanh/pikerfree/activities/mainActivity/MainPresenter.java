package ha.thanh.pikerfree.activities.mainActivity;

import android.content.Context;
import android.content.SharedPreferences;


import ha.thanh.pikerfree.constants.Constants;

import static android.content.Context.MODE_PRIVATE;


public class MainPresenter {
    private Context context;
    private SharedPreferences sharedPreferences;

    public MainPresenter(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.CONFIG, MODE_PRIVATE);
    }

    protected void saveConfig() {

    }

}
