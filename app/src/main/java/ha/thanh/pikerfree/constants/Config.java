package ha.thanh.pikerfree.constants;


public class Config {

    private boolean isPermissionWriteFile = false;
    private boolean isPermissionCamera = false;
    private boolean isPermissionLocation = false;

    private boolean isFirstRun = true;
    private int lastDay = 7;
    private int distanceSearch = 7;
    private boolean notificationMess = true;
    private boolean notificationPost = true;

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public int getDistanceSearch() {
        return distanceSearch;
    }

    public void setDistanceSearch(int distanceSearch) {
        this.distanceSearch = distanceSearch;
    }

    public boolean isNotificationMess() {
        return notificationMess;
    }

    public void setNotificationMess(boolean notificationMess) {
        this.notificationMess = notificationMess;
    }

    public boolean isNotificationPost() {
        return notificationPost;
    }

    public void setNotificationPost(boolean notificationPost) {
        this.notificationPost = notificationPost;
    }

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

