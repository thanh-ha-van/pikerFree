package ha.thanh.pikerfree.activities.editProfile;

import android.net.Uri;

/**
 * Created by HaVan on 9/10/2017.
 */

public interface EditProfileInterface {

    interface RequiredViewOps {

        void showDialog();

        void hideDialog();

        void onUserDataReady(String name, String address, String userPhone);

        void getOwnerImageDone(Uri link);
    }
}
