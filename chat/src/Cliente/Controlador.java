// Autores: Adalberto Cerrillo Vázquez, Elliot Axel Noriega
// Version: 1.0

package Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// controlador para realziar la comunicacion entre modelo y vista
public class Controlador implements ActionListener {
    protected GUI gui;
    protected Modelo modelo;
    protected String COMANDO = "ENVIAR";

    // constructor para asignar la vista y el modelo
    public Controlador(GUI g, Modelo m) {
        this.gui = g;
        this.modelo = m;
    }

    // se inicializa todo lo necesario para la ejecucion
    public void iniciar() {
        gui.mostrar();
        gui.inicializar();
        modelo.conectar();
        gui.agregarMensaje("Conectado al Servidor!");
        modelo.crearFlujos();
        modelo.start();
    }

    // acciones de boton para el envio de mensajes
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(COMANDO)) {
            String mensaje = gui.getMensaje();
            modelo.enviarMensaje(mensaje);
            gui.limpiarEntrada();
        }
    }

    // agregamos mensajes a la pantalla
    public void agregarMensaje(String mensaje) {
        gui.agregarMensaje(mensaje);
    }

    // Se envia el archivo y se confirma en pantalla.
    public void enviarArchivo(File archivo) {
        modelo.enviarArchivo(archivo);
        gui.agregarMensaje("Archivo enviado: " + archivo.getAbsolutePath());
    }
}