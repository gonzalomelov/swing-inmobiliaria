/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import Objetos.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luz
 */
public class DataCasa {
    private String id;
    private String Tipo; //"Terreno" o "Propiedad"
    private String Zona;
    private String direccion;
    private boolean NorteG; 
    
    private boolean PU;
    private boolean PH;
    
    private boolean aceptaBanco;
    private boolean tieneF;
    
    private int precioV;
    private int precioA;
    
    private String Estado;//en venta, alquiler, ambos NO!! solo para alquilar o en venta
    private int Superficie;
    private int dormitorios;
    private int Banos;
    private String Especificacion;

    //datos de contacto del dueno;
    private String nombre;
    private String Apellido;
    private String email;
    private String telefono;
    private String direccionDueno;
    private String celular;
    
    private List<String> images = new ArrayList<String>();
    //PREDECENCIA DE LA CASA


    public List<String> getImages() {
        return images;
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

    
    public void setImages(List<String> images) {
        this.images = images;
    }
    
    private String precedencia;
    private List<DataPedido> pedidos;

    public String getPrecedencia() {
        return precedencia;
    }

    public void setPrecedencia(String precedencia) {
        this.precedencia = precedencia;
    }

    public List<DataPedido> getPedidos() {
        return pedidos;
    }

    public String getApellido() {
        return Apellido;
    }

    public int getPrecioA() {
        return precioA;
    }

    public void setPrecioA(int precioA) {
        this.precioA = precioA;
    }

    public int getPrecioV() {
        return precioV;
    }

    public void setPrecioV(int precioV) {
        this.precioV = precioV;
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

    public String getZona() {
        return Zona;
    }

    public void setZona(String Zona) {
        this.Zona = Zona;
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

    public boolean isNorteG() {
        return NorteG;
    }

    public void setNorteG(boolean NorteG) {
        this.NorteG = NorteG;
    }

    @Override
    public String toString() {
        return "DataCasa{" + "id=" + id + ", Tipo=" + Tipo + ", Zona=" + Zona + ", direccion=" + direccion + ", NorteG=" + NorteG + ", PU=" + PU + ", PH=" + PH + ", precioV=" + precioV + ", precioA=" + precioA + ", Estado=" + Estado + ", Superficie=" + Superficie + ", dormitorios=" + dormitorios + ", Ba\u00f1os=" + Banos + ", Especificacion=" + Especificacion + ", nombre=" + nombre + ", Apellido=" + Apellido + ", email=" + email + ", telefono=" + telefono + ", direccionDue\u00f1o=" + direccionDueno + ", precedencia=" + precedencia + '}';
    }

    public void setPedidos(List<DataPedido> dpedidos) {
        this.pedidos = dpedidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    

}
