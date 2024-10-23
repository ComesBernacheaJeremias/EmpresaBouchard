package UI;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.util.Arrays;

public class Principal extends JFrame {
    private JPanel Principal;
    private JPanel Panel2;
    private JButton button1;
    private JFormattedTextField textoEscrito;
    private JLabel Label;
    private JPanel JPanelBtns;
    private JPanel JPanelResult;
    private JLabel LabelImg;
    private JButton buscarButton;
    private JTextField TfApellido;
    private JLabel Titulo;
    private JPanel PanelDatos;
    private JTextField TfNacimiento;
    private JTextField TfMuerte;
    private JPanel PanelResultDatos;
    private JLabel ResultApellido;
    private JLabel ResultNacimiento;
    private JLabel ResultMuerte;
    private JFormattedTextField FormattedTFNaci;
    private JFormattedTextField FormateddTexFieldMuerte;
    JFileChooser fileChooser = new JFileChooser();




    // Constructor de la clase Principal
    public Principal() {
        // Configurar la ventana
        setTitle("Mi Ventana Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(Principal);

        System.out.println("entro en Principañl");

        createUIComponents();
        PanelDatos.add(FormattedTFNaci);
        PanelDatos.add(FormateddTexFieldMuerte);

        System.out.println("primero" + FormattedTFNaci);
        System.out.println("primero" + FormateddTexFieldMuerte);
        System.out.println("primero" + FormattedTFNaci.getText());
        System.out.println("primero" + FormateddTexFieldMuerte.getText());
        System.out.println("primero" + FormattedTFNaci.getValue());
        System.out.println("primero" + FormateddTexFieldMuerte.getValue());






        // Agregar un ActionListener al botón para capturar el texto
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el texto del campo de texto
                String nombre = textoEscrito.getText();
                // Establecer el texto en la etiqueta
                Label.setText(nombre);
                String apellido = TfApellido.getText();
                ResultApellido.setText(apellido);



                Object nacimientoValue = FormattedTFNaci.getValue();
                Object muerteValue = FormateddTexFieldMuerte.getValue();
                System.out.println(nacimientoValue);
                System.out.println(muerteValue);
                System.out.println(FormattedTFNaci.getText());
                System.out.println(FormateddTexFieldMuerte.getText());

                System.out.println(FormattedTFNaci.getValue().toString());
                System.out.println(FormateddTexFieldMuerte.getValue().toString());

                if(nacimientoValue != null && muerteValue != null) {
                    ResultNacimiento.setText(nacimientoValue.toString());
                    ResultMuerte.setText(muerteValue.toString());
                }else {System.out.println("el valor es nulooo");}

            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(null);

                // Verificar si el usuario seleccionó un archivo (botón "Aceptar" en el diálogo)
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Obtener el archivo seleccionado por el usuario
                    File selectedFile = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());

// Redimensionar la imagen para ajustarla al tamaño del JLabel
                    Image image = imageIcon.getImage(); // Obtener la imagen del ImageIcon
                    Image newImage = image.getScaledInstance(LabelImg.getWidth(), LabelImg.getHeight(), Image.SCALE_SMOOTH); // Redimensionar la imagen
                    imageIcon = new ImageIcon(newImage); // Crear un nuevo ImageIcon con la imagen redimensionada


                    // Mostrar la imagen en el JLabel
                    LabelImg.setIcon(imageIcon);


                }
            }

        });
    }




    // Método que se usa para personalizar los componentes en el GUI Designer (si usas un IDE como IntelliJ)
    private void createUIComponents() {
        // Aquí podrías personalizar tus componentes si lo deseas
    System.out.println("entro en createUIComponents");
        //JFormattedTextField FormattedNaci = null;
        FormattedTFNaci = null;
        FormateddTexFieldMuerte = null;

        try {
            // Crear un MaskFormatter para la fecha en formato dd/MM/yyyy
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            //dateMask.setPlaceholderCharacter(' ');

            // Crea el JFormattedTextField con el MaskFormatter
            FormattedTFNaci = new JFormattedTextField(dateMask);
            FormateddTexFieldMuerte = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}
