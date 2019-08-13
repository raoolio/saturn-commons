package com.saturn.commons.http.impl.client;

import com.saturn.commons.http.HttpRequest;
import com.saturn.commons.http.HttpResponse;
import com.saturn.commons.http.impl.DefaultHttpResponse;
import java.io.ByteArrayInputStream;
import java.net.Socket;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;


/**
 * Apache HTTP Client implementation
 * @author rdelcid
 */
public class ApacheHttpClient extends BaseHttpClient
{

    @Override
    public HttpResponse sendRequest(HttpRequest req) throws Exception {

        DefaultHttpResponse httpResp= new DefaultHttpResponse();
        
        HttpProcessor httpproc = HttpProcessorBuilder.create()
            .add(new RequestContent())
            .add(new RequestTargetHost())
            .add(new RequestConnControl())
            .add(new RequestUserAgent("Test/1.1"))
            .add(new RequestExpectContinue()).build();

        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost("localhost", 8080);
        coreContext.setTargetHost(host);

        DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
        ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;

        try {

            HttpEntity[] requestBodies = {
                    new StringEntity("This is the first test send", ContentType.create("text/plain", Consts.UTF_8)),
                    new ByteArrayEntity("This is the second test send".getBytes(Consts.UTF_8),ContentType.APPLICATION_OCTET_STREAM),
                    new InputStreamEntity(new ByteArrayInputStream("This is the third test send (will be chunked)".getBytes(Consts.UTF_8)),ContentType.APPLICATION_OCTET_STREAM)
            };

            for (int i = 0; i < requestBodies.length; i++) {
                if (!conn.isOpen()) {
                    Socket socket = new Socket(host.getHostName(), host.getPort());
                    conn.bind(socket);
                }
                BasicHttpEntityEnclosingRequest send = new BasicHttpEntityEnclosingRequest("POST",
                        "/servlets-examples/servlet/RequestInfoExample");
                send.setEntity(requestBodies[i]);
                System.out.println(">> Request URI: " + send.getRequestLine().getUri());

                // process send!
                httpexecutor.preProcess(send, httpproc, coreContext);
                org.apache.http.HttpResponse response = httpexecutor.execute(send, conn, coreContext);
                httpexecutor.postProcess(response, httpproc, coreContext);

                // Extract Status code & message
                StatusLine sl=response.getStatusLine();
                httpResp.setStatus(sl.getStatusCode());
                httpResp.setMessage(sl.getReasonPhrase());

                String content= EntityUtils.toString(response.getEntity());

                if (!connStrategy.keepAlive(response, coreContext)) {
                    conn.close();
                } else {
                    System.out.println("Connection kept alive...");
                }
            }
        } finally {
            conn.close();
        }

        return httpResp;
    }

}
