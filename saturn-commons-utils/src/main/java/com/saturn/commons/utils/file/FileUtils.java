package com.saturn.commons.utils.file;

import java.io.File;


/**
 * FileUtils class
 */
public class FileUtils {


    /**
     * Private constructor
     */
    private FileUtils() {
    }



    /**
     * Search for given filename in given path
     * @param path Destination path to perform search
     * @param name Target filename to look
     * @return
     */
    public static File findFile(File path,String name) {
        return findFile(path,name,false);
    }



    /**
     * Search for given filename in given path
     * @param path Destination path to perform search
     * @param name Target filename to look
     * @param caseSensitive Case-sensitive name comparison?
     * @return
     */
    public static File findFile(File path,String name,boolean caseSensitive) {
        if (path.isFile() && (caseSensitive? path.getName().equals(name) : path.getName().equalsIgnoreCase(name)) ) {
            return path;
        } else {
            File[] files= path.listFiles(new FileFindFilter(name,caseSensitive));
            if (files!=null) {
                for (File f:files) {
                    File target= findFile(f,name,caseSensitive);
                    if (target!=null)
                        return target;
                }
            }
            return null;
        }
    }


}
