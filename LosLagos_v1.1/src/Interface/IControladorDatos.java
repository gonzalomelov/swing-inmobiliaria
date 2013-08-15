/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import javax.swing.JTable;


/**
 *
 * @author Santiago
 */
public interface IControladorDatos {
    public void exportarDatos(String path, boolean c,boolean p,boolean s) throws Exception;
    //public void bajaCasa(String id) throws Exception;

    public void marcarCasa(int idCliente,String idCasa) throws Exception; //marca una casa
    public void desMarcarCasa(int idCliente,String idCasa) throws Exception; //desmarca una csas

    public boolean isCasaMarcada(int idCLiente,String idCasa)throws Exception;


    //funcion que genera el archivo pdf
    public void imprimirPropiedades(String path, int tamañoLetraTabla)throws Exception;

    public void imprimirJTable(JTable t,String titulo);

}
