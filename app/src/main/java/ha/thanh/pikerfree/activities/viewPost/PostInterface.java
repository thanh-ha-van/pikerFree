package ha.thanh.pikerfree.activities.viewPost;

import android.net.Uri;

import java.util.List;

import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;



class PostInterface {
    interface RequiredViewOps {
        void getPostDone(Post post);
        void getLinkDone();
        void getPostFail();
        void getOwnerDone(User user);
        void getOwnerImageDone(Uri uri);
    }
}
