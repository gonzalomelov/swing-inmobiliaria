/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;


import DataTypes.DataCasa;
import DataTypes.DataCliente;
import DataTypes.DataPedido;
import DataTypes.DataZona;
import Interface.IControladorClientes;
import Objetos.Casa;
import Objetos.Cliente;
import Objetos.Pedido;
import Objetos.Zona;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Santiago
 */
public class ControladorClientes implements IControladorClientes{

    // CONSTRUCTOR
    public ControladorClientes(){
      
    }

	// Cambiar de lugar
    @Override
	public void cerrarEm(){
        ManejadorDatos.getInstance().closeEm();
    }

    //operaciones sobre los clientes
    
    @Override
    public void altaCliente(DataCliente c) {        
        //Caso de uso, dar de alta a un cliente.
        Cliente cli = new Cliente(c);
        ManejadorDatos.getInstance().persist(cli);
    }

    @Override
    public void bajaCliente(int id) throws Exception {
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Cliente c  = ManejadorDatos.getInstance().getEm().find(Cliente.class,id);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();

        if (c==null){
            throw new Exception("No existe el cliente en el Sistema");
        }else{

            ControladorDatos iCD = new ControladorDatos();
            //borramos los datos de la historia!! recorremos todas las solicitudes y tomamos las casas para cada una
            for (Pedido p :c.getPedidos()){
                for (Casa casa:p.getCasas()){
                    try{
                        iCD.desMarcarCasa(id, casa.getId());
                    }catch(Exception e){
                        System.out.print(e.getMessage());
                    }
                }

            }


            //antes de eliminarlo, tenemos que sacarle las casas a las solicitudes, ya que sino se borrarian
            System.out.print(c.getPedidos().size());
            for (Pedido p :c.getPedidos()){

                this.eliminarSolicitudes(c.getId(), p.getId());

            }

            c.setPedidos(new ArrayList<Pedido>());
            ManejadorDatos.getInstance().persist(c);

            try{
                ManejadorDatos.getInstance().getEm().getTransaction().begin();
                ManejadorDatos.getInstance().getEm().remove(c);
                ManejadorDatos.getInstance().getEm().getTransaction().commit();
             }catch(Exception e){

             }
            }
    }

    @Override
    public void modificarCliente(DataCliente c) throws Exception{

        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Cliente cli  = ManejadorDatos.getInstance().getEm().find(Cliente.class,c.getId());
        if (cli==null){
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
            throw new Exception("No existe el cliente en el Sistema");
        }else{
            cli.setNombre(c.getNombre());
            cli.setApellido(c.getApellido());
            cli.setDireccion(c.getDireccion());
            cli.setEmail(c.getEmail());
            cli.setTelefono(c.getTelefono());
            cli.setInf_adicional(c.getInf_adicional());
            cli.setCelular(c.getCelular());
            
            ManejadorDatos.getInstance().getEm().persist(cli);
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
        }        
    }
    

    @Override
    public List<DataCasa> realizarSolicitudes(int idCliente, DataPedido solicitudPedido) throws Exception {
        //PRECONDICIONES
        //LAS CASAS DEL SISTEMA ESTAN CORRECTAS
        
        //primero validamos el id del cliente
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Cliente cli  = ManejadorDatos.getInstance().getEm().find(Cliente.class,idCliente);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
        if (cli==null){
            throw new Exception("No existe el cliente en el Sistema");
        }else{
            //tenemos el cliente validado.
            String sql = "select c from Casa c;" ;
            List<Casa> list = (List<Casa>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
          
            List<Casa> mach = new ArrayList<Casa>();
            
            Pedido p = new Pedido(solicitudPedido);
            if (list!=null){
                //Para casa casa, vemos si cumple con los requisitos                            
                for (Casa ca:list){   
                    if (cumplePedido(ca,solicitudPedido)){
                        mach.add(ca);//agregamos la casa a la lista de add
                        ca.getPedidos().add(p);
                        ManejadorDatos.getInstance().persist(ca);
                    }
                }
         }
            
         //tenemos que retornar la lista mach, primero pasarla a dataCasa
         //tambien tenemos que guardar el pedido
            
        p.setCasas(mach);
        p.setCliente(cli);

        cli.getPedidos().add(p);//***********************************************
        ManejadorDatos.getInstance().persist(p);

        return ListcasaToDataCasa(mach);     
        }
    }


    @Override
    public void eliminarSolicitudes(int idCliente, int idSolicitud) throws Exception {
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Cliente c  = ManejadorDatos.getInstance().getEm().find(Cliente.class,idCliente);
        if (c==null){
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
            throw new Exception("No existe el cliente en el Sistema");
        }else{
            ManejadorDatos.getInstance().getEm().getTransaction().commit();

            //ahora buscamos el pedido, el pedido no se identtifica por el cliente
            ManejadorDatos.getInstance().getEm().getTransaction().begin();
            Pedido p  = ManejadorDatos.getInstance().getEm().find(Pedido.class,idSolicitud);
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
            if (p==null){
                throw new Exception("No existe la Solicitud en el sistema");
            }else{
                 if (p.getCliente().getId()!=idCliente)
                     throw new Exception("El cliente no tiene asociada dicha solicitud");
                 else{

                     if (p.getCasas()!=null){
                         for (Casa ca1: p.getCasas()){
                             List<Pedido> list = ca1.getPedidos();
                             Pedido aborrar = null;
                             for (Pedido aa:list){
                                if (aa.getId()==p.getId())
                                    aborrar=aa;
                             }
                             list.remove(aborrar);
                             ca1.setPedidos(list);

                             ManejadorDatos.getInstance().persist(ca1);
                         }
                     }
                     
                     ManejadorDatos.getInstance().getEm().getTransaction().begin();
                     Cliente cl  = ManejadorDatos.getInstance().getEm().find(Cliente.class,p.getCliente().getId());
                     ManejadorDatos.getInstance().getEm().getTransaction().commit();
            
                     cl.setPedidos(new ArrayList<Pedido>());
                     ManejadorDatos.getInstance().persist(cl);
                     
                     p.setCliente(null);//PONGO NULL PORQUE SINO EL CASACADE BORRA TODO!!!!
                     p.setCasas(new ArrayList<Casa>());
                     //p.setZonas(null);

                     ManejadorDatos.getInstance().persist(p);

                     ManejadorDatos.getInstance().getEm().getTransaction().begin();   
                     ManejadorDatos.getInstance().getEm().remove(p);
                     ManejadorDatos.getInstance().getEm().getTransaction().commit();
                 
                 }
                }
            
        
        }
        
       
        
    }

    
    public List<DataCasa> ListcasaToDataCasa(List<Casa> mach) {
        List<DataCasa> ret = new ArrayList<DataCasa>();
        
        if (mach!=null){
                for (Casa c :mach){
                    DataCasa data = casaToDataCasa(c);            
                    ret.add(data);
                }               
        }
        return ret;
    }


    //funcion que retorna true si la casa cumple con el pedido

    
    @Override
    public List<DataCliente> listarClientes() {
       
        ManejadorDatos.getInstance().getEm().getTransaction().begin();   
        String sql = "select c from Cliente c;" ;
        List<Cliente> list = (List<Cliente>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
        List<DataCliente> ret= new ArrayList<DataCliente>();
        if (list!=null){
            for (Cliente c:list){
                DataCliente d = new DataCliente();
                d.setApellido(c.getApellido());
                d.setCelular(c.getCelular());
                d.setDireccion(c.getDireccion());
                d.setEmail(c.getEmail());
                d.setId(c.getId());
                d.setInf_adicional(c.getInf_adicional());
                d.setNombre(c.getNombre());
                d.setTelefono(c.getTelefono());
                d.setInf_adicional(c.getInf_adicional());
                //estos van sin el pedido
                ret.add(d);
            }
        }
        return ret;
        
    }

    @Override
    public DataCliente obtenerCliente(int id) throws Exception {
        
        DataCliente d = new DataCliente();
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Cliente c  = ManejadorDatos.getInstance().getEm().find(Cliente.class,id);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
         
        if (c==null){
            throw new Exception("No existe el cliente en el Sistema");
        }else{
            d.setApellido(c.getApellido());
            d.setCelular(c.getCelular());
            d.setDireccion(c.getDireccion());
            d.setEmail(c.getEmail());
            d.setId(c.getId());
            d.setInf_adicional(c.getInf_adicional());
            d.setNombre(c.getNombre());
            d.setTelefono(c.getTelefono());
            d.setInf_adicional(c.getInf_adicional());
                
            ControladorCasas cc = new ControladorCasas();
            List<DataPedido> dpedidos = new ArrayList<DataPedido>();
                
            if (c.getPedidos()!=null){
                for (Pedido p:c.getPedidos()){
                    dpedidos.add(cc.PedidoToDataPedido(p));
                }
            }
            d.setPedidos(dpedidos);
    }
    
        return d;
    }

    @Override
    public List<DataCasa> modificarSolicitudes(int idCliente, DataPedido solicitudPedido) throws Exception {
        this.eliminarSolicitudes(idCliente, solicitudPedido.getId());
        return this.realizarSolicitudes(idCliente, solicitudPedido);
    }

    


    @Override
    public List<DataPedido> obtenerSolicitudes() {
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();   
        String sql = "select p from Pedido p;" ;
        List<Pedido> list = (List<Pedido>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
        List<DataPedido> ret = new ArrayList<DataPedido>();
        
        ControladorCasas cC = new ControladorCasas();
        for (Pedido p:list){
            DataPedido data = cC.PedidoToDataPedido(p);
            ret.add(data);
        }
        return ret;
    }

    @Override
    public DataPedido obtenerSolicitud(int idSolicitud) throws Exception {
        //obtenemos la solicitud
        DataPedido d = new DataPedido();
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Pedido p  = ManejadorDatos.getInstance().getEm().find(Pedido.class,idSolicitud);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
         
        if (p==null){
            throw new Exception("No existe el pedido en el Sistema");
        }else{
            ControladorCasas cC = new ControladorCasas();
            d = cC.PedidoToDataPedido(p);
        }
        return d;
    }

    public DataCasa casaToDataCasa(Casa c) {
          
        DataCasa data = new DataCasa();
        
        data.setBanos(c.getBanos());
        data.setDireccion(c.getDireccion());
        data.setDireccionDueno(c.getDireccionDueno());
        data.setDormitorios(c.getDormitorios());
        data.setEmail(c.getEmail());
        data.setEspecificacion(c.getEspecificacion());
        data.setEstado(c.getEstado());
        data.setId(c.getId());
        data.setNombre(c.getNombre());
        data.setPH(c.isPH());
        data.setPU(c.isPU());
        data.setPrecioV(c.getPrecioV());
        data.setPrecioA(c.getPrecioA());
        data.setSuperficie(c.getSuperficie());
        data.setTelefono(c.getTelefono());
        data.setTipo(c.getTipo());
        data.setZona(c.getZona());
        data.setAceptaBanco(c.isAceptaBanco());
        data.setTieneF(c.isTieneF());
        data.setPrecedencia(c.getPrecedencia());
        data.setNorteG(c.isNorte());
        data.setApellido(c.getApellido());  
        data.setCelular(c.getCelular());
        
    return data;
    }
    
    
    public boolean cumplePedido(Casa ca,DataPedido p){
        boolean cumple=false;

        if ((p.getZonas()==null) | (p.getZonas().isEmpty())){
            cumple=true;
        }else{
            for (DataZona data: p.getZonas()){
                if (ca.getZona().equals(data.getZona())){
                    if ((ca.isNorte() & data.isNorteG()) | (!ca.isNorte() & data.isSurG()))
                        cumple=true;
                }
            }
        }

        if (!cumple)
            return false;
        //cumple vale true si una zona cumple con la casa.

        /*
        if (!(  ( ((p.getBanosH()==0)|(p.getBanosH()==3)) |(ca.getBanos()>=p.getBanosL() & ca.getBanos()<=p.getBanosH()) )
                & (  ((p.getDormitoriosH()==0) | (p.getDormitoriosH()==4)) |(ca.getDormitorios()>=p.getDormitoriosL() & ca.getDormitorios()<=p.getDormitoriosH())  )
                    & (p.getTipo().equals(ca.getTipo()))    ))
            return false;
        */
        
        if (!((p.getDormitoriosL()==0 && p.getDormitoriosH()==0) ||
            (p.getDormitoriosH()==4 && p.getDormitoriosL()<=ca.getDormitorios()) ||
            (p.getDormitoriosL()<=ca.getDormitorios() && p.getDormitoriosH()>=ca.getDormitorios())))
            
            return false;
        
        if (!((p.getBanosL()==0 && p.getBanosH()==0) ||
            (p.getBanosH()==3 && p.getBanosL()<=ca.getBanos()) ||
            (p.getBanosL()<=ca.getBanos() && p.getBanosH()>=ca.getBanos())))
            
            return false;
        
        
        if (!p.getTipo().equals(ca.getTipo()))
            return false;
        
        
        //hay que controlar el ph y el pu
        if (!(p.getPH().equals("") | (p.getPH().equals("si")&ca.isPH()) | (p.getPH().equals("no")&!ca.isPH())))
            return false;
        if (!(p.getPU().equals("") | (p.getPU().equals("si")&ca.isPU()) | (p.getPU().equals("no")&!ca.isPU())))
            return false;

        if (!((p.getSuperficieL()==0 && p.getSuperficieH()==0) |
             (p.getSuperficieL()==0 && ca.getSuperficie()<=p.getSuperficieH()) |
             (p.getSuperficieH()==0 && ca.getSuperficie()>=p.getSuperficieL()) |
             (ca.getSuperficie()<=p.getSuperficieH() && ca.getSuperficie()>=p.getSuperficieL())))
            return false;
        
        //ahora vemos los otros factores
        /*if ( p.getEstado().equals("alquiler") | ( p.getEstado().equals("ambos"))) {
            if (!((ca.getEstado().equals("alquiler")| ca.getEstado().equals("ambos") )& ( (p.getPrecioAH()==0)|((ca.getPrecioA() >= p.getPrecioAL()) & (ca.getPrecioA()<=p.getPrecioAH())))) )
                return false;
        }else if ( p.getEstado().equals("venta") | ( p.getEstado().equals("ambos"))){
            if (!((ca.getEstado().equals("venta")| ca.getEstado().equals("ambos") )&  ( (p.getPrecioVH()==0)|((ca.getPrecioV() >= p.getPrecioVL()) & (ca.getPrecioV()<=p.getPrecioVH())) ))  )
                return false;
        }*/

        if (ca.getEstado().equals("alquiler")&p.getEstado().equals("venta"))
            return false;

        if (ca.getEstado().equals("venta")&p.getEstado().equals("alquiler"))
            return false;
            
        if (ca.getEstado().equals("alquiler")) {
            if (!((p.getPrecioAH()==0 & p.getPrecioAL()==0) |
                (p.getPrecioAH()==0 & p.getPrecioAL()<=ca.getPrecioA()) |
                (p.getPrecioAL()==0 & p.getPrecioAH()>=ca.getPrecioA()) |
                (ca.getPrecioA() >= p.getPrecioAL()) & (ca.getPrecioA()<=p.getPrecioAH())))
                
                return false;
        }
        
        if (ca.getEstado().equals("venta")){
            if (!((p.getPrecioVH()==0 & p.getPrecioVL()==0) |
                (p.getPrecioVH()==0 & p.getPrecioVL()<=ca.getPrecioV()) |
                (p.getPrecioVL()==0 & p.getPrecioVH()>=ca.getPrecioV()) |
                ((ca.getPrecioV() >= p.getPrecioVL()) & (ca.getPrecioV()<=p.getPrecioVH()))))
                
                return false;
        }
        
        
        if (ca.getEstado().equals("ambos")){
            if (!(
				((p.getEstado().equals("venta")|p.getEstado().equals("ambos"))&
                ((p.getPrecioVH()==0 & p.getPrecioVL()==0) |
                (p.getPrecioVH()==0 & p.getPrecioVL()<=ca.getPrecioV()) |
                (p.getPrecioVL()==0 & p.getPrecioVH()>=ca.getPrecioV()) |
                ((ca.getPrecioV() >= p.getPrecioVL()) & (ca.getPrecioV()<=p.getPrecioVH()))))
                || 
                ((p.getEstado().equals("alquiler")|p.getEstado().equals("ambos"))&
                ((p.getPrecioAH()==0 & p.getPrecioAL()==0) |
                (p.getPrecioAH()==0 & p.getPrecioAL()<=ca.getPrecioA()) |
                (p.getPrecioAL()==0 & p.getPrecioAH()>=ca.getPrecioA()) |
                ((ca.getPrecioA() >= p.getPrecioAL()) & (ca.getPrecioA()<=p.getPrecioAH()))))
				))
                return false;
        }
        
        
        //verificamos el tema del banco
        if (p.getAceptaBanco().equals("si") & !ca.isAceptaBanco())
            return false;

        if (p.getConF().equals("si") & !ca.isTieneF())
            return false; 


        return true;
    }
}

