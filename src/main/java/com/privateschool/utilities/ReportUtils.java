package com.privateschool.utilities;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import javax.print.PrintService;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.privateschool.base.BaseClass;

public class ReportUtils {	

	public static void main(String[] args) {
		//archiveCurrentReport();
		try {
			System.out.println(UploadFileToDrive());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//archiveCurrentReport();
	}
//	public static void archiveCurrentReport() {
//		try {
//    		HttpClient client = HttpClients.custom().
//                    setHostnameVerifier(new AllowAllHostnameVerifier()).
//                    setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
//                    {
//                    	public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
//                    	{
//                    		return true;
//                    	}
//                    }).build()).build();
//        	
//        	 //Creating a HttpGet object
//    		HttpPost httpget = new HttpPost(BaseClass.api_base_url+"home/PostFileUpload");
//    		java.io.File file = new java.io.File(ZipUtils.OUTPUT_ZIP_FILE);
//    		
//    		String message = "This is a multipart post";
//    		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//    		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//    		builder.addBinaryBody("formFile", file, ContentType.DEFAULT_BINARY, "Web Automation Test Report.zip");
//    		builder.addTextBody("text", message, ContentType.DEFAULT_BINARY);
//    		// 
//    		HttpEntity entity = builder.build();
//            httpget.setEntity(entity);
//            
//			 HttpResponse response = client.execute(httpget);
//            HttpEntity entit = response.getEntity();
//            String sresponse = EntityUtils.toString(entit);
//            System.out.println("===========================");
//            System.out.println(sresponse);
//            System.out.println("===========================");
//            System.out.println("Report Archived Successfully");
//    	}catch (Exception e) {
//    		e.printStackTrace();
//			
//		}
//
//	}

	/** Application name. */
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /** Directory to store authorization tokens for this application. */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static String CREDENTIALS_FILE_PATH = "credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
	
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ReportUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);//ReportUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
        	if (System.getProperty("os.name").contains("Windows")) {
        		CREDENTIALS_FILE_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\credentials.json";
    		} else {
    			CREDENTIALS_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/data/credentials.json";
    		}
        	in = ReportUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        	if (in == null) {
        		in = new FileInputStream(CREDENTIALS_FILE_PATH);
        	}
        	if(in == null)
        		throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        System.out.println("Credentials found at: "+CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

	
	
    public static String UploadFileToDrive() throws IOException, GeneralSecurityException {
    	
    	
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
        
        String folderId = "19MEXdhL_VTK-dXr5Buizyn-qhFbeH3hA";
        File fileMetadata = new File();
        fileMetadata.setName("Web Automation Report.zip");
        fileMetadata.setParents(Collections.singletonList(folderId));
        java.io.File filePath = new java.io.File(ZipUtils.OUTPUT_ZIP_FILE);
        FileContent mediaContent = new FileContent("application/zip", filePath);
        File file = service.files().create(fileMetadata, mediaContent)
            .setFields("id, parents")
            .execute();
        
        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
        	  @Override
        	  public void onFailure(GoogleJsonError e,
        	                        HttpHeaders responseHeaders)
        	      throws IOException {
        	    // Handle error
        	    System.err.println(e.getMessage());
        	  }

        	  @Override
        	  public void onSuccess(Permission permission,
        	                        HttpHeaders responseHeaders)
        	      throws IOException {
        	    System.out.println("Permission ID: " + permission.getId());
        	  }
        	};
        
        BatchRequest batch = service.batch();
        Permission domainPermission = new Permission()
        	    .setType("domain")
        	    .setRole("reader")
        	    .setDomain("google.com");
        service.permissions().create(file.getId(), domainPermission)
        .setFields("id")
        .queue(batch, callback);

    batch.execute();
        System.out.println("File ID: " + file.getId());
        return "https://drive.google.com/file/d/"+file.getId()+"/view?usp=sharing";
    }
}
