package pl.p32.app;

public class DeletionDetails {

    private long deletedFiles = 0;
    private long deletedDirectories = 0;
    private long deletedBytes = 0;

    public long getDeletedFiles() {
        return deletedFiles;
    }

    public long getDeletedDirectories() {
        return deletedDirectories;
    }

    public long getDeletedBytes() {
        return deletedBytes;
    }

    public void addFile() {
        deletedFiles++;
    }

    public void addDirectory() {
        deletedDirectories++;
    }

    public void addBytes(long bytes) {
        deletedBytes += bytes;
    }

    @Override
    public String toString() {
        return "DeletionDetails{" +
                "deletedFiles=" + deletedFiles +
                ", deletedDirectories=" + deletedDirectories +
                ", deletedBytes=" + deletedBytes +
                '}';
    }
}
