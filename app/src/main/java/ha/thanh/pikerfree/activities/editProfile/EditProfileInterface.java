package ha.thanh.pikerfree.activities.editProfile;

import android.graphics.Bitmap;

import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 9/10/2017.
 */

public interface EditProfileInterface {

    interface RequiredViewOps {
        void showDialog();
        void hideDialog();
        void onUpdateUserData(User user, String url);
        void onLocalBitmapReady(Bitmap bitmap);
    }

    interface RequiredPresenterOps {

    }
}
