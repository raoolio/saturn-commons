package com.saturn.commons.utils;

import static com.saturn.commons.utils.StringUtils.safeTrim;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * PathUtils
 */
public class PathUtils {


    private PathUtils() {
    }


    /**
     * Stores the given string values into a Map
     *
     * Input:
     *    "objeto.par1,par2,par3"
     *
     * Output:
     * MAP(
     *     { "objeto" "par1" } -> "objeto.par1"
     *     { "par2" } -> "par2"
     *     { "par3" } -> "par3"
     * )
     *
     * @param ids String to split
     * @param sep Splitting char
     * @return
     */
    public static Map<LinkedList,String> toPathListMap(String ids,String sep) {
        return toPathListMap(null,ids,sep);
    }



    /**
     * Stores the given string values into a Map
     *
     * Input:
     *    "objeto.par1,par2,par3"
     *
     * Output:
     * MAP(
     *     { "objeto" "par1" } -> "objeto.par1"
     *     { "par2" } -> "par2"
     *     { "par3" } -> "par3"
     * )
     *
     * @param parent Parent
     * @param str String to split
     * @param sep Splitting char
     * @return
     */
    public static Map<LinkedList,String> toPathListMap(String parent,String str,String sep) {
        String[] paths= str==null ? new String[0] : str.split(sep);
        Map m= new LinkedHashMap();
        String[] parPath= parent==null? null : parent.split("\\.");

        for (String path: paths) {
            String[] ids= path.split("\\.");
            List l= new LinkedList();

            // Add Parent to path list ?
            if (parPath!=null) {
                for (String id: parPath) {
                    l.add(safeTrim(id));
                }
            }

            // Add attribute ids
            for (String id: ids) {
                l.add(safeTrim(id));
            }
            m.put(l, path);
        }

        return m;
    }



}
