package ha.thanh.pikerfree.activities.splash;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ha.thanh.pikerfree.constants.Globals;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


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
        if (cm.getActiveNetworkInfo() != null)
            mView.onNetworkFail();
    }

    boolean isFirstRun() {
        return Globals.getIns().getConfig().isFirstRun();
    }

    public void getServerInfor() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("postCount");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int value = dataSnapshot.getValue(int.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                mView.onNetworkFail();
            }
        });
    }

    @Override
    public void loadConfigDone() {
        mView.onLoadConfigDone();
    }


}
