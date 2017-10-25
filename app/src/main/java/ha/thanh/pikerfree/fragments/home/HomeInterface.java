package ha.thanh.pikerfree.fragments.home;

import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 8/27/2017.
 */

public class HomeInterface {
    interface RequiredViewOps {
        void onLocalDataReady(String name, String address, String filepath);
        void onGetUserDataDone(User user);
        void onGetUserPostsDone();
    }
}
