// Autores: Adalberto Cerrillo Vázquez, Elliot Axel Noriega
// Version: 1.0

package Servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controlador implements ActionListener {
    protected GUI gui;
    protected Modelo modelo;
    protected String COMANDO = "ENVIAR";

    // constructor para asignar la interfaz y el modelo a controlar
    public Controlador(GUI g, Modelo m) {
        this.gui = g;
        this.modelo = m;
    }

    // se inicializan todos los elementos necesarios y se abren las conexiones
    public void iniciar() {
        gui.mostrar();
        gui.inicializar();
        modelo.abrirPuerto();
        gui.agregarMensaje("Esperando conexiones...");
        modelo.esperar();
        gui.agregarMensaje("Cliente conectado!");
        modelo.crearFlujos();
        modelo.start();
    }

    // accion de botones para enviar mensajes
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(COMANDO)) {
            String mensaje = gui.getMensaje();
            modelo.enviarMensaje(mensaje);
            gui.limpiarEntrada();
        }
    }


    // se agrega un mensaje a la pantalla de la interfaz
    public void agregarMensaje(String mensaje) {
        gui.agregarMensaje(mensaje);
    }

    // se recibe el archivo y se muestra el contenido en pantalla
    public void recibirArchivo(File archivo) {
        modelo.recibirArchivo(archivo);
        gui.agregarMensaje("Archivo recibido: " + archivo.getAbsolutePath());
    }
}
