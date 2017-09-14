package ha.thanh.pikerfree.activities.newPost;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
