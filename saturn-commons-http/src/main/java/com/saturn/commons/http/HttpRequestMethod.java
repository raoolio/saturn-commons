package com.saturn.commons.http;


/**
 * HTTP Request Methods.
 * @link "https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html"
 * @author rdelcid
 */
public enum HttpRequestMethod
{

    /**
     * <p>The <b>GET</b> method means retrieve whatever information (in the form
     * of an entity) is identified by the Request-URI.</p>
     * <p>If the Request-URI refers to a data-producing process, it is the
     * produced data which shall be returned as the entity in the response and
     * not the source text of the process, unless that text happens to be the
     * output of the process</p>
     */
    GET,


    /**
     * <p>The <b>HEAD</b> method is identical to GET except that the server MUST NOT
     * return a message-body in the response. The metainformation contained
     * in the HTTP headers in response to a HEAD request SHOULD be identical to
     * the information sent in response to a GET request.</p> <p>This method can
     * be used for obtaining metainformation about the entity implied by the
     * request without transferring the entity-body itself. This method is
     * often used for testing hypertext links for validity, accessibility, and
     * recent modification</p>
     */
    HEAD,


    /**
     * <p>The <b>POST</b> method is used to request that the origin server accept
     * the entity enclosed in the request as a new subordinate of the resource
     * identified by the Request-URI in the Request-Line. POST is designed to
     * allow a uniform method to cover the following functions:</p>
     *
     * <ul>
     *  <li>Annotation of existing resources;</li>
     *  <li>Posting a message to a bulletin board, newsgroup, mailing list,
     *   or similar group of articles;</li>
     *  <li>Providing a block of data, such as the result of submitting a
     *   form, to a data-handling process;</li>
     *  <li>Extending a database through an append operation.</li>
     * </ul>
     *
     * <p>The actual function performed by the POST method is determined by the
     * server and is usually dependent on the Request-URI. The posted entity is
     * subordinate to that URI in the same way that a file is subordinate to a
     * directory containing it, a news article is subordinate to a newsgroup to
     * which it is posted, or a record is subordinate to a database.</p>
     */
    POST,


    /**
     * <p>The <b>PUT</b> method requests that the enclosed entity be stored under
     * the supplied Request-URI. If the Request-URI refers to an already existing
     * resource, the enclosed entity SHOULD be considered as a modified version
     * of the one residing on the origin server. If the Request-URI does not point
     * to an existing resource, and that URI is capable of being defined as a
     * new resource by the requesting user agent, the origin server can create
     * the resource with that URI.</p>
     * <p>If a new resource is created, the origin server MUST inform the user
     * agent via the 201 (Created) response.</p>
     * <p>If an existing resource is modified, either the 200 (OK) or
     * 204 (No Content) response codes SHOULD be sent to indicate successful
     * completion of the request.</p>
     * <p>If the resource could not be created or modified with the Request-URI,
     * an appropriate error response SHOULD be given that reflects the nature of
     * the problem.</p>
     * <p>The recipient of the entity MUST NOT ignore any Content-* (e.g. Content-Range)
     * headers that it does not understand or implement and MUST return a 501
     * (Not Implemented) response in such cases</p>
     */
    PUT,


    /**
     * <p>The <b>DELETE</b> method requests that the origin server delete the
     * resource identified by the Request-URI. This method MAY be overridden by
     * human intervention (or other means) on the origin server. The client
     * cannot be guaranteed that the operation has been carried out, even if
     * the status code returned from the origin server indicates that the action
     * has been completed successfully. However, the server SHOULD NOT indicate
     * success unless, at the time the response is given, it intends to delete
     * the resource or move it to an inaccessible location.</p>
     *
     * <p>A successful response SHOULD be 200 (OK) if the response includes an
     * entity describing the status, 202 (Accepted) if the action has not yet
     * been enacted, or 204 (No Content) if the action has been enacted but the
     * response does not include an entity.</p>
     */
    DELETE,


    /**
     * <p> The <b>CONNECT</b> method is reserved for use with a proxy that can
     * dynamically switch to being a tunnel, for example a SSL tunneling.
     * </p>
     */
    CONNECT,


    /**
     * <p>The <b>OPTIONS</b> method represents a request for information about the
     * communication options available on the request/response chain identified
     * by the Request-URI.</p> <p>This method allows the client to determine
     * the options and/or requirements associated with a resource, or the
     * capabilities of a server, without implying a resource action or
     * initiating a resource retrieval</p>
     */
    OPTIONS,


    /**
     * <p>The <b>TRACE</b> method is used to invoke a remote, application-layer
     * loop-back of the request message. The final recipient of the request
     * SHOULD reflect the message received back to the client as the entity-body
     * of a 200 (OK) response.</p>
     * <p>The final recipient is either the origin server or the first proxy or
     * gateway to receive a Max-Forwards value of zero (0) in the request.
     * A TRACE request MUST NOT include an entity.</p>
     */
    TRACE,


    /**
     * <p>The <b>PATCH</b> method applies partial modifications to a resource.</p>
     * <p>The HTTP PUT method only allows complete replacement of a document.
     * Unlike PUT, PATCH is not idempotent, meaning successive identical patch
     * requests may have different effects. However, it is possible to issue
     * PATCH requests in such a way as to be idempotent.</p>
     */
    PATCH

}
