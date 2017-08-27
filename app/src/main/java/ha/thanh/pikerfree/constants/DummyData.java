package ha.thanh.pikerfree.constants;

import android.net.Uri;

/**
 * Created by HaVan on 8/27/2017.
 */

public class DummyData {
    private static DummyData ourInstance = null;
    public  static Uri linkForImagesTemp = Uri.parse("http://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c05145861.png");

    public static DummyData getIns() {
        if (ourInstance == null) {
            ourInstance = new DummyData();
        }
        return ourInstance;
    }

    private DummyData() {
    }

    public Uri getUri() {
        return linkForImagesTemp;
    }

}
