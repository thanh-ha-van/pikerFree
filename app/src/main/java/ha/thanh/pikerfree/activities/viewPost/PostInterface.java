package ha.thanh.pikerfree.activities.viewPost;

import java.util.List;

import ha.thanh.pikerfree.models.Post;

/**
 * Created by HaVan on 9/23/2017.
 */

public class PostInterface {
    interface RequiredViewOps {
        void getPostDone(Post post);
        void getLinkDone();
        void getPostFail();
        void onInternetFail(String error);
    }
}
