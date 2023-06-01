// Autores: Adalberto Cerrillo Vázquez, Elliot Axel Noriega
// Version: 1.0

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
    //elementos necesarios para crear la interfaz
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

    // inicializamos los componentes de la interfaz
    public GUI() {
        inicializarComponentes();
    }

    // Se inicializan componentes, se dan layouts, tamaños y funcionalidad de seleccion de archivos
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
        // Se crea un scroll para que se pueda deslizar y ver correctamente la pantalla
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
            // agregamosla funcionalidad al seleccionador de archivos
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

    // se agrega un mensaje a la pantalla
    public void agregarMensaje(String mensaje) {
        pantalla.append(mensaje + "\n");
    }

    // se limpia la entrada para que no se quede el mensaje despues del envio
    public void limpiarEntrada() {
        fldmsj.setText("");
    }

    // se asigna el controlador a la interfaz
    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    // mostramos nuestra ventana
    public void mostrar() {
        this.setVisible(true);
    }

    // inicializamos las acciones de los botones
    public void inicializar() {
        enviar.setActionCommand(COMANDO);
        enviar.addActionListener(controlador);
    }

    // se obtiene el mensaje del txtfield
    public String getMensaje() {
        return fldmsj.getText();
    }
}