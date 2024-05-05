package services;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.sun.net.httpserver.HttpServer;

public class GoogleSignInHelper {
    private static final String APPLICATION_NAME = "UnitedPers";
    private static final String CLIENT_SECRET_FILE = "client_secret_685838587839-cj8he6m5lgvl81vi8nppplouecv5nk23.apps.googleusercontent.com.json";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final java.util.Collection<String> SCOPES = Arrays.asList(Oauth2Scopes.USERINFO_EMAIL, Oauth2Scopes.USERINFO_PROFILE);

    public static Userinfoplus signInWithGoogle() throws IOException, GeneralSecurityException {
        // Load client secrets
        InputStream inputStream = GoogleSignInHelper.class.getResourceAsStream("/" + CLIENT_SECRET_FILE);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

        // Set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline")
                .build();

        // Generate authorization URL
        String authorizationUrl = flow.newAuthorizationUrl()
                .setRedirectUri(REDIRECT_URI)
                .build();

        // Open browser window for user to authenticate
        openWebBrowser(authorizationUrl);

        // Start local server to handle callback
        CallbackServer server = new CallbackServer();
        server.start();

        // Wait for callback with authorization code
        String authorizationCode = server.waitForCallback();

        // Exchange authorization code for access token
        GoogleTokenResponse tokenResponse = flow.newTokenRequest(authorizationCode)
                .setRedirectUri(REDIRECT_URI)
                .execute();

        // Fetch user information using access token
        Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, flow.createAndStoreCredential(tokenResponse, null))
                .setApplicationName(APPLICATION_NAME)
                .build();
        Userinfoplus userInfo = oauth2.userinfo().get().execute();

        // Shutdown local server
        server.stop();

        return userInfo;
    }

    private static void openWebBrowser(String url) throws IOException, GeneralSecurityException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI.create(url));
        } else {
            throw new UnsupportedOperationException("Desktop browsing not supported.");
        }
    }

    private static class CallbackServer {
        private HttpServer server;
        private String authorizationCode;

        public void start() throws IOException {
            server = HttpServer.create(new InetSocketAddress(8888), 0);
            server.createContext("/callback", exchange -> {
                String query = exchange.getRequestURI().getQuery();
                authorizationCode = query.substring(query.indexOf("code=") + 5);
                String response = "Authentication done. You can now go back to app to check.";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            });
            server.start();
        }

        public String waitForCallback() {
            while (authorizationCode == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return authorizationCode;
        }

        public void stop() {
            if (server != null) {
                server.stop(0);
            }
        }
    }
}
