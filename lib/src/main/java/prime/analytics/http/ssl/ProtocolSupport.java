package prime.analytics.http.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProtocolSupport {
    SSLContext defaultContext;
    SSLEngine defaultEngine;
    SSLParameters defaultParams;
    SSLParameters supportParams;

    public ProtocolSupport() throws NoSuchAlgorithmException {
        this.defaultContext = SSLContext.getDefault();
        this.defaultEngine = this.defaultContext.createSSLEngine();
        this.defaultParams = defaultContext.getDefaultSSLParameters();
        this.supportParams = defaultContext.getSupportedSSLParameters();
    }

    public ProtocolSupport(String useProtocols) throws NoSuchAlgorithmException, KeyManagementException {
        this.defaultContext = SSLContext.getInstance(useProtocols);
        this.defaultContext.init(null, null, null);
        this.defaultEngine = this.defaultContext.createSSLEngine();
        this.defaultParams = defaultContext.getDefaultSSLParameters();
        this.supportParams = defaultContext.getSupportedSSLParameters();
    }

    public void printProtocolInfo() {
        System.out.printf("default protocols: %s%n", Arrays.toString(defaultParams.getProtocols()));
        System.out.printf("support protocols: %s%n", Arrays.toString(supportParams.getProtocols()));
        if (defaultEngine != null) {
            System.out.printf("enabled protocols: %s%n", Arrays.toString(defaultEngine.getEnabledProtocols()));
        }

        System.out.printf("not active protocols: %s%n", Arrays.toString(notActiveProtocols().toArray()));
    }

    public List<String> notActiveProtocols() {
        List<String> supportProtocols = Arrays.asList(supportParams.getProtocols());
        List<String> defaultProtocols = Arrays.asList(defaultParams.getProtocols());

        List<String> notEnabled = new ArrayList<String>();
        for (String support : supportProtocols) {
            if (!defaultProtocols.contains(support)) {
                notEnabled.add(support);
            }
        }

        return notEnabled;
    }

    public static void main(String[] args) {
        try {
            System.out.printf("java.version: %s%n", System.getProperty("java.version"));
            System.out.printf("https.protocols: %s%n", System.getProperty("https.protocols"));
            System.out.printf("jdk.tls.client.protocols: %s%n", System.getProperty("jdk.tls.client.protocols"));

            new ProtocolSupport().printProtocolInfo();
            if (System.getProperty("jdk.tls.client.protocols") == null) {
                System.out.println("----- Re-run with TLSv1.2 set -----");
                new ProtocolSupport("TLSv1.2").printProtocolInfo();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(0);
        
    }
}