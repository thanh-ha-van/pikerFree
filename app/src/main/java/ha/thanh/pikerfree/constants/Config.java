package ha.thanh.pikerfree.constants;


public class Config {
    private static final int DEFAULT_WORD_OF_DATE = 8;

    private boolean isPermissionWriteFile = false;

    public Config() {

    }

    public boolean isPermissionWriteFile() {
        return isPermissionWriteFile;
    }

    public void setPermissionWriteFile(boolean permissionWriteFile) {
        isPermissionWriteFile = permissionWriteFile;
    }
}

