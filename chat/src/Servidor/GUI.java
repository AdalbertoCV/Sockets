// Autores: Adalberto Cerrillo Vázquez, Elliot Axel Noriega
// Version: 1.0

package Servidor;

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

public class GUI extends JFrame {
    // componentes necesarios para la interfaz
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

    // se inicializan los componentes de la interfaz y se dan layouts y tamaños
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
        this.add(enviar, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        botones.add(enviar);
        this.add(botones, BorderLayout.SOUTH);
    }

    // se agrega un mensaje a la pantalla
    public void agregarMensaje(String mensaje) {
        pantalla.append(mensaje + "\n");
    }

    // se limpia la entrada luego del envio de un mensaje
    public void limpiarEntrada() {
        fldmsj.setText("");
    }

    // se asigna el controlador correspondiente
    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    // mostramos nuestra ventana
    public void mostrar() {
        this.setVisible(true);
    }

    // inicializamos la funcionalidad de los botones
    public void inicializar() {
        enviar.setActionCommand(COMANDO);
        enviar.addActionListener(controlador);
    }

    // obtenemos el mensaje del txtfld
    public String getMensaje() {
        return fldmsj.getText();
    }
}
