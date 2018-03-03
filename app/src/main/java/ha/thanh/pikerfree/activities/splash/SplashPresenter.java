package ha.thanh.pikerfree.activities.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.constants.Globals;


/**
 * Created by HaVan on 5/24/2017.
 */

class SplashPresenter implements SplashInterface.RequiredPresenterOps {
    private SplashInterface.RequiredViewOps mView;
    private SplashModel mModel;
    private Context context;

    SplashPresenter(Context context, SplashInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.context = context;
        mModel = new SplashModel(context, this);
        checkNetwork();
    }

    private void checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null)
            mView.onNetworkFail();
    }

    private void checkLogIn() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mView.onAutoLoginDone();
                    } else {
                        mView.onAutoLoginFail();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            mView.onAutoLoginFail();
        }
    }

    boolean isFirstRun() {
        return Globals.getIns().getConfig().isFirstRun();
    }

    @Override
    public void loadConfigDone() {
        if (isFirstRun()) {
            mView.onFirstRun();
            setIsFirstRun();
        }
        else
            checkLogIn();
    }
    void setIsFirstRun() {
        SharedPreferences sPref;
        sPref = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(Constants.IS_FIRST_RUN, false);
        editor.apply();
    }

}
