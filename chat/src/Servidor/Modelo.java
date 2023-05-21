package Servidor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Modelo extends Thread{
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

    public void setControlador(Controlador c){
        this.controlador = c;
    }

    public void abrirPuerto(){
        try {
            socket = new ServerSocket(PUERTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void esperar(){
        try {
            socket_cli = socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearFlujos(){
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

    public void enviarMensaje(String mensaje){
       try {
            bw.write(mensaje);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public String recibirMensaje(){
        try {
            String mensaje = br.readLine();
            return mensaje;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void run(){
       while(true){
            String mensaje = recibirMensaje();
            controlador.agregarMensaje(mensaje);
       } 
    }
}
