package ha.thanh.pikerfree.activities.newPostActivity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;

import static android.content.ContentValues.TAG;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostPresenter implements NewPostInterface.RequiredPresenterOps {
    private NewPostInterface.RequiredViewOps mView;
    private NewPostModel mModel;

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    NewPostPresenter(Context context, NewPostInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new NewPostModel(context, this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}
