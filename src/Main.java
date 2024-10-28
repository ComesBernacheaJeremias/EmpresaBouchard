import UI.Principal;
import db.Conexion;


public class Main {


    public static void main(String[] args) {

        Conexion.conectarFireBase();

        Principal ventana = new Principal();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);


    }
}