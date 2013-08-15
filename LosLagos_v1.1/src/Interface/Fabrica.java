/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;
import Controladores.ControladorCasas;
import Controladores.*;
/**
 *
 * @author santiago
 */
public class Fabrica {
   
    public Fabrica(){
    }
    public IControladorClientes getIControladorClientes(){
        ControladorClientes cE = new ControladorClientes();
        return cE;
    }
    public IControladorCasas getIControladorCasas(){
        ControladorCasas cJ = new ControladorCasas();
        return cJ;
    }
    
    public IControladorDatos getIControladorDatos(){
        ControladorDatos cJ = new ControladorDatos();
        return cJ;
    }
    
    
}
