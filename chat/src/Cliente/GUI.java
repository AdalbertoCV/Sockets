package Cliente;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame {
    protected JTextArea pantalla;
    protected JLabel lblmsj;
    protected JTextField fldmsj;
    protected JButton enviar;
    protected JButton seleccionarArchivo;
    protected JPanel encabezado;
    protected JPanel botones;
    protected Controlador controlador;
    protected String COMANDO = "ENVIAR";
    protected JFileChooser fileChooser;
    protected JScrollPane scroll;

    public GUI() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setSize(400, 400);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        encabezado = new JPanel();
        encabezado.setLayout(new FlowLayout());
        lblmsj = new JLabel("Ingresa un mensaje:");
        encabezado.add(lblmsj);
        fldmsj = new JTextField(10);
        fldmsj.setBounds(20, 50, 20, 50);
        encabezado.add(fldmsj);
        this.add(encabezado, BorderLayout.NORTH);
        pantalla = new JTextArea();
        pantalla.setEditable(false);
        scroll = new JScrollPane(pantalla);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll, BorderLayout.CENTER);
        botones = new JPanel();
        botones.setLayout(new FlowLayout());
        enviar = new JButton("Enviar");
        seleccionarArchivo = new JButton("Seleccionar archivo");
        this.add(enviar, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fileChooser = new JFileChooser();
        seleccionarArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    agregarMensaje("Archivo seleccionado: " + selectedFile.getAbsolutePath());
                    controlador.enviarArchivo(selectedFile);
                }
            }
        });
        botones.add(enviar);
        botones.add(seleccionarArchivo);
        this.add(botones, BorderLayout.SOUTH);
    }

    public void agregarMensaje(String mensaje) {
        pantalla.append(mensaje + "\n");
    }

    public void limpiarEntrada() {
        fldmsj.setText("");
    }

    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    public void mostrar() {
        this.setVisible(true);
    }

    public void inicializar() {
        enviar.setActionCommand(COMANDO);
        enviar.addActionListener(controlador);
    }

    public String getMensaje() {
        return fldmsj.getText();
    }
}