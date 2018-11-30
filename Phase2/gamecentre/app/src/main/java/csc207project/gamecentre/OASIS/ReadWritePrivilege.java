package csc207project.gamecentre.OASIS;

public interface ReadWritePrivilege {

    /**
     * For loading object from local files.
     */
    void loadFromFile();

    /**
     * For saving object to local files.
     */
    void saveToFile();
}
