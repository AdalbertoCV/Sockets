package Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controlador implements ActionListener {
    protected GUI gui;
    protected Modelo modelo;
    protected String COMANDO = "ENVIAR";

    public Controlador(GUI g, Modelo m) {
        this.gui = g;
        this.modelo = m;
    }

    public void iniciar() {
        gui.mostrar();
        gui.inicializar();
        modelo.conectar();
        gui.agregarMensaje("Conectado al Servidor!");
        modelo.crearFlujos();
        modelo.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(COMANDO)) {
            String mensaje = gui.getMensaje();
            modelo.enviarMensaje(mensaje);
            gui.limpiarEntrada();
        }
    }

    public void agregarMensaje(String mensaje) {
        gui.agregarMensaje(mensaje);
    }

    public void enviarArchivo(File archivo) {
        modelo.enviarArchivo(archivo);
        gui.agregarMensaje("Archivo enviado: " + archivo.getAbsolutePath());
    }
}