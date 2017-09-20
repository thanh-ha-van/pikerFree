package ha.thanh.pikerfree.activities.editProfile;

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
