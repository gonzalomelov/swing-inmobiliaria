/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luz
 */
public class DataCliente {
    //atributos de los clientes, estos deben ser iguales a los de la clase Cliente
    
    private int id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private String telefono;
    private String celular;
    private String inf_adicional;
    
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Informacion Cliente:" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + ", inf_adicional=" + inf_adicional + ", pedidos=" + pedidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInf_adicional() {
        return inf_adicional;
    }

    public void setInf_adicional(String inf_adicional) {
        this.inf_adicional = inf_adicional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<DataPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<DataPedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private List<DataPedido> pedidos = new ArrayList<DataPedido>();

}
