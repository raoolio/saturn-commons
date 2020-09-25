package com.saturn.commons.utils.file;

import java.io.File;
import java.io.FileFilter;


/**
 * FileFinder filter
 */
public class FileFindFilter implements FileFilter {

    /** File to find */
    private String fileName;

    /** Case sensitive? */
    private boolean caseSensitive;



    /**
     * Constructor
     * @param fileName Filename to search
     */
    public FileFindFilter(String fileName) {
        this(fileName,false);
    }



    /**
     * Constructor
     * @param fileName Filename to search
     * @param caseSensitive Case sensitive comparison?
     */
    public FileFindFilter(String fileName, boolean caseSensitive) {
        this.fileName = fileName;
        this.caseSensitive = caseSensitive;
    }



    @Override
    public boolean accept(File f) {
        if (caseSensitive)
            return (f.isFile() && f.getName().equals(fileName)) || f.isDirectory();
        else
            return (f.isFile() && f.getName().equalsIgnoreCase(fileName)) || f.isDirectory();
    }


}
