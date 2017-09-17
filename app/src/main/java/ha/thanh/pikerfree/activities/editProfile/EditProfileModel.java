package ha.thanh.pikerfree.activities.editProfile;

import android.content.Context;

/**
 * Created by HaVan on 9/10/2017.
 */

public class EditProfileModel {
    private EditProfileInterface.RequiredPresenterOps mPresenter;
    private Context context;


    EditProfileModel(Context context, EditProfileInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        this.context =  context;
    }
}
