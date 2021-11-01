package prime.analytics.http.normal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class HttpConnectTest {
    @Test
    void connectSuccessful() throws IOException {
        HttpConnect test = new HttpConnect("https://www.google.ca");
        assertTrue(test.connect() >= 200, "Connection was successful");
    }
}
