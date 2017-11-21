package ha.thanh.pikerfree.activities.conversation;

import ha.thanh.pikerfree.models.Message;

/**
 * Created by HaVan on 10/16/2017.
 */

public class ConInterface {
    interface RequiredViewOps {
        void OnGoToProfile(String id);
        void OnNewMess(Message message);
    }

}
