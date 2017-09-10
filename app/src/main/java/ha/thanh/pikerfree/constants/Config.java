package ha.thanh.pikerfree.constants;


public class Config {
    private static final int DEFAULT_WORD_OF_DATE = 8;
    public static final String BASE_URL = "https://pikerfree.firebaseio.com/";

    private boolean isPermissionWriteFile = false;
    private boolean isPermissionCamera = false;

    public Config() {

    }

    public boolean isPermissionWriteFile() {
        return isPermissionWriteFile;
    }

    public void setPermissionWriteFile(boolean permissionWriteFile) {
        isPermissionWriteFile = permissionWriteFile;
    }

    public boolean isPermissionCamera() {
        return isPermissionCamera;
    }

    public void setPermissionCamera(boolean permissionCamera) {
        isPermissionCamera = permissionCamera;
    }
}

