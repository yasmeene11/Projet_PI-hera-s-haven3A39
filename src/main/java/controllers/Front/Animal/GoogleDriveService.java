package controllers.Front.Animal;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleDriveService {
    private static final String APPLICATION_NAME = "UnitedPetss";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\perri\\IdeaProjects\\UnitedPets (1)\\UnitedPets\\src\\main\\resources\\client_secret_442199297983-o6umb6fn28ib76l3blinip2pfl529bd9.apps.googleusercontent.com.json";
    private static final String USER_EMAIL = "nuharuached@gmail.com"; // The email associated with your service account

    private Drive service;

    public GoogleDriveService() throws IOException, GeneralSecurityException {
        final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials(HttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        credential = credential.createDelegated(USER_EMAIL); // impersonate the user
        return credential;
    }

    public void uploadPDF(String pdfFilePath, String folderId) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName("My PDF File");
        fileMetadata.setMimeType("application/pdf");
        fileMetadata.setParents(Collections.singletonList(folderId));

        java.io.File filePath = new java.io.File(pdfFilePath);
        FileContent mediaContent = new FileContent("application/pdf", filePath);
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());
    }
}