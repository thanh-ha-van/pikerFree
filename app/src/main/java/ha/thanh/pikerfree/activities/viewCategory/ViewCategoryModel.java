package ha.thanh.pikerfree.activities.viewCategory;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;

/**
 * Created by HaVan on 10/31/2017.
 */

public class ViewCategoryModel {
    private Context context;
    private SharedPreferences sharedPreferences;

    public ViewCategoryModel(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

}
