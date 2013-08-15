/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Objetos;

import DataTypes.DataCasa;
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
public class Casa implements Serializable {
    
    @Id
    private String id;
    private String Tipo; //"Terreno" o "Propiedad"
    private String Estado;//en venta, alquiler, ambos NO!! solo para alquilar o en venta
    
    private String Zona;
    private boolean Norte;
    private String direccion;
    
  

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Path> images = new ArrayList<Path>();
    
    private boolean PU;
    private boolean PH;
    
    private int precioV;
    private int precioA;
    
    private int Superficie;
    private int dormitorios;
    private int Banos;
    private String Especificacion;

    //datos de contacto del cliente;
    private String nombre;
    private String Apellido;
    private String email;
    private String telefono;
    private String direccionDueno;
    private String celular;
    private String precedencia;
   
    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    //@JoinColumn(name = "casas")
    private List<Pedido> pedidos = new ArrayList<Pedido>();
    private boolean aceptaBanco;
    private boolean tieneF;
    
    public Casa(DataCasa c) {
        Banos = c.getBanos();
        direccion = c.getDireccion();
        direccionDueno=c.getDireccionDueno();
        dormitorios=c.getDormitorios();
        email=c.getEmail();
        Especificacion=c.getEspecificacion();
        Estado=c.getEstado();
        id=c.getId();
        nombre=c.getNombre();
        PH=c.isPH();
        PU=c.isPU();
        precioV=c.getPrecioV();
        precioA=c.getPrecioA();
        Superficie=c.getSuperficie();
        telefono=c.getTelefono();
        Tipo=c.getTipo();
        Zona=c.getZona();
        this.aceptaBanco = c.isAceptaBanco();
        this.tieneF = c.isTieneF();
        this.precedencia=c.getPrecedencia();
        this.Norte=c.isNorteG();
        this.Apellido = c.getApellido();
        
        celular = c.getCelular();
        
        List<Path> i = new ArrayList<Path>();
        if (c.getImages()!=null){
        for (String a :c.getImages()){
          Path p = new Path();
          p.setPath(a);
          i.add(p);  
        }}
        
        this.images = i;
    }

    public int getPrecioV() {
        return precioV;
    }

    public void setPrecioV(int precioV) {
        this.precioV = precioV;
    }

    public int getPrecioA() {
        return precioA;
    }

    public boolean isAceptaBanco() {
        return aceptaBanco;
    }

    public void setAceptaBanco(boolean aceptaBanco) {
        this.aceptaBanco = aceptaBanco;
    }

    public boolean isTieneF() {
        return tieneF;
    }

    public void setTieneF(boolean tieneF) {
        this.tieneF = tieneF;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setPrecioA(int precioA) {
        this.precioA = precioA;
    }
    
    

    public String getPrecedencia() {
        return precedencia;
    }

    public void setPrecedencia(String precedencia) {
        this.precedencia = precedencia;
    }
    

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getDireccionDueno() {
        return direccionDueno;
    }

    public void setDireccionDueno(String direccionDueno) {
        this.direccionDueno = direccionDueno;
    }

    public boolean isNorte() {
        return Norte;
    }

    public void setNorte(boolean Norte) {
        this.Norte = Norte;
    }

    public String getZona() {
        return Zona;
    }

    public void setZona(String Zona) {
        this.Zona = Zona;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    

    public boolean isPH() {
        return PH;
    }

    public void setPH(boolean PH) {
        this.PH = PH;
    }

    public boolean isPU() {
        return PU;
    }

    public void setPU(boolean PU) {
        this.PU = PU;
    }

    public List<Path> getImages() {
        return images;
    }

    public void setImages(List<Path> images) {
        this.images = images;
    }


   

    
    public Casa(){

    }

    public int getBanos() {
        return Banos;
    }

    public void setBanos(int Banos) {
        this.Banos = Banos;
    }

    public String getEspecificacion() {
        return Especificacion;
    }

    public void setEspecificacion(String Especificacion) {
        this.Especificacion = Especificacion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public int getSuperficie() {
        return Superficie;
    }

    public void setSuperficie(int Superficie) {
        this.Superficie = Superficie;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    


}
