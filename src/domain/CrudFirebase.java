package domain;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import db.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CrudFirebase {
    CollectionReference reference;
    static Firestore db;

    public static boolean agregar(String coleccion, String documento, Map<String, Object> data){
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Agregado correctamente");
            return true;
        }catch (Exception e){ System.out.println("No se pudo agregar");}
        return false;
    }

    public static boolean actualizar(String coleccion, String documento, Map<String, Object> data){
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.update(data);
            System.out.println("Actualizado correctamente");
            return true;
        }catch (Exception e){ System.out.println("No se pudo Actualizar");}
        return false;
    }

    public static boolean eliminar(String coleccion, String documento){
        db = FirestoreClient.getFirestore();
        try {
            DocumentReference docRef = db.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.delete(Precondition.NONE);
            System.out.println("Eliminado correctamente");
            return true;
        }catch (Exception e){ System.out.println("No se pudo Eliminar");}
        return false;
    }

    public static void cargarTablaPersona(JTable table){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Nacimiento");
        model.addColumn("Muerte");
        try {
            CollectionReference personas = Conexion.db.collection("persona");
            ApiFuture<QuerySnapshot> querySnap = personas.get();
            for(DocumentSnapshot document: querySnap.get().getDocuments()){
                model.addRow(new Object[]{
                        document.getString("Nombre"),
                        document.getString("Apellido"),
                        document.getString("Nacimiento"),
                        document.getString("Muerte")
                });
            }
        }catch (InterruptedException | ExecutionException e){
            System.err.println("Error: " + e);
        }
    }

    public static String subirImagen(File selectedFile) {
        String pathLocalImagen = selectedFile.getAbsolutePath();


        try {
            // Configura las credenciales y el cliente de almacenamiento
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("empresabouchard-firebase.json"));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();


            // Generar un ID único para el archivo en Storage
            String uniqueId = UUID.randomUUID().toString();
            String nombreArchivo = "imagenes_contactos/" + uniqueId + ".jpg";

            // Subir la imagen a Firebase Storage
            BlobInfo blobInfo = BlobInfo.newBuilder(StorageClient.getInstance().bucket().getName(), nombreArchivo)
                    .setContentType("image/png")
                    .build();

/*
            // Obtén el cliente de Storage y sube la imagen usando el array de bytes
            Storage storage = StorageOptions.getDefaultInstance().getService();
            storage.create(blobInfo, new FileInputStream(pathLocalImagen));
*/

            // Lee el archivo como array de bytes
            byte[] data = Files.readAllBytes(selectedFile.toPath());

            // Sube el archivo
            storage.create(blobInfo, data);

            System.out.println("Imagen subida correctamente " + nombreArchivo);
            System.out.println("setpath " + pathLocalImagen);
            return nombreArchivo;  // Retorna la ruta de la imagen en Storage

        } catch (IOException e) {
            System.out.println("Error al subir la imagen: " + e.getMessage());
            return null;
        }
    }
}
