package Interface;

import DataTypes.DataCasa;
import DataTypes.DataCliente;
import DataTypes.DataPedido;
import java.util.List;


public interface IControladorClientes {
    public void cerrarEm();
    
    public void altaCliente(DataCliente c);
    public void bajaCliente(int id) throws Exception;
    public void modificarCliente(DataCliente c)throws Exception;
    
    public List<DataCliente> listarClientes();
    public DataCliente obtenerCliente(int id) throws Exception;
   
    public List<DataCasa> realizarSolicitudes(int idCliente, DataPedido solicitudPedido)throws Exception;
    public List<DataCasa> modificarSolicitudes(int idCliente, DataPedido solicitudPedido)throws Exception;
    public void eliminarSolicitudes(int idCliente, int idSolicitud)throws Exception;
    
    
    public List<DataPedido> obtenerSolicitudes();
    public DataPedido obtenerSolicitud(int idSolicitud) throws Exception;
}
