package ha.thanh.pikerfree.activities.editProfile;

import android.content.Context;

/**
 * Created by HaVan on 9/10/2017.
 */

public class EditProfilePresenter implements EditProfileInterface.RequiredPresenterOps {
    private EditProfileInterface.RequiredViewOps mView;
    private EditProfileModel mModel;

    EditProfilePresenter(Context context, EditProfileInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new EditProfileModel(context, this);
    }

}
