package ha.thanh.pikerfree.activities.viewCategory;

/**
 * Created by HaVan on 10/31/2017.
 */

public class ViewCategoryInterface {
    public interface ViewOpt {
        void OnGetListPostDone();

        void OnGetListPostFaild();
    }

    public interface PresenterOpt {
        void onGetDataDone();

        void onGetDataFail();
    }
}
