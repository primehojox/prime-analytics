package prime.analytics.http.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CipherSupport {
    SSLContext defaultContext;
    SSLEngine defaultEngine;
    SSLParameters defaultParams;
    SSLParameters supportParams;

    public CipherSupport() throws NoSuchAlgorithmException {
        this.defaultContext = SSLContext.getDefault();
        this.defaultEngine = this.defaultContext.createSSLEngine();
        this.defaultParams = defaultContext.getDefaultSSLParameters();
        this.supportParams = defaultContext.getSupportedSSLParameters();
    }

    public CipherSupport(String useProtocols) throws NoSuchAlgorithmException, KeyManagementException {
        this.defaultContext = SSLContext.getInstance(useProtocols);
        this.defaultContext.init(null, null, null);
        this.defaultEngine = this.defaultContext.createSSLEngine();
        this.defaultParams = defaultContext.getDefaultSSLParameters();
        this.supportParams = defaultContext.getSupportedSSLParameters();
    }

    public void printCiphersInfo() {
        System.out.printf("default ciphers(%d): %s%n", defaultParams.getCipherSuites().length,
                Arrays.toString(defaultParams.getCipherSuites()));
        System.out.printf("support ciphers(%d): %s%n", supportParams.getCipherSuites().length,
                Arrays.toString(supportParams.getCipherSuites()));
        if (defaultEngine != null) {
            System.out.printf("enabled ciphers(%d): %s%n", defaultEngine.getEnabledCipherSuites().length,
                    Arrays.toString(defaultEngine.getEnabledCipherSuites()));
        }

        List<String> notActive = notActiveCiphers();
        System.out.printf("not active ciphers(%d): %s%n", notActive.size(), Arrays.toString(notActive.toArray()));
    }

    public List<String> notActiveCiphers() {
        List<String> supportedCiphers = Arrays.asList(supportParams.getCipherSuites());
        List<String> defaultCiphers = Arrays.asList(defaultParams.getCipherSuites());

        List<String> notEnabled = new ArrayList<String>();
        for (String support : supportedCiphers) {
            if (!defaultCiphers.contains(support)) {
                notEnabled.add(support);
            }
        }

        return notEnabled;
    }

    public static void main(String[] args) {
        try {
            System.out.printf("java.version: %s%n", System.getProperty("java.version"));
            System.out.printf("https.cipherSuites: %s%n", System.getProperty("https.cipherSuites"));
            System.out.printf("jdk.tls.client.cipherSuites: %s%n", System.getProperty("jdk.tls.client.cipherSuites"));

            if ("1.6.0_131".equals(System.getProperty("java.version"))) {
                System.out.println("Add Bouncy Castle");
                // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            } else if ("1.6.0_191".equals(System.getProperty("java.version"))) {
                System.out.println("Add Bouncy Castle");
                // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            }
            new CipherSupport().printCiphersInfo();
            /*
             */
            if (System.getProperty("jdk.tls.client.protocols") == null) {
                System.out.println("----- Re-run with TLSv1.2 set -----");
                new CipherSupport("TLSv1.2").printCiphersInfo();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}