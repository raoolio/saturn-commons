package com.saturn.commons.http;


/**
 * HTTP payload content types
 * @author rdelcid
 */
public enum HttpContentType {

    /**
     * <p>The <b>application/x-www-form-urlencoded</b> format is a simple way
     * to encode name-value pairs in a byte sequence where all bytes are
     * ASCII bytes</p>
     */
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded","ISO_8859_1"),

    /**
     * <p><b>JavaScript Object Notation</b> (JSON) is a lightweight, text-based,
     * language-independent data interchange format.</p>
     * <p>It was derived from the ECMAScript Programming Language Standard.
     * JSON defines a small set of formatting rules for the portable
     * representation of structured data</p>
     */
    APPLICATION_JSON("application/json","UTF-8"),

    /**
     * <p>The <b>application/xml</b> media type [RFC3023] is a generic media type for
     * XML documents, and the definition of 'application/xml' does not preclude
     * serving XHTML documents as that media type. Any XHTML Family document MAY
     * be served as 'application/xml'</p>
     */
    APPLICATION_XML("application/xml", "ISO_8859_1"),

    /**
     * <p>The <b>multipart/form-data</b> can be used for forms that are
     * presented using representations other than HTML (spreadsheets, Portable
     * Document Format, etc), and for transport using other means than
     * electronic mail or HTTP<p>
     */
    MULTIPART_FORM_DATA("multipart/form-data", "ISO_8859_1"),

    /**
     * <p>The <b>text/html</b> media type can be used for sending content in
     * HTML language.</p>
     */
    TEXT_HTML("text/html", "UTF-8"),

    /**
     * <p>The <b>text/plain</b> media type can be used for sending content in
     * plain text without any format.</p>
     */
    TEXT_PLAIN("text/plain", "ISO_8859_1"),

    TEXT_XML("text/xml", "ISO_8859_1");




    /** Content type string value */
    private String type;

    /** Default character set */
    private String charset;



    /**
     * Constructor
     * @param type
     */
    HttpContentType(String type,String charset) {
        this.type=type;
        this.charset=charset;
    }



    /**
     * Returns the content type string value
     * @return
     */
    public String getType() {
        return type;
    }


    /**
     * Returns this content's type suggested charset
     * @return
     */
    public String getCharset() {
        return charset;
    }



    /**
     * Returns the corresponding enum type
     * @param str HttpContentType string
     * @return
     */
    public static HttpContentType parseContentType(String str) {
        HttpContentType type=null;

        for (HttpContentType ct: HttpContentType.values()) {
            if (ct.getType().contains(str)) {
                type= ct;
                break;
            }
        }

        return type;
    }


}


