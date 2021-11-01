package prime.analytics.security;

import java.security.Provider;
import java.security.Security;

public class ProviderSupport {

    public void printProvidersInfo() {
        Provider[] secProviders = Security.getProviders();
        for (int p=0; p < secProviders.length; p++) {
            Provider provider = secProviders[p];
            System.out.printf("%d) %s%n", p+1, provider);
        }
    }

    public static void main(String[] args) {
        try {
            new ProviderSupport().printProvidersInfo();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}
