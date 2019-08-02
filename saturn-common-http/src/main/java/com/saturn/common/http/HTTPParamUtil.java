package com.saturn.common.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;


/**
 * Param replacement methods
 * @author raoolio
 */
public class HTTPParamUtil {


    /**
     * <p>Returns a copy of the given string, replacing any parameter name with
     * it's corresponding value in the map.<p>
     * <p>If the parameter exists in the map, it's value is removed from the map.
     * If it doesn't exist, the parameter name is copied to the destination
     * buffer unchanged.</p>
     *
     * @param src Source string
     * @param params Parameter map
     * @return
     */
    static String replaceAndCopy(String src,Map<String,Object> params) {
        StringBuilder sb= new StringBuilder();
        replaceAndCopy(sb,src,params,null);
        return sb.toString();
    }



    /**
     * <p>Copies the source string to the destination buffer, replacing any
     * parameter name with it's corresponding value in the map.<p>
     * <p>If the parameter exists in the map, it's value is removed from the map.
     * If it doesn't exist, the parameter name is copied to the destination
     * buffer unchanged.</p>
     * @param dst Destination buffer
     * @param req HTTPRequest instance
     */
    static void replaceAndCopy(StringBuilder dst,HTTPRequest req) {
        replaceAndCopy(dst,req.getUrl(),req.getParams(),req.getContentCharset());
    }



    /**
     * <p>Copies the source string to the destination buffer, replacing any
     * parameter name with it's corresponding value in the map.<p>
     * <p>If the parameter exists in the map, it's value is removed from the map.
     * If it doesn't exist, the parameter name is copied to the destination
     * buffer unchanged.</p>
     *
     * @param dst Destination buffer
     * @param src Source string
     * @param params Parameter map
     */
    static void replaceAndCopy(StringBuilder dst,String src,Map<String,Object> params,String charset) {

        boolean isKey=false;
        StringBuilder $key= new StringBuilder(25);

        // Loop through source chars
        for (int p=0; p<src.length(); p++) {
            char c= src.charAt(p);

            switch(c) {
                case '{':
                    if (isKey) {
                        dst.append('{').append($key);
                        $key.delete(0, $key.length());
                    } else {
                        isKey=true;
                    }
                    break;

                case '}':
                    if (isKey) {
                        isKey=false;
                        String key= $key.toString();
                        $key.delete(0, $key.length());
                        Object val= params.remove(key);
                        if (val!=null) {
                            if (charset==null) {
                                dst.append(String.valueOf(val));
                            } else {
                                try {
                                    String encPar=URLEncoder.encode(String.valueOf(val), charset);
                                    dst.append(encPar);
                                } catch (Exception e) {
                                    dst.append('{').append(key).append('}');
                                }
                            }
                        }
                        else
                            dst.append('{').append(key).append('}');
                        break;
                    }

                default:
                    StringBuilder buf= isKey? $key : dst;
                    buf.append(c);
            }
        }
    }



    //<editor-fold defaultstate="collapsed" desc=" Parameter encoding methods ">
    /**
     * Encode the sendRequest parameters
     * @param req HTTP sendRequest bean
     * @return
     * @throws Exception
     */
    static StringBuilder encodeParams(HTTPRequest req) throws Exception {
        return encodeParams(new StringBuilder(req.getParamsLength()*2),req);
    }



    /**
     * Encode the sendRequest parameters
     * @param sb Destination StringBuilder for the parameters
     * @param req HTTP sendRequest bean
     * @return
     * @throws java.lang.Exception
     */
    static StringBuilder encodeParams(StringBuilder sb,HTTPRequest req) throws Exception {

        ContentType type=req.getContentType();

        switch(type) {
            case APPLICATION_JSON: params2Json(sb,req); break;
            case APPLICATION_FORM_URLENCODED: params2UrlEncoded(sb,req); break;
//            case MULTIPART_FORM_DATA: pars2FormData(sb,req); break;
//            case TEXT_PLAIN: pars2Plain(sb,req); break;
            default: throw new Exception("ContentType "+type+" not supported yet.");
        }
        return sb;
    }



    /**
     * Encodes the parameters in JSON format
     * @param sb Destination buffer
     * @param req HTTP sendRequest bean
     */
    static void params2Json(StringBuilder sb,HTTPRequest req) throws JsonProcessingException {
        ObjectMapper mapper= new ObjectMapper();
        sb.append( mapper.writeValueAsString(req.getParams()) );
    }



    /**
     * Encodes the parameters in URL format
     * @param sb Destination buffer
     * @param req HTTP sendRequest bean
     * @throws UnsupportedEncodingException
     */
    static void params2UrlEncoded(StringBuilder sb,HTTPRequest req) throws UnsupportedEncodingException {
        Map<String,Object> pars= req.getParams();
        Iterator<String> keys= pars.keySet().iterator();
        int n=0;

        // Append parameters
        while (keys.hasNext()) {
            String id = keys.next();
            Object ob= pars.get(id);

            if (n>0)
                sb.append('&');
            sb.append(URLEncoder.encode(id, req.getContentCharset()) );
            sb.append('=');
            sb.append(URLEncoder.encode(String.valueOf(ob), req.getContentCharset()));
            n++;
        }
    }

    // TODO: Implement FormData param encoding
    static void pars2FormData(StringBuilder sb,HTTPRequest req) {
    }

    // TODO: Implement PlainText param encoding
    static void pars2Plain(StringBuilder sb,HTTPRequest req) {
    }

    //</editor-fold>



}
