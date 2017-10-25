package ha.thanh.pikerfree.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.constants.DummyData;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;

/**
 * Created by HaVan on 8/27/2017.
 */

public class HomeModel {
    private SharedPreferences sharedPreferences;
    private Context mCon;

    HomeModel(Context context) {
        this.mCon = context;
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    String getUserNameStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_NAME, "");
    }

    String getUserAddressStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ADDRESS, "");

    }

    String getLocalImageStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_PROFILE_PIC_PATH, "");
    }

    String getUserIdFromSharePf(){
        return sharedPreferences.getString(Constants.USER_ID, "");
    }
}
