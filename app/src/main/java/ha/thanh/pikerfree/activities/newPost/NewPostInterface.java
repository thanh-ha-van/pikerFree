package ha.thanh.pikerfree.activities.newPost;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostInterface {
    interface RequiredViewOps {

        void onPostDone();
        void onUploadSingleImageDone();
        void onPostFail(String error);
    }

    interface RequiredPresenterOps {

        void onSaveLocalDone();

        void onSaveLocalFail(String error);
    }
}
