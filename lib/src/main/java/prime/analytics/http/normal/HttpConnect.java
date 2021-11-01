package prime.analytics.http.normal;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpConnect {
    private URL testURL;
    private List<HttpCookie> cookies;
    private String responseContent = "";
    private int responseCode = -1;
    private String responseRedirectUrl = "";

    public HttpConnect(String testUrl) throws MalformedURLException {
        this.testURL = new URL(testUrl);
        this.cookies = new ArrayList<HttpCookie>();
    }

    public HttpConnect(String testUrl, List<HttpCookie> cookies) throws MalformedURLException {
        this.testURL = new URL(testUrl);
        this.cookies = cookies;
    }

    protected HttpConnect setCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
        return this;
    }

    public int connect() throws IOException {
        return connect("GET");
    }

    public int connect(String method) throws IOException {
        return connect(method, null);
    }

    public int connect(String method, Map<String,Object> postData) throws IOException {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) testURL.openConnection();
            conn.setRequestMethod(method);
            if (cookies.size() > 0) {
                conn.setRequestProperty("Cookie", getCookieHeader());
            }
            conn.connect();

            responseCode = conn.getResponseCode();
            responseRedirectUrl = conn.getHeaderField("Location");
            responseContent = readContent();

            return responseCode;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
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
        System.out.println("HttpConnect");
    }
}
