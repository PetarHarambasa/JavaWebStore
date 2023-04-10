package hr.algebra.webshop.utils;

public class FileUtils {
    public static String getFileExtension(String fileName) {
        // Get the last index of '.' in the file name
        int lastIndex = fileName.lastIndexOf('.');

        // If the last index is greater than 0 and less than the length of the file name - 1
        // then return the file extension
        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex + 1);
        } else {
            return "";
        }
    }
}
