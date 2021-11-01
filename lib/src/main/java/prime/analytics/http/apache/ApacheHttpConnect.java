package prime.analytics.http.apache;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ApacheHttpConnect {
    private URL testURL;
    private List<HttpCookie> cookies;
    private String responseContent = "";
    private int responseCode = -1;
    private String responseRedirectUrl = "";

    public ApacheHttpConnect(String testUrl) throws MalformedURLException {
        this.testURL = new URL(testUrl);
        this.cookies = new ArrayList<HttpCookie>();
    }

    public ApacheHttpConnect(String testUrl, List<HttpCookie> cookies) throws MalformedURLException {
        this.testURL = new URL(testUrl);
        this.cookies = cookies;
    }

    protected ApacheHttpConnect setCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
        return this;
    }

    public int connect() throws IOException {
        return connect("GET");
    }

    public int connect(String method) throws IOException {
        return connect(method, null);
    }

    public int connect(String method, Map<String, Object> postData) throws IOException {
        try {
            CloseableHttpClient httpClient = HttpClients.custom().build();

            System.out.printf("Get request to: %s%n", testURL);
            HttpGet req = new HttpGet(testURL.toString());
            HttpResponse res = httpClient.execute(req);

            StatusLine status = res.getStatusLine();
            System.out.printf("Response status: %s%n", status);

            return responseCode;
        } finally {
        }
    }

    private String readContent() {
        return "";
    }

    private String getCookieHeader() {
        return "";
    }

    public String getResponseContent() {
        return responseContent;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseRedirectUrl() {
        return responseRedirectUrl;
    }

    public static void main(String[] args) {
        System.out.println("ApacheHttpConnect");
    }

}
