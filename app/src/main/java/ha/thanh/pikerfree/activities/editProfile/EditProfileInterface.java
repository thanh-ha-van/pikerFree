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
        void onLocalInforReady(String name, String address, String filepath);
    }

    interface RequiredPresenterOps {

    }
}
