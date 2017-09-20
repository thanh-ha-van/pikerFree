package ha.thanh.pikerfree.models;

/**
 * Created by HaVan on 9/21/2017.
 */

public class ImagePost {
    private  String path;
    private boolean isUploadDone;

    public ImagePost(String path) {
        this.path = path;
        isUploadDone = false;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isUploadDone() {
        return isUploadDone;
    }

    public void setUploadDone(boolean uploadDone) {
        isUploadDone = uploadDone;
    }
}
