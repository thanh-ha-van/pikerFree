package ha.thanh.pikerfree.activities.viewCategory;

import android.content.Context;
import android.view.View;

/**
 * Created by HaVan on 10/31/2017.
 */

public class ViewCategoryPresenter implements ViewCategoryInterface.PresenterOpt {

    private Context context;
    private ViewCategoryModel model;
    private ViewCategoryInterface.ViewOpt viewOpt;

    ViewCategoryPresenter(Context context, ViewCategoryInterface.ViewOpt viewOpt) {
        this.context = context;
        this.viewOpt = viewOpt;
        model = new ViewCategoryModel(context, this);
    }

    private void LoadAllPost(int category){

    }

    @Override
    public void onGetDataDone() {

    }

    @Override
    public void onGetDataFail() {

    }
}
