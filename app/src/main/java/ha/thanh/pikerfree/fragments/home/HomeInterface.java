package ha.thanh.pikerfree.fragments.home;

import android.net.Uri;

import ha.thanh.pikerfree.models.User;

class HomeInterface {
    interface RequiredViewOps {

        void onGetUserDataDone(User user);

        void onGetUserPostsDone();

        void onNoGPS();

        void getOwnerImageDone(Uri link);
    }
}
