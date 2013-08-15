/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Objetos;

import DataTypes.DataPedido;
import DataTypes.DataZona;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Santiago
 */
@Entity
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    //criterior ingresador para la busqueda
    private String Tipo; //"Terreno" o "Propiedad"
    
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Zona> Zonas;
    
    private String PU;
    private String PH;    
    private int precioAL; //precio rango bajo
    private int precioAH; //precion rango alto    
    private int precioVL; //precio rango bajo
    private int precioVH; //precion rango alto    
    private String Estado;//en venta, alquiler, ambos NO!! solo para alquilar o en venta    
    private int SuperficieL;
    private int SuperficieH;    
    private int dormitoriosL;
    private int dormitoriosH;    
    private int BanosL;
    private int BanosH;    
    private String DescripcionAddicional;
    
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "pedidos")
    private List<Casa> casas = new ArrayList<Casa>();

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "pedidos")
    private Cliente cliente;


    private String aceptaBanco;
    private String conF;

    public String getAceptaBanco() {
        return aceptaBanco;
    }

    public void setAceptaBanco(String aceptaBanco) {
        this.aceptaBanco = aceptaBanco;
    }

    public String getConF() {
        return conF;
    }

    public void setConF(String conF) {
        this.conF = conF;
    }
 
    public Pedido(DataPedido s) {
        this.BanosH=s.getBanosH();
        this.BanosL=s.getBanosL();
        
        this.aceptaBanco = s.getAceptaBanco();
        this.conF = s.getConF();
        this.DescripcionAddicional=s.getDescripcionAddicional();
        this.Estado=s.getEstado();
        this.PH=s.getPH();
        this.PU=s.getPU();
        this.SuperficieH=s.getSuperficieH();
        this.SuperficieL=s.getSuperficieL();
        this.Tipo=s.getTipo();
        
        List<Zona> z = new ArrayList<Zona>();
        if (s.getZonas()!=null){
            for (DataZona data :s.getZonas()){
                Zona zz = new Zona();
                zz.setNorteG(data.isNorteG());
                zz.setSurG(data.isSurG());
                zz.setZona(data.getZona());
                z.add(zz);
            }
        }
        this.Zonas=z;
        this.dormitoriosH=s.getDormitoriosH();
        this.dormitoriosL=s.getDormitoriosL();
        this.precioAH=s.getPrecioAH();
        this.precioAL=s.getPrecioAL();
        this.precioVH=s.getPrecioVH();
        this.precioVL=s.getPrecioVL();
   
    }

    public int getBanosH() {
        return BanosH;
    }

    public void setBanosH(int BanosH) {
        this.BanosH = BanosH;
    }

    public int getBanosL() {
        return BanosL;
    }

    public void setBanosL(int BanosL) {
        this.BanosL = BanosL;
    }

    public String getDescripcionAddicional() {
        return DescripcionAddicional;
    }

    public void setDescripcionAddicional(String DescripcionAddicional) {
        this.DescripcionAddicional = DescripcionAddicional;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getPH() {
        return PH;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    public String getPU() {
        return PU;
    }

    public void setPU(String PU) {
        this.PU = PU;
    }

   


    public int getSuperficieH() {
        return SuperficieH;
    }

    public void setSuperficieH(int SuperficieH) {
        this.SuperficieH = SuperficieH;
    }

    public int getSuperficieL() {
        return SuperficieL;
    }

    public void setSuperficieL(int SuperficieL) {
        this.SuperficieL = SuperficieL;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }



    public List<Casa> getCasas() {
        return casas;
    }

    public void setCasas(List<Casa> casas) {
        this.casas = casas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getDormitoriosH() {
        return dormitoriosH;
    }

    public void setDormitoriosH(int dormitoriosH) {
        this.dormitoriosH = dormitoriosH;
    }

    public int getDormitoriosL() {
        return dormitoriosL;
    }

    public void setDormitoriosL(int dormitoriosL) {
        this.dormitoriosL = dormitoriosL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrecioAH() {
        return precioAH;
    }

    public void setPrecioAH(int precioAH) {
        this.precioAH = precioAH;
    }

    public int getPrecioAL() {
        return precioAL;
    }

    public void setPrecioAL(int precioAL) {
        this.precioAL = precioAL;
    }

    public int getPrecioVH() {
        return precioVH;
    }

    public void setPrecioVH(int precioVH) {
        this.precioVH = precioVH;
    }

    public int getPrecioVL() {
        return precioVL;
    }

    public void setPrecioVL(int precioVL) {
        this.precioVL = precioVL;
    }

    public List<Zona> getZonas() {
        return Zonas;
    }

    public void setZonas(List<Zona> Zonas) {
        this.Zonas = Zonas;
    }

    public Pedido(){

    }

    

}
