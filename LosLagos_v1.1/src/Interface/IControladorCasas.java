/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import DataTypes.DataCasa;
import DataTypes.DataPedido;
import java.util.List;

/**
 *
 * @author Santiago
 */
public interface IControladorCasas {
    public List<DataPedido> altaCasa(DataCasa a) throws Exception;
    public void bajaCasa(String id) throws Exception;
    public List<DataPedido> modificarCasa(String idViejo, DataCasa a)throws Exception;
    
    public List<DataCasa> listarCasas();
    public DataCasa obtenerCasa(String id) throws Exception;
    
    public List<String> listarLugares();
    public void ingresarLugar(String lugar)throws Exception;
    public void eliminarLugar(String lugar) throws Exception;
}
