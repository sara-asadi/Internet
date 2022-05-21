package ir.ac.ut.iemdb.services.HTTPRequestHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.http.impl.client.HttpClientBuilder;

public class HTTPRequestHandler {

    public static int getStatusCode(String uri) throws Exception {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(uri);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return response.getStatusLine().getStatusCode();
        }

    }

    public static String getRequest(String uri) throws Exception {
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(uri);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            String result = "";
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            return result;
        }
    }

    public static void postRequest(String url) throws Exception{
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(url);
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ignored) {
        }
    }

}
