// Autores: Adalberto Cerrillo Vázquez, Elliot Axel Noriega
// Version: 1.0

package Cliente;

import java.io.*;
import java.net.Socket;

// hilo para la comunicacion cliente/servidor
public class Modelo extends Thread {
    protected Controlador controlador;
    final int PUERTO = 60002;
    protected Socket socket_cli;
    protected InputStream in;
    protected InputStreamReader isr;
    protected BufferedReader br;
    protected OutputStream os;
    protected OutputStreamWriter osw;
    protected BufferedWriter bw;
    final String HOST = "localhost";

    // Se asigna el controlador a nuestro modelo
    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    // el cliente se conecta al servidor mediante el socket
    public void conectar() {
        try {
            socket_cli = new Socket(HOST, PUERTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // se crean los flujos de intercambio de mensajes entrada/salida
    public void crearFlujos() {
        try {
            in = socket_cli.getInputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            os = socket_cli.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metodo para enviar mensaje al servidor
    public void enviarMensaje(String mensaje) {
        try {
            bw.write(mensaje);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // metodo para recibir un mensaje del servidor
    public String recibirMensaje() {
        try {
            String mensaje = br.readLine();
            return mensaje;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    // metodo para enviar un archivo en un arreglo de bytes al servidor
    public void enviarArchivo(File archivo) {
        try {
            FileInputStream fis = new FileInputStream(archivo);
            byte[] buffer = new byte[8192];
            int count;
            bw.write(archivo.getName());
            bw.newLine();
            bw.flush();
            while ((count = fis.read(buffer)) > 0) {
                os.write(buffer, 0, count);
            }
            os.flush();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // ciclo infinito para mantener abierta la comunicacion hasta que se cierren las ventanas.
    public void run() {
        while (true) {
            String mensaje = recibirMensaje();
            controlador.agregarMensaje(mensaje);
        }
    }
}