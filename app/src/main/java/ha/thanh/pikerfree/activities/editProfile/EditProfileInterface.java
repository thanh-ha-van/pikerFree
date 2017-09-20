package ha.thanh.pikerfree.activities.editProfile;

import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 9/10/2017.
 */

public interface EditProfileInterface {

    interface RequiredViewOps {

        void showDialog();

        void hideDialog();

        void onLocalDataReady(String name, String address, String filepath);

        void onLocalDataFail();

        void onServerDataReady(User user);

        void onServerDataFail(String error);
    }

    interface RequiredPresenterOps {

    }
}
