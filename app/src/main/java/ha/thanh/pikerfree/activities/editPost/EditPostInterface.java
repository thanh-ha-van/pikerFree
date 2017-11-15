package ha.thanh.pikerfree.activities.editPost;

import android.net.Uri;

import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 10/16/2017.
 */

public class EditPostInterface {
    interface RequiredViewOps {

        void onPostDone();

        void onUploadSingleImageDone();

        void getPostDone(Post post);

        void getLinkDone();

        void getPostFail();
    }
}
