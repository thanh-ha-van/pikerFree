package ha.thanh.pikerfree.constants;


public class Config {

    private boolean isPermissionWriteFile = false;
    private boolean isPermissionCamera = false;
    private boolean isPermissionLocation = false;

    private boolean isFirstRun = true;

    Config() {

    }


    public boolean isFirstRun() {
        return isFirstRun;
    }

    public void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
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

    public boolean isPermissionLocation() {
        return isPermissionLocation;
    }

    public void setPermissionLocation(boolean permissionLocation) {
        isPermissionLocation = permissionLocation;
    }
}

