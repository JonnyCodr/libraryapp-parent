package utils;

import org.junit.Ignore;

@Ignore
public class FileTestNameUtils {

    private static final String PATH_REQUEST = "/request/";
    private static final String PATH_RESPONSE = "/response";

    public FileTestNameUtils() {
    }

    public static String getPathFileRequest(final String mainfolder, final String fileName) {
        return mainfolder + PATH_REQUEST + fileName;
    }

    public static String getPathFileResponse (final String mainFolder, final String filename) {
        return mainFolder + PATH_RESPONSE + filename;
    }

}
