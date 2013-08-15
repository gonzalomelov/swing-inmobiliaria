/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import DataTypes.DataCasa;
import DataTypes.DataCliente;
import DataTypes.DataPedido;
import DataTypes.DataZona;
import Interface.IControladorCasas;
import Objetos.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santiago
 */
public class ControladorCasas implements IControladorCasas{

    public ControladorCasas(){
    }

    @Override
    public List<DataPedido> altaCasa(DataCasa a) throws Exception{
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Casa c2  = ManejadorDatos.getInstance().getEm().find(Casa.class,a.getId());
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
        if (c2!=null){
            throw new Exception("Ya existe una casa en el sistema con identificador: "+a.getId());
        }
        
        Casa casa = new Casa(a);
        ManejadorDatos.getInstance().persist(casa);
        
        // tenemos que recorrer los pedidos y ver si la casa cumple con los mismos!
        // y si cumple la agregamos al pedido y retornamos una lista de los mismos
        List<DataPedido> ret = new ArrayList<DataPedido>();
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        String sql = "select p from Pedido p;" ;
        List<Pedido> list = (List<Pedido>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
        ManejadorDatos.getInstance().getEm().getTransaction().commit();



        if (list!=null){
            for (Pedido p:list){
                if (this.cumplePedido(casa, p)){
                    //la casa cumple con un pedido, entonces la agregamos al pedido, y persistimos el pedido
                    p.getCasas().add(casa);
                    ManejadorDatos.getInstance().persist(p);
                    DataPedido data = PedidoToDataPedido(p);
                    ret.add(data);
                    casa.getPedidos().add(p);

                }

            }
        }
        return ret;
    }

    @Override
    public void bajaCasa(String id) throws Exception{
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Casa c  = ManejadorDatos.getInstance().getEm().find(Casa.class,id);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();

        if (c==null){
            
            throw new Exception("No existe la casa en el Sistema");
        }else{

            ControladorDatos iCD = new ControladorDatos();
            //borramos la historia cuando se elimina el cliente
            for (Pedido p: c.getPedidos()){
                //tratamos de desmarcar para cada uno
                try{
                    iCD.desMarcarCasa(p.getCliente().getId(), id);
                }catch(Exception e){

                }
            }

               if (c.getPedidos()!=null){
                     for (Pedido p: c.getPedidos()){

                         List<Casa> cas = p.getCasas();
                         Casa borrame = null;
                         for (Casa casa2: cas){
                            if (casa2.getId().equals(c.getId())){
                                borrame = casa2;
                            }
                         }
                         cas.remove(borrame);
                         p.setCasas(cas);
                         ManejadorDatos.getInstance().persist(p);
                     }
               }

            c.setPedidos(new ArrayList<Pedido>());
            ManejadorDatos.getInstance().persist(c);

            ManejadorDatos.getInstance().getEm().getTransaction().begin();
            ManejadorDatos.getInstance().getEm().remove(c);
            ManejadorDatos.getInstance().getEm().getTransaction().commit();      
        }
               
    }

    @Override
    public List<DataPedido> modificarCasa(String idViejo,DataCasa a) throws Exception {
        //pueden cambiar los pedidos..
        this.bajaCasa(idViejo);
        return this.altaCasa(a);    
    }
    
    
    //*****FUNCIONES AUXILIARES******//
    
    
    public boolean cumplePedido(Casa ca,Pedido p){
        boolean cumple=false;

        if ((p.getZonas()==null) | (p.getZonas().isEmpty())){
            cumple=true;
        }else{
            for (Zona data: p.getZonas()){
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

    public DataPedido PedidoToDataPedido(Pedido p) {
        DataPedido d = new DataPedido();
    
        d.setBanosH(p.getBanosH());
        d.setBanosL(p.getBanosL());
        
        d.setAceptaBanco(p.getAceptaBanco());
        d.setConF(p.getConF());
        
        ControladorClientes cC = new ControladorClientes();
        List<DataCasa> casas = cC.ListcasaToDataCasa(p.getCasas()); 
        d.setCasas(casas);
        
        DataCliente dc = new DataCliente();
       
        dc.setApellido(p.getCliente().getApellido());
        dc.setDireccion(p.getCliente().getDireccion());
        dc.setEmail(p.getCliente().getEmail());
        dc.setId(p.getCliente().getId());
        dc.setInf_adicional(p.getCliente().getInf_adicional());
        dc.setNombre(p.getCliente().getNombre());
        dc.setTelefono(p.getCliente().getTelefono());
        dc.setCelular(p.getCliente().getCelular());
        
        //los pedidos no van aca..
        d.setCliente(dc);
        
        d.setDescripcionAddicional(p.getDescripcionAddicional());
        d.setDormitoriosH(p.getDormitoriosH());
        d.setDormitoriosL(p.getDormitoriosL());
        d.setEstado(p.getEstado());
        d.setId(p.getId());
        d.setPH(p.getPH());
        d.setPU(p.getPU());
        d.setPrecioAH(p.getPrecioAH());
        d.setPrecioAL(p.getPrecioAL());
        d.setPrecioVH(p.getPrecioVH());
        d.setPrecioVL(p.getPrecioVL()); 
        d.setSuperficieH(p.getSuperficieH());
        d.setSuperficieL(p.getSuperficieL());
        d.setTipo(p.getTipo());
        
        List<DataZona> z = new ArrayList<DataZona>();
        
        if (p.getZonas()!=null){
            for (Zona da:p.getZonas()){
                DataZona data = new DataZona();
                data.setNorteG(da.isNorteG());
                data.setSurG(da.isSurG());
                data.setZona(da.getZona());
				z.add(data);
            }
        }
        d.setZonas(z);
        
        return d;
    }
    
   
   //*****FUNCIONES AUXILIARES******//

    @Override
    public List<DataCasa> listarCasas() {
        ManejadorDatos.getInstance().getEm().getTransaction().begin();   
        String sql = "select c from Casa c;" ;
        List<Casa> list = (List<Casa>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
        ControladorClientes cC = new ControladorClientes();
        
        return cC.ListcasaToDataCasa(list);                
       }

    @Override
    public DataCasa obtenerCasa(String id) throws Exception {
        
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Casa c  = ManejadorDatos.getInstance().getEm().find(Casa.class,id);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
        DataCasa d; 
        if (c==null){
            throw new Exception("No existe la casa en el Sistema");
        }else{
            ControladorClientes cC = new ControladorClientes();
            d = cC.casaToDataCasa(c);
            List<DataPedido> dpedidos = new ArrayList<DataPedido>();
                
            if (c.getPedidos()!=null){
                for (Pedido p:c.getPedidos()){
                    dpedidos.add(PedidoToDataPedido(p));
                }
            }
            d.setPedidos(dpedidos);
            List<String> s = new ArrayList<String>();
            if (c.getImages()!=null){
                for (Path p:c.getImages()){
                    s.add(p.getPath());
                }                
            }
            d.setImages(s);
    }
    
    return d;
    
    }

    @Override
    public List<String> listarLugares() {
        ManejadorDatos.getInstance().getEm().getTransaction().begin();   
        String sql = "select l from Lugar l;" ;
        List<Lugar> list = (List<Lugar>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
        ManejadorDatos.getInstance().getEm().getTransaction().commit();  
        
        List<String> ret = new ArrayList<String>();
        if (list!=null){
            for (Lugar l:list){
                ret.add(l.getLugar());
            }
        }
        return ret;
    }


    @Override
    public void ingresarLugar(String lugar) throws Exception{
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Lugar l2  = ManejadorDatos.getInstance().getEm().find(Lugar.class,lugar);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();

        if (l2!=null){
            throw new Exception("Ya existe el lugar en el sistema: "+lugar);
        }
        Lugar l = new Lugar();
        l.setLugar(lugar);
        ManejadorDatos.getInstance().getEm().persist(l);
    }
    
    @Override
    public void eliminarLugar(String lugar) throws Exception{
        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Lugar l  = ManejadorDatos.getInstance().getEm().find(Lugar.class,lugar);
        ManejadorDatos.getInstance().getEm().remove(l);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();
    }
}
