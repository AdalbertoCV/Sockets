// Autores: Adalberto Cerrillo Vázquez, Elliot Axel Noriega
// Version: 1.0

package Cliente;

// Clase para inicializar todos los componentes y correr el programa
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
