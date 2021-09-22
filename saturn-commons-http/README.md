Saturn Commons Http
=====================
This module provides a way to perform HTTP requests using easy to learn approach. Why another library for performing HTTP requests you ask? well this implementation offers features not found on other libraries:

- One interface supports Java's native `UrlConnection` or `Apache Http Core`.
- Fluent `HttpRequestBuilder` to build requests easily.
- Supports the request methods: `GET, POST, PUT, DELETE, HEAD, CONNECT, OPTIONS, TRACE, PATH`.
- Supports parameter placeholder replacement in the URL and in the request's content.
- Can automatically build the request content using provided parameters in **JSON** or **URL-Encoded** formats.
- By default supports pooled TCP connections.

Let's dive into some examples to get you started:

GET Example
-----------
Here is a simple HTTP Get example:
```java
    // Build the request object
    HttpRequest req= new HttpRequestBuilder()
        .setUrl("http://yourendpoint.com/path")
        .setRequestMethod(HttpRequestMethod.GET)
        .build();

    // Perform the actual request (by default uses Java native UrlConnection)
    HttpResponse res= HttpClientFactory.getHttpClient().send(req);

    // Validate the response
    if (res.isSuccess()) {
        // do something...
    }
```
GET Example with Parameters
--------------------------
Here is a simple HTTP Get example that has some parameter placeholders in the URL, this placeholders are replaced with the actual values provided in the builder prior to performing the actual request.
```java
    // Build the request object
    HttpRequest req= new HttpRequestBuilder()
        .setUrl("http://yourendpoint.com/{client}/{id}")
        .setRequestMethod(HttpRequestMethod.GET)
        .addParam("client","26VRI86ECD")
        .addParam("id","10")
        .build();

    // Perform the actual request (by default uses Java native UrlConnection)
    HttpResponse res= HttpClientFactory.getHttpClient().send(req);

    // Validate the response
    if (res.isSuccess()) {
        // do something...
    }
```
The above code would perform a request that looks like this:
```
GET http://yourendpoint.com/path/26VRI86ECD/10
```

POST Example
-----------
Here is a simple HTTP POST that builds a JSON content from the given parameters:
```java
    // Build the request object
    HttpRequest req= new HttpRequestBuilder()
        .setUrl("http://yourendpoint.com/path/")
        .setRequestMethod(HttpRequestMethod.POST)
        .setContentType(HttpContentType.APPLICATION_JSON)
        .addParam("client","26VRI86ECD")
        .addParam("id","10")
        .build();

    // Perform the actual request
    HttpResponse res= HttpClientFactory.getHttpClient().send(req);

    // Validate the response
    if (res.isSuccess()) {
        // do something...
    }
```
The above code would perform a request that looks like this:
```
POST http://yourendpoint.com/path/
CONTENT: { "client":"26VRI86ECD", "id":"10" }
```


`HttpRequestBuilder` Explained
-----------------------------
This fluent builder allows you to perform many types of requests, you can use the following methods:

|Method     |Description    |
|---        |---            |
|`setUrl`|Sets the request's URL endpoint (with parameter placeholder support)|
|`setRequestMethod`|Sets the HTTP request method. (see `HttpRequestMethod`). Default: **GET** |
|`setContent`|Sets the request content (with parameter placeholder support).|
|`setContentType`|Sets the request content type (see `HttpContentType`)|
|`setContentCharset`|Sets the content charset.|
|`setTimeout`|Sets the request timeout in seconds (default is 30).|
|`addHeader`|Adds a HTTP header name/value pair to the request.|
|`addHeaderIfMissing`|Adds given header value only if it's not already set.|
|`addParam`|Adds a parameter name/value pair for placeholder replacement or content building.|
|`addParamAll`|Adds a Map of name/value pairs to the request.|
|`setSendAllParams`|Sets the parameter building mode. If **true** all unused parameters are sent in the request. For GET all remaining parameters are appended in the endpoint URL. For POST all remaining parameters are built in the content (if no content was specified). Default value: **true**|
|`setFetchHeaders`|Retrieve the HTTP response headers? Default: **false**|
|`setBasicCredentials`|Sets user/password for BASIC authentication method.|
|`setBasicAuth`|Sets the string for BASIC authentication method.|
|`setFollowRedirects`|Follow redirect responses? (HTTP status codes 300-308). Default: **true**|
|`setUserAgent`|Sets the **User-Agent** header value.|
|`setReferer`|Sets the **Referer** header value.|
|`addCookie`|Adds the given cookie value to the request headers.|
|`setCookies`|Adds all the given cookies map to the request headers.|





`HttpClientFactory` Explained
--------------------------
The HttpClientFactory returns a `HttpClient` instance that can be used to perform the actual HTTP request.

|Method |Parameter  |Description |
|---    |---        |---         |
|`getHttpClient`|None|With no parameters returns a HttpClient instance that runs with native Java UrlConnection. |
|`getHttpClient`|HttpClientType|You can specify which HttpClient instance to return. Supported types are: `NATIVE` and `APACHE` |



`HttpResponse` Explained
--------------------------
The HttpClientResponse object allows you to obtain everything you need from the request result.

|Method     |Description    |
|---        |---            |
|`isSuccess`|Indicates if the request was successful (status code between 200 and 299).|
|`getStatus`|Returns the HTTP response code of the request.|
|`getMessage`|Returns the HTTP response code message.|
|`hasContent`|Indicates if the response has content.|
|`getContent`|Returns the response content as a String.|
|`getContentStream`|Returns the response content as an InputStream|
|`getContentType`|Returns the HTTP response content type.|
|`getContentEncoding`|Returns the HTTP response content encoding.|
|`getContentLength`|Returns the HTTP response content length.|
|`getHeaders`|Returns the HTTP response headers.|
|`getCookies`|Returns the response cookies as a key-value map.|
|`isRedirect`|Tells if the given response is a redirect (HTTP status codes 300-308)|
|`getLocation`|Returns the **location** response header value.|





