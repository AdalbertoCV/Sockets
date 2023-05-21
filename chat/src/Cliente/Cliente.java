package Cliente;
public class Cliente {
    public static void main(String[] args){
        GUI gui = new GUI();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(gui, modelo);
        gui.setControlador(controlador);
        modelo.setControlador(controlador);

        controlador.iniciar();
    }
}
