package ha.thanh.pikerfree.activities.viewPost;

/**
 * Created by HaVan on 9/23/2017.
 */

public class PostInterface {
    interface RequiredViewOps {
        void getPostDone();
        void getLinkDone();
        void getPostFail();
        void onInternetFail(String error);
    }
}
