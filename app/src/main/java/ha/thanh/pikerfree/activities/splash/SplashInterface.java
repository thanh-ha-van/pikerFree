package ha.thanh.pikerfree.activities.splash;

/**
 * Created by HaVan on 5/24/2017.
 */

interface SplashInterface {

    interface RequiredViewOps {

        void onAutoLoginDone();

        void onNetworkFail();

        void onAutoLoginFail();

        void onFirstRun();
    }

    interface RequiredPresenterOps {
        void loadConfigDone();
    }
}
