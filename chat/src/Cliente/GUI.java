package Cliente;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class GUI extends JFrame{
    protected JTextArea pantalla;
    protected JLabel lblmsj;
    protected JTextField fldmsj;
    protected JButton enviar;
    protected JPanel encabezado;
    protected Controlador controlador;
    protected String COMANDO ="ENVIAR";

    public GUI(){
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
        fldmsj.setBounds(20,50, 20,50);
        encabezado.add(fldmsj);
        this.add(encabezado, BorderLayout.NORTH);
        pantalla = new JTextArea();
        this.add(pantalla, BorderLayout.CENTER);
        enviar = new JButton("enviar");
        this.add(enviar,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void agregarMensaje(String mensaje){
        pantalla.append(mensaje + "\n");
    }

    public void limpiarEntrada(){
        fldmsj.setText("");
    }

    public void setControlador(Controlador c){
        this.controlador = c;
    }

    public void mostrar(){
        this.setVisible(true);
    }

    public void inicializar(){
        enviar.setActionCommand(COMANDO);
        enviar.addActionListener(controlador);
    }

    public String getMensaje(){
        return fldmsj.getText();
    }
}
