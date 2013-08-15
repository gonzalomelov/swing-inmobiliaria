/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import DataTypes.DataPedido;
import Interface.Fabrica;
import Interface.IControladorClientes;
import Interface.IControladorDatos;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author gonza
 */
public class SolicitudLista extends javax.swing.JInternalFrame {

    /**
     * Creates new form SolicitudLista
     */
    public SolicitudLista(String modo) {
        initComponents();
        
        this.modo = modo;
        this.idSolicitudSeleccionada = -1;
        
        if (this.modo.equals("ConsultarSolicitud")){
            this.setTitle("Consultar Solicitud");
            this.jButtonAceptar.setText("Ver Detalles");
        } else if (this.modo.equals("BajaSolicitud")){
            this.setTitle("Baja Solicitud");
            this.jButtonAceptar.setText("Eliminar");
        } else if (this.modo.equals("ModificarSolicitud")){
            this.setTitle("Modificar Solicitud");
            this.jButtonAceptar.setText("Modificar");
        } else if (this.modo.equals("ConsultarPropiedadesSolicitud")){
            this.setTitle("Consultar Propiedades de Solicitud");
            this.jButtonAceptar.setText("Consultar Propiedades");
        }
        
        Fabrica fab = new Fabrica();
        IControladorClientes icc = fab.getIControladorClientes();
        List<DataPedido> listaSolicitudes = icc.obtenerSolicitudes();
        this.actualizarTablaSolicitudes(listaSolicitudes);
        
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.jTableSolicitudes.getModel());
        jTableSolicitudes.setRowSorter(sorter);
        
        this.jTableSolicitudes.getColumnModel().removeColumn(this.jTableSolicitudes.getColumnModel().getColumn(this.jTableSolicitudes.getColumnModel().getColumnCount()-1));
    }
    
    // Caso Ver Solicitudes de Propiedad
    public SolicitudLista(List<DataPedido> listaSolicitudes){
        initComponents();
        
        this.modo = "ConsultarSolicitudesDePropiedad";
        this.idSolicitudSeleccionada = -1;
        
        this.setTitle("Consultar Solicitudes cumplidas por la Propiedad");
        this.jButtonAceptar.setText("Ver Detalles");
        
        this.actualizarTablaSolicitudes(listaSolicitudes);
        
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.jTableSolicitudes.getModel());
        jTableSolicitudes.setRowSorter(sorter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSolicitudes = new javax.swing.JTable();
        jTextFieldFiltro = new javax.swing.JTextField();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle("Lista de Solicitudes");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Solicitudes"));

        jTableSolicitudes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cliente", "idCliente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSolicitudes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableSolicitudes);

        jTextFieldFiltro.setText("Buscar...");
        jTextFieldFiltro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldFiltroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldFiltroFocusLost(evt);
            }
        });
        jTextFieldFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiltroKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jTextFieldFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonAceptar.setText("jButton1");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cerrar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancelar))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonAceptar)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        javax.swing.JDesktopPane desktopPane = this.getDesktopPane();
        desktopPane.remove(this);
        desktopPane.updateUI();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        this.idSolicitudSeleccionada = -1;
        for (int i=0; i<this.jTableSolicitudes.getRowCount(); i++){
            if (this.jTableSolicitudes.isRowSelected(i)){
                
                this.idSolicitudSeleccionada = (Integer) this.jTableSolicitudes.getRowSorter().getModel().
                        getValueAt(this.jTableSolicitudes.getRowSorter().convertRowIndexToModel(i), 0);
                this.idClienteSeleccionado = (Integer) this.jTableSolicitudes.getRowSorter().getModel().
                        getValueAt(this.jTableSolicitudes.getRowSorter().convertRowIndexToModel(i), 2);
                
                break;
            }
        }
        
        if (this.idSolicitudSeleccionada == -1){
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud","Info",JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Fabrica fab = new Fabrica();
        IControladorClientes icc = fab.getIControladorClientes();
        
        if (modo.equals("BajaSolicitud")){
            int respuesta;
            respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea eliminar la solicitud?", "Eliminar Solicitud", 0);
            if (respuesta == 0){
                // Quiere eliminar la solicitud
                try{
                    icc.eliminarSolicitudes(this.idClienteSeleccionado, this.idSolicitudSeleccionada);
                    JOptionPane.showMessageDialog(this, "Solicitud eliminada con exito","Exito",JOptionPane.INFORMATION_MESSAGE);
                    List<DataPedido> listaSolicitudes = icc.obtenerSolicitudes();
                    this.actualizarTablaSolicitudes(listaSolicitudes);
                    this.idClienteSeleccionado = -1;
                    this.idSolicitudSeleccionada = -1;
                }
                catch (Exception exc){
                    JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            } else if (respuesta == 1){
                // No la quiere eliminar
                JOptionPane.showMessageDialog(this, "No se ha eliminado la solicitud","Info",JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (modo.equals("ModificarSolicitud") || modo.equals("ConsultarSolicitud") || modo.equals("ConsultarSolicitudesDePropiedad")){
            SolicitudInfo ifSolicitudInfo = new SolicitudInfo(this.modo,this.idSolicitudSeleccionada);
            this.getDesktopPane().add(ifSolicitudInfo);
            ifSolicitudInfo.setVisible(true);
        }
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jTextFieldFiltroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldFiltroFocusGained
        if (this.jTextFieldFiltro.getText().equals("Buscar...")){
            this.jTextFieldFiltro.setText("");
        }
    }//GEN-LAST:event_jTextFieldFiltroFocusGained

    private void jTextFieldFiltroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldFiltroFocusLost
        if (this.jTextFieldFiltro.getText().equals("")){
            this.jTextFieldFiltro.setText("Buscar...");
        }
    }//GEN-LAST:event_jTextFieldFiltroFocusLost

    private void jTextFieldFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiltroKeyReleased
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.jTableSolicitudes.getModel());
        jTableSolicitudes.setRowSorter(sorter);
        String i = "(?i)";
        String texto= i.concat(this.jTextFieldFiltro.getText());
        if (texto.length() == 4) {
            sorter.setRowFilter(null);
        } else {
            try{
                sorter.setRowFilter(RowFilter.regexFilter(texto));
            }
            catch(Exception exc){
                sorter.setRowFilter(null);
            }
        }
    }//GEN-LAST:event_jTextFieldFiltroKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Fabrica fab = new Fabrica();
        IControladorDatos icd = fab.getIControladorDatos();
        icd.imprimirJTable(this.jTableSolicitudes,"Solicitudes");
    }//GEN-LAST:event_jButton1ActionPerformed

    public final void actualizarTablaSolicitudes(List<DataPedido> listaSolicitudes){
        
        DefaultTableModel dtm = (DefaultTableModel) this.jTableSolicitudes.getModel();
        
        while(dtm.getRowCount()>0)
            dtm.removeRow(0);
        
        for (DataPedido dataP : listaSolicitudes){
            Object[] row = {dataP.getId(),dataP.getCliente().getNombre()+" "+dataP.getCliente().getApellido(),dataP.getCliente().getId()};
            dtm.addRow(row);
        }
        
        this.jTableSolicitudes.setModel(dtm);
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    public javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSolicitudes;
    private javax.swing.JTextField jTextFieldFiltro;
    // End of variables declaration//GEN-END:variables

    // modo = "ModificarSolicitud" | "ConsultarSolicitud" | "BajaSolicitud" | "ConsultarSolicitudesDePropiedad"
    private String modo;
    private int idSolicitudSeleccionada;
    private int idClienteSeleccionado;
}
