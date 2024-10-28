package db;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


import java.io.FileInputStream;
import java.io.IOException;


public class Conexion {

    public static Firestore db;

    public static void conectarFireBase(){
        try {
            FileInputStream sa = new FileInputStream("empresabouchard-firebase.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(sa))
                    .build();

            FirebaseApp.initializeApp(options);


            db = FirestoreClient.getFirestore();
            System.out.println("Exito al conectar con Firebase");

        }catch (IOException e) {
            System.out.println("Error en ConectarFirebase");
            throw new RuntimeException(e);

        }

    }


}
