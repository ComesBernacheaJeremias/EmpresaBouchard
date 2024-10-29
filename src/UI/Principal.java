package UI;


import domain.CrudFirebase;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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
    private JPanel PanelResultDatos;
    private JLabel ResultApellido;
    private JLabel ResultNacimiento;
    private JLabel ResultMuerte;
    private JFormattedTextField FormattedTFNaci;
    private JFormattedTextField FormateddTexFieldMuerte;
    private JFileChooser fileChooser = new JFileChooser();

    // Constructor de la clase Principal
    public Principal() {
        setTitle("Mi Ventana Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(Principal);



        // Agregar ActionListeners
        button1.addActionListener(this::onButton1Click);
        buscarButton.addActionListener(this::onBuscarButtonClick);
    }

    // Método para capturar el texto y establecer en los labels
    private void onButton1Click(ActionEvent e) {
        String nombre = textoEscrito.getText();
        Label.setText(nombre);

        String apellido = TfApellido.getText();
        ResultApellido.setText(apellido);

        String nacimientoValue = (FormattedTFNaci.getValue() != null) ? FormattedTFNaci.getValue().toString() : "NOche";
        String muerteValue = (FormateddTexFieldMuerte.getValue() != null) ? FormateddTexFieldMuerte.getValue().toString() : "asdasd";

        ResultNacimiento.setText(nacimientoValue);
        ResultMuerte.setText(muerteValue);

        guardar(nombre, apellido, nacimientoValue, muerteValue);
    }

    private void guardar(String nombre, String apellido, String nacimiento, String muerte) {
//genera un id para el nombre del documento. esto puedo cambiar
        int id = (int) (Math.random() * 1000000);
        try {
            Map<String, Object> datos = new HashMap<>();
            datos.put("Nombre", nombre);
            datos.put("Apellido", apellido);
            datos.put("Nacimiento", nacimiento);
            datos.put("Muerte", muerte);
            CrudFirebase.agregar("persona", String.valueOf(id), datos);
            JOptionPane.showMessageDialog(null, "Guardado con exito");
        }catch (HeadlessException e){
            System.out.println("No se pudo guardar");
            JOptionPane.showMessageDialog(null, "No se pudo guardar");
        }

    }

    private void actualizar(String nombre, String apellido, String nacimiento, String muerte) {

        //Debo poner el id adecuado lo que puse es un ejemplo
        int id = 1;
        try {
            Map<String, Object> datos = new HashMap<>();
            datos.put("Nombre", nombre);
            datos.put("Apellido", apellido);
            datos.put("Nacimiento", nacimiento);
            datos.put("Muerte", muerte);
            CrudFirebase.agregar("persona", String.valueOf(id), datos);
            JOptionPane.showMessageDialog(null, "Guardado con exito");
        }catch (HeadlessException e){
            System.out.println("No se pudo guardar");
            JOptionPane.showMessageDialog(null, "No se pudo guardar");
        }

    }
    private void eliminar(String nombre, String apellido, String nacimiento, String muerte) {
        //debo poner la persona y el id adecuados
        int id = 1;
        try {

            CrudFirebase.eliminar("persona", String.valueOf(id));
            JOptionPane.showMessageDialog(null, "Guardado con exito");
        }catch (HeadlessException e){
            System.out.println("No se pudo guardar");
            JOptionPane.showMessageDialog(null, "No se pudo guardar");
        }

    }



    // Método para abrir el selector de archivos y mostrar la imagen
    private void onBuscarButtonClick(ActionEvent e) {
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            setScaledImage(selectedFile);
            CrudFirebase.subirImagen(selectedFile);
        }
    }


    // Método para redimensionar y mostrar la imagen seleccionada
    private void setScaledImage(File file) {
        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(LabelImg.getWidth(), LabelImg.getHeight(), Image.SCALE_SMOOTH);
        LabelImg.setIcon(new ImageIcon(newImage));
    }

    // Método para personalizar componentes
    private void createUIComponents() {
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');

            FormattedTFNaci = new JFormattedTextField(dateMask);
            FormateddTexFieldMuerte = new JFormattedTextField(dateMask);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
