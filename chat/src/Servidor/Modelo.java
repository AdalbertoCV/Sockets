package Servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Modelo extends Thread {
    protected Controlador controlador;
    final int PUERTO = 60002;
    protected ServerSocket socket;
    protected Socket socket_cli;
    protected InputStream in;
    protected InputStreamReader isr;
    protected BufferedReader br;
    protected OutputStream os;
    protected OutputStreamWriter osw;
    protected BufferedWriter bw;
    protected String FILES_FOLDER = "files/";

    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    public void abrirPuerto() {
        try {
            socket = new ServerSocket(PUERTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void esperar() {
        try {
            socket_cli = socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void enviarMensaje(String mensaje) {
        try {
            bw.write(mensaje);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String recibirMensaje() {
        try {
            String mensaje = br.readLine();
            return mensaje;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void recibirArchivo(File archivo) {
        try {
            String nombreArchivo = br.readLine();
            File destino = new File(FILES_FOLDER + nombreArchivo);
            FileOutputStream fos = new FileOutputStream(destino);
            byte[] buffer = new byte[8192];
            int count;
            while ((count = in.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void run() {
        while (true) {
            String mensaje = recibirMensaje();
            controlador.agregarMensaje(mensaje);
        }
    }
}
