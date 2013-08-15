/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import DataTypes.DataCasa;
import DataTypes.DataCliente;
import DataTypes.DataPedido;
import DataTypes.DataZona;
import Interface.Fabrica;
import Interface.IControladorCasas;
import Interface.IControladorClientes;
import Interface.IControladorDatos;
import java.awt.Component;
import java.util.ArrayList;
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
public class SolicitudInfo extends javax.swing.JInternalFrame {
    /**
     * Creates new form SolicitudInfo
     */
    // Caso AltaSolicitud
    public SolicitudInfo() {
        initComponents();
        
        this.modo = "AltaSolicitud";
        this.idClienteSolicitud = -1;
        this.setTitle("Alta Solicitud");
        this.jButtonAceptar.setText("Crear Solicitud");
        
        Fabrica f = new Fabrica();
        IControladorCasas icc = f.getIControladorCasas();
        Object[] zonas;
        try {
            List<String> zonasString = icc.listarLugares();
            zonas = (Object[])zonasString.toArray();
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, exc.getMessage(),"Error",0);
            return;
        }
        
        DefaultTableModel modeloTablaZonas = (DefaultTableModel)this.jTableZonas.getModel();
        for(int i=0; i<zonas.length; i++){
            Object[] row = {false,(String)zonas[i],false,false};
            modeloTablaZonas.addRow(row);
        }
        this.jTableZonas.setModel(modeloTablaZonas);
        
        this.jTextFieldPrecioAlquilerDesde.setEnabled(false);
        this.jTextFieldPrecioAlquilerHasta.setEnabled(false);
        this.jTextFieldPrecioCompraDesde.setEnabled(false);
        this.jTextFieldPrecioCompraHasta.setEnabled(false);
        
        // Elimino la pestana de sugerencia de propiedades
        this.jTabbedPane1.remove(this.jPanelPropSug);
    }
    
    // Caso ConsultarSolicitud o ModificarSolicitud
    public SolicitudInfo(String modo, int idSolicitud) {
        initComponents();
        
        this.modo = modo;
        this.jButtonSelCliente.setEnabled(false);
        
        if (this.modo.equals("ModificarSolicitud")){
            this.setTitle("Modificar Solicitud");
            this.jButtonAceptar.setText("Modificar");
        }else if (this.modo.equals("ConsultarSolicitud")){
            this.setTitle("Consultar Solicitud");
            this.jButtonAceptar.setText("Aceptar");
        }else if (this.modo.equals("ConsultarSolicitudesDePropiedad")){
            this.setTitle("Consultar Solicitudes de Propiedad");
            this.jButtonAceptar.setText("Aceptar");
        }
        
        try {
            this.idSolicitud = idSolicitud;
            this.crearFormularioDesdeDataPedido(idSolicitud);
            
            if (this.modo.equals("ModificarSolicitud")){
                // Elimino la pestana de sugerencia de propiedades
                this.jTabbedPane1.remove(this.jPanelPropSug);
            } else if (this.modo.equals("ConsultarSolicitud") || this.modo.equals("ConsultarSolicitudesDePropiedad")){
                
                if (this.modo.equals("ConsultarSolicitudesDePropiedad")){
                    // Elimino la pestana de sugerencia de propiedades
                    this.jTabbedPane1.remove(this.jPanelPropSug);
                }
                
                // Inhabilito los inputs
                /// ######## Pestana Informacion Basica ########
                this.jRadioButtonTipoCasa.setEnabled(false);
                this.jRadioButtonTipoTerreno.setEnabled(false);
                this.jCheckBoxOperacionAlquiler.setEnabled(false);
                this.jCheckBoxOperacionCompra.setEnabled(false);
                this.jRadioButtonPHSi.setEnabled(false);
                this.jRadioButtonPHNo.setEnabled(false);
                this.jRadioButtonPUSi.setEnabled(false);
                this.jRadioButtonPUNo.setEnabled(false);
                this.jRadioButtonFinanciacionSi.setEnabled(false);
                this.jRadioButtonFinanciacionNo.setEnabled(false);
                this.jRadioButtonBancoSi.setEnabled(false);
                this.jRadioButtonBancoNo.setEnabled(false);
                this.jTextFieldPrecioCompraDesde.setEditable(false);
                this.jTextFieldPrecioCompraHasta.setEditable(false);
                this.jTextFieldPrecioAlquilerDesde.setEditable(false);
                this.jTextFieldPrecioAlquilerHasta.setEditable(false);
                
                /// ######## Pestana Descripcion de la Propiedad ########
                this.jTextFieldSupTerrDesde.setEditable(false);
                this.jTextFieldSupTerrHasta.setEditable(false);
                this.jCheckBoxDormitorios1.setEnabled(false);
                this.jCheckBoxDormitorios2.setEnabled(false);
                this.jCheckBoxDormitorios3.setEnabled(false);
                this.jCheckBoxDormitoriosMas3.setEnabled(false);
                this.jCheckBoxBanos1.setEnabled(false);
                this.jCheckBoxBanos2.setEnabled(false);
                this.jCheckBoxBanosMas2.setEnabled(false);
                this.jTextAreaInfoAdic.setEditable(false);
                
                /// ######## Pestana Datos de Contacto ########   
                this.jTextFieldContactoNombre.setEditable(false);
                this.jTextFieldContactoApell.setEditable(false);
                this.jTextFieldContactoEmail.setEditable(false);
                this.jTextFieldContactoTel.setEditable(false);
                this.jTextFieldContactoCel.setEditable(false);
                this.jTextFieldContactoDir.setEditable(false);
                this.jTextAreaContactoInfoAdic.setEditable(false);
                this.jButtonSelCliente.setEnabled(false);
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(null, exc.getMessage(),"Error",0);
        }
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableZonas = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPrecioAlquilerDesde = new javax.swing.JTextField();
        jTextFieldPrecioAlquilerHasta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldPrecioCompraDesde = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldPrecioCompraHasta = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jRadioButtonTipoCasa = new javax.swing.JRadioButton();
        jRadioButtonTipoTerreno = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jCheckBoxOperacionAlquiler = new javax.swing.JCheckBox();
        jCheckBoxOperacionCompra = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jRadioButtonPHSi = new javax.swing.JRadioButton();
        jRadioButtonBancoNo = new javax.swing.JRadioButton();
        jRadioButtonFinanciacionNo = new javax.swing.JRadioButton();
        jRadioButtonPUNo = new javax.swing.JRadioButton();
        jRadioButtonPHNo = new javax.swing.JRadioButton();
        jRadioButtonPUSi = new javax.swing.JRadioButton();
        jRadioButtonFinanciacionSi = new javax.swing.JRadioButton();
        jRadioButtonBancoSi = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInfoAdic = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldSupTerrDesde = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldSupTerrHasta = new javax.swing.JTextField();
        jCheckBoxDormitorios1 = new javax.swing.JCheckBox();
        jCheckBoxDormitorios2 = new javax.swing.JCheckBox();
        jCheckBoxDormitorios3 = new javax.swing.JCheckBox();
        jCheckBoxDormitoriosMas3 = new javax.swing.JCheckBox();
        jCheckBoxBanos1 = new javax.swing.JCheckBox();
        jCheckBoxBanos2 = new javax.swing.JCheckBox();
        jCheckBoxBanosMas2 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldContactoNombre = new javax.swing.JTextField();
        jTextFieldContactoApell = new javax.swing.JTextField();
        jTextFieldContactoEmail = new javax.swing.JTextField();
        jTextFieldContactoTel = new javax.swing.JTextField();
        jTextFieldContactoDir = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaContactoInfoAdic = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldContactoCel = new javax.swing.JTextField();
        jButtonSelCliente = new javax.swing.JButton();
        jPanelPropSug = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablePropSug = new javax.swing.JTable();
        filtro = new javax.swing.JTextField();
        jButtonPropSugDetalles = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setTitle("Alta Solicitud");

        jButtonAceptar.setText("Crear Solicitud");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Propiedad"));

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ubicacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), java.awt.Color.black)); // NOI18N

        jTableZonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Seleccion", "Zona", "Norte", "Sur"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableZonas);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Precio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Alquiler", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel5.setText("Desde: ");

        jTextFieldPrecioAlquilerDesde.setColumns(10);

        jTextFieldPrecioAlquilerHasta.setColumns(10);

        jLabel6.setText("Hasta: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldPrecioAlquilerDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldPrecioAlquilerHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldPrecioAlquilerDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldPrecioAlquilerHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel12.setText("Desde: ");

        jTextFieldPrecioCompraDesde.setColumns(10);

        jLabel18.setText("Hasta: ");

        jTextFieldPrecioCompraHasta.setColumns(10);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldPrecioCompraDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldPrecioCompraHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldPrecioCompraDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldPrecioCompraHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Basico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bitstream Vera Sans", 1, 12))); // NOI18N

        jLabel2.setText("Tipo: ");

        buttonGroup1.add(jRadioButtonTipoCasa);
        jRadioButtonTipoCasa.setText("Casa");

        buttonGroup1.add(jRadioButtonTipoTerreno);
        jRadioButtonTipoTerreno.setText("Terreno");

        jLabel1.setText("Operacion: ");

        jCheckBoxOperacionAlquiler.setText("Alquiler");
        jCheckBoxOperacionAlquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOperacionAlquilerActionPerformed(evt);
            }
        });

        jCheckBoxOperacionCompra.setText("Compra");
        jCheckBoxOperacionCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOperacionCompraActionPerformed(evt);
            }
        });

        jLabel3.setText("Propiedad Horizontal: ");

        jLabel4.setText("Padron Unico: ");

        jLabel19.setText("Financiacion:");

        jLabel20.setText("Banco:");

        buttonGroup2.add(jRadioButtonPHSi);
        jRadioButtonPHSi.setText("Si");

        buttonGroup5.add(jRadioButtonBancoNo);
        jRadioButtonBancoNo.setText("No");

        buttonGroup4.add(jRadioButtonFinanciacionNo);
        jRadioButtonFinanciacionNo.setText("No");

        buttonGroup3.add(jRadioButtonPUNo);
        jRadioButtonPUNo.setText("No");

        buttonGroup2.add(jRadioButtonPHNo);
        jRadioButtonPHNo.setText("No");

        buttonGroup3.add(jRadioButtonPUSi);
        jRadioButtonPUSi.setText("Si");

        buttonGroup4.add(jRadioButtonFinanciacionSi);
        jRadioButtonFinanciacionSi.setText("Si");

        buttonGroup5.add(jRadioButtonBancoSi);
        jRadioButtonBancoSi.setText("Si");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jRadioButtonTipoCasa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButtonTipoTerreno))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jCheckBoxOperacionAlquiler)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxOperacionCompra))))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jRadioButtonPHSi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButtonPHNo))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonFinanciacionSi, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jRadioButtonBancoSi, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jRadioButtonPUSi, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonBancoNo)
                                    .addComponent(jRadioButtonFinanciacionNo)
                                    .addComponent(jRadioButtonPUNo))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButtonTipoCasa)
                    .addComponent(jRadioButtonTipoTerreno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCheckBoxOperacionAlquiler)
                    .addComponent(jCheckBoxOperacionCompra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButtonPHSi)
                    .addComponent(jRadioButtonPHNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jRadioButtonPUSi)
                    .addComponent(jRadioButtonPUNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jRadioButtonFinanciacionSi)
                    .addComponent(jRadioButtonFinanciacionNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jRadioButtonBancoSi)
                    .addComponent(jRadioButtonBancoNo))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informacion Basica", jPanel2);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Descriptivos"));

        jLabel10.setText("Dormitorios: ");

        jLabel11.setText("Banos: ");

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion Adicional", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jTextAreaInfoAdic.setColumns(20);
        jTextAreaInfoAdic.setRows(5);
        jScrollPane1.setViewportView(jTextAreaInfoAdic);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Superficie del Terreno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel7.setText("Desde: ");

        jTextFieldSupTerrDesde.setColumns(10);

        jLabel8.setText("Hasta: ");

        jTextFieldSupTerrHasta.setColumns(10);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldSupTerrDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldSupTerrHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldSupTerrDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldSupTerrHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jCheckBoxDormitorios1.setText("1");

        jCheckBoxDormitorios2.setText("2");

        jCheckBoxDormitorios3.setText("3");

        jCheckBoxDormitoriosMas3.setText("mas de 3");

        jCheckBoxBanos1.setText("1");

        jCheckBoxBanos2.setText("2");

        jCheckBoxBanosMas2.setText("mas de 2");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jCheckBoxBanos1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxBanos2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxBanosMas2))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jCheckBoxDormitorios1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxDormitorios2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxDormitorios3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxDormitoriosMas3)))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jCheckBoxDormitorios1)
                    .addComponent(jCheckBoxDormitorios2)
                    .addComponent(jCheckBoxDormitorios3)
                    .addComponent(jCheckBoxDormitoriosMas3))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jCheckBoxBanos1)
                    .addComponent(jCheckBoxBanos2)
                    .addComponent(jCheckBoxBanosMas2))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Descripcion de la Propiedad", jPanel4);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Contacto"));

        jLabel13.setText("Nombre: ");

        jLabel14.setText("Apellido: ");

        jLabel15.setText("Email: ");

        jLabel16.setText("Telefono: ");

        jLabel17.setText("Direccion: ");

        jTextFieldContactoNombre.setColumns(20);
        jTextFieldContactoNombre.setEnabled(false);

        jTextFieldContactoApell.setColumns(20);
        jTextFieldContactoApell.setEnabled(false);

        jTextFieldContactoEmail.setColumns(20);
        jTextFieldContactoEmail.setEnabled(false);

        jTextFieldContactoTel.setColumns(20);
        jTextFieldContactoTel.setEnabled(false);

        jTextFieldContactoDir.setColumns(20);
        jTextFieldContactoDir.setEnabled(false);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion Adicional", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jTextAreaContactoInfoAdic.setColumns(20);
        jTextAreaContactoInfoAdic.setRows(5);
        jTextAreaContactoInfoAdic.setEnabled(false);
        jScrollPane2.setViewportView(jTextAreaContactoInfoAdic);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel9.setText("Celular");

        jTextFieldContactoCel.setColumns(20);
        jTextFieldContactoCel.setEnabled(false);

        jButtonSelCliente.setText("Seleccionar Cliente");
        jButtonSelCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldContactoDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldContactoEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextFieldContactoApell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldContactoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldContactoTel)
                                    .addComponent(jTextFieldContactoCel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addComponent(jButtonSelCliente)
                        .addGap(31, 31, 31))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldContactoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldContactoApell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSelCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldContactoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldContactoTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldContactoCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldContactoDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contacto", jPanel7);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Propiedades Sugeridas"));

        jTablePropSug.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Operacion", "Tipo", "Zona", "Direccion", "Precio", "Tomar en cuenta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePropSug.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePropSugMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTablePropSug);

        filtro.setColumns(10);
        filtro.setText("Buscar...");
        filtro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                filtroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                filtroFocusLost(evt);
            }
        });
        filtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtroKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addComponent(filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonPropSugDetalles.setText("Ver Detalles");
        jButtonPropSugDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPropSugDetallesActionPerformed(evt);
            }
        });

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPropSugLayout = new javax.swing.GroupLayout(jPanelPropSug);
        jPanelPropSug.setLayout(jPanelPropSugLayout);
        jPanelPropSugLayout.setHorizontalGroup(
            jPanelPropSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPropSugLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPropSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPropSugLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPropSugDetalles)))
                .addContainerGap())
        );
        jPanelPropSugLayout.setVerticalGroup(
            jPanelPropSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPropSugLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelPropSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPropSugDetalles)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Propiedades", jPanelPropSug);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCancelar))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAceptar)
                    .addComponent(jButtonCancelar))
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
        Fabrica fab = new Fabrica();
        IControladorClientes icc = fab.getIControladorClientes();
        // TODO Controlar Excepciones!!!!!
        if (this.modo.equals("ConsultarSolicitud") || this.modo.equals("ConsultarSolicitudesDePropiedad")){
            // Cierro la ventana
            JDesktopPane desktopPane = this.getDesktopPane();
            desktopPane.remove(this);
            desktopPane.updateUI();
        } else if (this.modo.equals("AltaSolicitud") || this.modo.equals("ModificarSolicitud")){
            // Para el modificar, el idClienteSolicitud se llena solo al inicializar
            if (this.idClienteSolicitud == -1){
                JOptionPane.showMessageDialog(this, "No selecciono ningun cliente. Seleccione uno para crear la solicitud","Exito",0);
                return;
            }
            
            DataPedido dp;
            try {
                dp = this.crearDataPedidoDesdeFormulario();
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
                return;
            }
            
            if (this.modo.equals("AltaSolicitud")){
                int respuesta;
                respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea crear la solicitud?", "Crear Solicitud", 0);
                if (respuesta == 0){ //TODO JOptionPane.YesOPtion
                    // Quiere crear la solicitud
                    try{
                        List<DataCasa> propiedadesSugeridas = icc.realizarSolicitudes(this.idClienteSolicitud, dp);     
                        int quiereVerPropiedades;
                        quiereVerPropiedades = JOptionPane.showConfirmDialog(this,
                                "Solicitud creada con exito.\nDesea ver las propiedades que cumplen sus requisitos?",
                                "Ver Propiedades", 0);
                        if (quiereVerPropiedades == 0){
                            PropiedadLista ifPropLista = new PropiedadLista(propiedadesSugeridas);
                            ifPropLista.idClienteSolicitudSugerida = this.idClienteSolicitud;
                            this.getDesktopPane().add(ifPropLista);
                            ifPropLista.setVisible(true);
                        } else if (quiereVerPropiedades == 1){
                            // Nada
                        }
                        JDesktopPane desktopPane = this.getDesktopPane();
                        desktopPane.remove(this);
                        desktopPane.updateUI();
                    }
                    catch (Exception exc){
                        JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
                    }
                } else if (respuesta == 1){ //TODO JOptionPane.NoOPtion
                    // No la quiere crear, nada
                    JOptionPane.showMessageDialog(this, "No se ha creado la solicitud","Info",0);
                }
            } else if (this.modo.equals("ModificarSolicitud")){
                int respuesta;
                respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea modificar la solicitud?", "Modificar Solicitud", 0);
                if (respuesta == 0){
                    // Quiere modificar la solicitud
                    try{
                        System.out.println(this.idClienteSolicitud);
                        System.out.println(dp.getId());
                        List<DataCasa> propiedadesSugeridas = icc.modificarSolicitudes(this.idClienteSolicitud,dp);
                        
                        Component[] ventanas = this.getDesktopPane().getComponents();
                        SolicitudLista ventanaListaSolicitud = null;
                        for(Component comp: ventanas){
                            if (comp instanceof SolicitudLista){
                                ventanaListaSolicitud = (SolicitudLista)comp;
                                break;
                            }
                        }
                        if (ventanaListaSolicitud != null){
                            ventanaListaSolicitud.actualizarTablaSolicitudes(icc.obtenerSolicitudes());
                        }
                        
                        int quiereVerPropiedades;
                        quiereVerPropiedades = JOptionPane.showConfirmDialog(this,
                                "Solicitud modificada con exito.\nDesea ver las propiedades que cumplen sus requisitos?",
                                "Ver Propiedades", 0);
                        if (quiereVerPropiedades == 0){
                            PropiedadLista ifPropLista = new PropiedadLista(propiedadesSugeridas);
                            this.getDesktopPane().add(ifPropLista);
                            ifPropLista.setVisible(true);
                        } else if (quiereVerPropiedades == 1){
                            // Nada
                        }
                    }
                    catch (Exception exc){
                        JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
                    }
                    JDesktopPane desktopPane = this.getDesktopPane();
                    desktopPane.remove(this);
                    desktopPane.updateUI();
                } else if (respuesta == 1){
                    // No la quiere modificar, nada
                    JOptionPane.showMessageDialog(this, "No se ha modificado la solicitud","Info",0);
                }
            }
        }
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jButtonSelClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelClienteActionPerformed
        // Muesttro la lista de clientes
        ClienteLista ifClienteLista = new ClienteLista("SeleccionClienteSolicitud");
        this.getDesktopPane().add(ifClienteLista);
        ifClienteLista.setVisible(true);
    }//GEN-LAST:event_jButtonSelClienteActionPerformed

    private void jCheckBoxOperacionCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOperacionCompraActionPerformed
        boolean quiereComprar = this.jCheckBoxOperacionCompra.isSelected();
        this.jTextFieldPrecioCompraDesde.setEnabled(quiereComprar);
        this.jTextFieldPrecioCompraHasta.setEnabled(quiereComprar);
        if (!quiereComprar) {
            this.jTextFieldPrecioCompraDesde.setText("");
            this.jTextFieldPrecioCompraHasta.setText("");
        }
    }//GEN-LAST:event_jCheckBoxOperacionCompraActionPerformed

    private void jCheckBoxOperacionAlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOperacionAlquilerActionPerformed
        boolean quiereAlquilar = this.jCheckBoxOperacionAlquiler.isSelected();
        this.jTextFieldPrecioAlquilerDesde.setEnabled(quiereAlquilar);
        this.jTextFieldPrecioAlquilerHasta.setEnabled(quiereAlquilar);
        if (!quiereAlquilar) {
            this.jTextFieldPrecioAlquilerDesde.setText("");
            this.jTextFieldPrecioAlquilerHasta.setText("");
        }
    }//GEN-LAST:event_jCheckBoxOperacionAlquilerActionPerformed

    private void jButtonPropSugDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPropSugDetallesActionPerformed
        if (!this.idPropiedadSeleccionada.equals("")){
            PropiedadInfo ifPropiedadInfo;
            ifPropiedadInfo = new PropiedadInfo("ConsultarPropiedadesDeSolicitud",this.idPropiedadSeleccionada, this.idClienteSolicitud);
            
            this.getDesktopPane().add(ifPropiedadInfo);
            ifPropiedadInfo.setVisible(true);
        } else{
            JOptionPane.showMessageDialog(this, "Debe seleccionar una propiedad de la lista","Info",JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonPropSugDetallesActionPerformed

    private void jTablePropSugMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePropSugMouseClicked
        this.idPropiedadSeleccionada = (String)this.jTablePropSug.getValueAt(this.jTablePropSug.rowAtPoint(evt.getPoint()), 0);
    }//GEN-LAST:event_jTablePropSugMouseClicked

    private void filtroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filtroFocusGained
        if (filtro.getText().equals("Buscar...")){
            filtro.setText("");
        }
    }//GEN-LAST:event_filtroFocusGained

    private void filtroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_filtroFocusLost
        if (filtro.getText().equals("")){
            filtro.setText("Buscar...");
        }
    }//GEN-LAST:event_filtroFocusLost

    private void filtroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtroKeyReleased
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.jTablePropSug.getModel());
        this.jTablePropSug.setRowSorter(sorter);
        
        String i = "(?i)";
        String texto= i.concat(this.filtro.getText());
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
    }//GEN-LAST:event_filtroKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Fabrica fab = new Fabrica();
        IControladorDatos icd = fab.getIControladorDatos();
        icd.imprimirJTable(this.jTablePropSug,"Propiedades Sugeridas");
    }//GEN-LAST:event_jButton1ActionPerformed

    public void actualizarInfoContacto(DataCliente dc){
        this.jTextFieldContactoNombre.setText(dc.getNombre());
        this.jTextFieldContactoApell.setText(dc.getApellido());
        this.jTextFieldContactoEmail.setText(dc.getEmail());
        this.jTextFieldContactoTel.setText(dc.getTelefono());
        this.jTextFieldContactoCel.setText(dc.getCelular());
        this.jTextFieldContactoDir.setText(dc.getDireccion());
        this.jTextAreaContactoInfoAdic.setText(dc.getInf_adicional());
        
        this.idClienteSolicitud = dc.getId();
    }
    
    // Caso ModificarSolicitud y AltaSolicitud
    private DataPedido crearDataPedidoDesdeFormulario() throws Exception{
        DataPedido dp = new DataPedido();
        
        dp.setId(this.idSolicitud);
        
        /// ######## Pestana Informacion Basica ########
        if (this.jRadioButtonTipoCasa.isSelected())
            dp.setTipo("casa");
        else if (this.jRadioButtonTipoTerreno.isSelected())
            dp.setTipo("terreno");
        else
            throw new Exception("Debe seleccionar el tipo (Casa o Terreno)");

        boolean quiereCompra = this.jCheckBoxOperacionCompra.isSelected();
        boolean quiereAlquilar = this.jCheckBoxOperacionAlquiler.isSelected();
        if (quiereAlquilar && !quiereCompra)
            dp.setEstado("alquiler");
        if (!quiereAlquilar && quiereCompra)
            dp.setEstado("venta");
        if (quiereAlquilar && quiereCompra)
            dp.setEstado("ambos");
        if (!quiereAlquilar && !quiereCompra)
            throw new Exception("Debe seleccionar la operacion (Alquiler o Compra)");

        if (this.jRadioButtonPHSi.isSelected())
            dp.setPH("si");
        else if (this.jRadioButtonPHNo.isSelected())
            dp.setPH("no");
        else
            dp.setPH("");
            
        if (this.jRadioButtonPUSi.isSelected())
            dp.setPU("si");
        else if (this.jRadioButtonPUNo.isSelected())
            dp.setPU("no");
        else
            dp.setPU("");

        if (this.jRadioButtonBancoSi.isSelected())
            dp.setAceptaBanco("si");
        else if (this.jRadioButtonBancoNo.isSelected())
            dp.setAceptaBanco("no");
        else
            dp.setAceptaBanco("");
        
        if (this.jRadioButtonFinanciacionSi.isSelected())
            dp.setConF("si");
        else if (this.jRadioButtonFinanciacionNo.isSelected())
            dp.setConF("no");
        else
            dp.setConF("");
        
        try{
            if (this.jCheckBoxOperacionAlquiler.isSelected()){
                if (!this.jTextFieldPrecioAlquilerDesde.getText().equals(""))
                    dp.setPrecioAL(Integer.parseInt(this.jTextFieldPrecioAlquilerDesde.getText()));
                else
                    dp.setPrecioAL(0);
                if (!this.jTextFieldPrecioAlquilerHasta.getText().equals(""))
                    dp.setPrecioAH(Integer.parseInt(this.jTextFieldPrecioAlquilerHasta.getText()));
                else
                    dp.setPrecioAH(0);
            }
            if (this.jCheckBoxOperacionCompra.isSelected()){
                if (!this.jTextFieldPrecioCompraDesde.getText().equals(""))
                    dp.setPrecioVL(Integer.parseInt(this.jTextFieldPrecioCompraDesde.getText()));
                else
                    dp.setPrecioVL(0);
                if (!this.jTextFieldPrecioCompraHasta.getText().equals(""))
                    dp.setPrecioVH(Integer.parseInt(this.jTextFieldPrecioCompraHasta.getText()));
                else
                    dp.setPrecioVH(0);
            }
        }
        catch(NumberFormatException exc){
            throw new Exception("Valores de precio en pestana Informacion Basica incorrectos");
        }
        
        TableModel modeloTablaZonas = this.jTableZonas.getModel();
        List<DataZona> listaZonas = new ArrayList<DataZona>();
        int nroZonas = modeloTablaZonas.getRowCount();

        for (int i=0; i<nroZonas; i++){
            if ((Boolean)modeloTablaZonas.getValueAt(i, 0)){
                DataZona dz = new DataZona();
                dz.setZona((String)modeloTablaZonas.getValueAt(i, 1));
                dz.setNorteG((Boolean)modeloTablaZonas.getValueAt(i, 2));
                dz.setSurG((Boolean)modeloTablaZonas.getValueAt(i, 3));
                
                if (!dz.isNorteG() && !dz.isSurG()){
                    dz.setNorteG(true);
                    dz.setSurG(true);
                }
                
                listaZonas.add(dz);
            }
        }
        
        if (listaZonas.isEmpty()){
            for (int i=0; i<nroZonas; i++){
                DataZona dz = new DataZona();
                dz.setZona((String)modeloTablaZonas.getValueAt(i, 1));
                dz.setNorteG(true);
                dz.setSurG(true);
                listaZonas.add(dz);
            }
        }
        dp.setZonas(listaZonas);

        /// ######## Pestana Descripcion de la Propiedad ########
        try {
            if (!this.jTextFieldSupTerrDesde.getText().equals(""))
                dp.setSuperficieL(Integer.parseInt(this.jTextFieldSupTerrDesde.getText()));
            else
                dp.setSuperficieL(0);
            if (!this.jTextFieldSupTerrHasta.getText().equals(""))
                dp.setSuperficieH(Integer.parseInt(this.jTextFieldSupTerrHasta.getText()));
            else
                dp.setSuperficieH(0);
        }
        catch(NumberFormatException exc){
            throw new Exception("Valores en pestana Descripcion de Propiedad incorrectos");
        }

        dp.setDormitoriosL(0);
        dp.setDormitoriosH(0);
        if (this.jCheckBoxDormitoriosMas3.isSelected())
            dp.setDormitoriosL(4);
        if (this.jCheckBoxDormitorios3.isSelected())
            dp.setDormitoriosL(3);            
        if (this.jCheckBoxDormitorios2.isSelected())
            dp.setDormitoriosL(2);
        if (this.jCheckBoxDormitorios1.isSelected())
            dp.setDormitoriosL(1);

        if (this.jCheckBoxDormitorios1.isSelected())
            dp.setDormitoriosH(1);
        if (this.jCheckBoxDormitorios2.isSelected())
            dp.setDormitoriosH(2);
        if (this.jCheckBoxDormitorios3.isSelected())
            dp.setDormitoriosH(3);            
        if (this.jCheckBoxDormitoriosMas3.isSelected())
            dp.setDormitoriosH(4);
        
        if (!this.jCheckBoxDormitoriosMas3.isSelected() &&
            !this.jCheckBoxDormitorios3.isSelected() &&
            !this.jCheckBoxDormitorios2.isSelected() &&
            !this.jCheckBoxDormitorios1.isSelected()){
        
            dp.setDormitoriosL(0);
            dp.setDormitoriosH(0);
        }    
        
        dp.setBanosL(0);
        dp.setBanosH(0);
        if (this.jCheckBoxBanosMas2.isSelected())
            dp.setBanosL(3);
        if (this.jCheckBoxBanos2.isSelected())
            dp.setBanosL(2);
        if (this.jCheckBoxBanos1.isSelected())
            dp.setBanosL(1);

        if (this.jCheckBoxBanos1.isSelected())
            dp.setBanosH(1);
        if (this.jCheckBoxBanos2.isSelected())
            dp.setBanosH(2);
        if (this.jCheckBoxBanosMas2.isSelected())
            dp.setBanosH(3);
        
        if (!this.jCheckBoxBanosMas2.isSelected() &&
            !this.jCheckBoxBanos2.isSelected() &&
            !this.jCheckBoxBanos1.isSelected()){
            
            dp.setBanosL(0);
            dp.setBanosH(0);
        }
            
        dp.setDescripcionAddicional(this.jTextAreaInfoAdic.getText());

        /// ######## Pestana Datos de Contacto ########
        DataCliente dc = new DataCliente(); 
        dc.setId(this.idClienteSolicitud);
        dc.setNombre(this.jTextFieldContactoNombre.getText());
        dc.setApellido(this.jTextFieldContactoApell.getText());
        dc.setEmail(this.jTextFieldContactoEmail.getText());
        dc.setTelefono(this.jTextFieldContactoTel.getText());
        dc.setCelular(this.jTextFieldContactoCel.getText());
        dc.setDireccion(this.jTextFieldContactoDir.getText());
        dc.setInf_adicional(this.jTextAreaContactoInfoAdic.getText());
        dp.setCliente(dc); 
        
        return dp;
    }
    
    // Caso ConsultarSolicitud o ModificarSolicitud
    private void crearFormularioDesdeDataPedido(int idSolicitud) throws Exception{
        // Lleno los campos con la solicitud
        Fabrica fab = new Fabrica();
        IControladorClientes icc = fab.getIControladorClientes();
        DataPedido dp;
        try{
            dp = icc.obtenerSolicitud(idSolicitud);
            
            this.idClienteSolicitud = dp.getCliente().getId();
            
            
            /// ######## Pestana Informacion Basica ########
            if (dp.getTipo().equals("casa")){
                this.jRadioButtonTipoCasa.setSelected(true);
                this.jRadioButtonTipoTerreno.setSelected(false);
            } else if (dp.getTipo().equals("terreno")){
                this.jRadioButtonTipoTerreno.setSelected(true);
                this.jRadioButtonTipoCasa.setSelected(false);
            }
            
            if (dp.getEstado().equals("alquiler")){
                this.jCheckBoxOperacionAlquiler.setSelected(true);
                this.jCheckBoxOperacionCompra.setSelected(false);
            } else if (dp.getEstado().equals("venta")){
                this.jCheckBoxOperacionCompra.setSelected(true);
                this.jCheckBoxOperacionAlquiler.setSelected(false);
            } else if (dp.getEstado().equals("ambos")){
                this.jCheckBoxOperacionAlquiler.setSelected(true);
                this.jCheckBoxOperacionCompra.setSelected(true);
            }
            
            if (dp.getPH().equals("si")){
                this.jRadioButtonPHSi.setSelected(true);
                this.jRadioButtonPHNo.setSelected(false);
            } else if (dp.getPH().equals("no")){
                this.jRadioButtonPHNo.setSelected(true);
                this.jRadioButtonPHSi.setSelected(false);
            } else {
                this.jRadioButtonPHNo.setSelected(false);
                this.jRadioButtonPHSi.setSelected(false);
            }
            
            if (dp.getPU().equals("si")){
                this.jRadioButtonPUSi.setSelected(true);
                this.jRadioButtonPUNo.setSelected(false);
            } else if (dp.getPU().equals("no")){
                this.jRadioButtonPUNo.setSelected(true);
                this.jRadioButtonPUSi.setSelected(false);
            } else {
                this.jRadioButtonPUNo.setSelected(false);
                this.jRadioButtonPUSi.setSelected(false);
            }
            
            if (dp.getAceptaBanco() == null || dp.getAceptaBanco().equals("")){
                this.jRadioButtonBancoSi.setSelected(false);
                this.jRadioButtonBancoNo.setSelected(false);
            } else if(dp.getAceptaBanco().equals("si")){
                this.jRadioButtonBancoSi.setSelected(true);
                this.jRadioButtonBancoNo.setSelected(false);
            } else if (dp.getAceptaBanco().equals("no")){
                this.jRadioButtonBancoNo.setSelected(true);
                this.jRadioButtonBancoSi.setSelected(false);
            }
            
            if (dp.getConF() == null || dp.getConF().equals("")){
                this.jRadioButtonFinanciacionSi.setSelected(false);
                this.jRadioButtonFinanciacionNo.setSelected(false);
            } else if(dp.getConF().equals("si")){
                this.jRadioButtonFinanciacionSi.setSelected(true);
                this.jRadioButtonFinanciacionNo.setSelected(false);
            } else if (dp.getConF().equals("no")){
                this.jRadioButtonFinanciacionNo.setSelected(true);
                this.jRadioButtonFinanciacionSi.setSelected(false);
            }
            
            if (dp.getEstado().equals("alquiler")){
                if (dp.getPrecioAL()!=0)
                    this.jTextFieldPrecioAlquilerDesde.setText(String.valueOf(dp.getPrecioAL()));
                if (dp.getPrecioAH()!=0)
                    this.jTextFieldPrecioAlquilerHasta.setText(String.valueOf(dp.getPrecioAH()));
                this.jTextFieldPrecioCompraDesde.setEnabled(false);
                this.jTextFieldPrecioCompraHasta.setEnabled(false);
            } else if (dp.getEstado().equals("venta")){
                if (dp.getPrecioVL()!=0)
                    this.jTextFieldPrecioCompraDesde.setText(String.valueOf(dp.getPrecioVL()));
                if (dp.getPrecioVH()!=0)
                    this.jTextFieldPrecioCompraHasta.setText(String.valueOf(dp.getPrecioVH()));
                this.jTextFieldPrecioAlquilerDesde.setEnabled(false);
                this.jTextFieldPrecioAlquilerHasta.setEnabled(false);
            } else if (dp.getEstado().equals("ambos")){
                if (dp.getPrecioAL()!=0)
                    this.jTextFieldPrecioAlquilerDesde.setText(String.valueOf(dp.getPrecioAL()));
                if (dp.getPrecioAH()!=0)
                    this.jTextFieldPrecioAlquilerHasta.setText(String.valueOf(dp.getPrecioAH()));
                if (dp.getPrecioVL()!=0)
                    this.jTextFieldPrecioCompraDesde.setText(String.valueOf(dp.getPrecioVL()));
                if (dp.getPrecioVH()!=0)
                    this.jTextFieldPrecioCompraHasta.setText(String.valueOf(dp.getPrecioVH()));
            }
            
            Object[] columns = {"Seleccion","Zonas","Norte","Sur"};
            
            final boolean[] editables = {false,false,false,false};
            if (this.modo.equals("ConsultarSolicitud")){
                // Nada
            } else if (this.modo.equals("ModificarSolicitud")){
                editables[0]=editables[2]=editables[3]=true;
            }
            
            DefaultTableModel modeloTablaZonas = new DefaultTableModel(new Object[][]{},columns){
                Class[] types = new Class [] {
                    java.lang.Boolean.class, java.lang.String.class,
                    java.lang.Boolean.class, java.lang.Boolean.class
                };
                
                @Override
                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return editables[columnIndex];
                }
            };
            
            List<DataZona> listaZonas = dp.getZonas();
            
            for(DataZona dz: listaZonas){
                Object[] row = {true, dz.getZona(),dz.isNorteG(), dz.isSurG()};
                modeloTablaZonas.addRow(row);
            }
            
            if (this.modo.equals("ConsultarSolicitud")){
                // Muestro los que tengo nomas
            } else if (this.modo.equals("ModificarSolicitud")){
                // Tengo que mostrar todos y dejar que elija
                for (String lugar : fab.getIControladorCasas().listarLugares()){
                    boolean encontroLugar = false;
                    for(DataZona dz: listaZonas){
                        if (lugar.equals(dz.getZona())){
                            encontroLugar = true;
                            break;
                        }
                    }
                    if (!encontroLugar){
                        Object[] row = {false, lugar,false, false};
                        modeloTablaZonas.addRow(row);
                    }
                }
            }
            
            this.jTableZonas.setModel(modeloTablaZonas);
            
            /// ######## Pestana Descripcion de la Propiedad ########
            if (dp.getSuperficieL()!=0)
                this.jTextFieldSupTerrDesde.setText(String.valueOf(dp.getSuperficieL()));
            if (dp.getSuperficieH()!=0)
                this.jTextFieldSupTerrHasta.setText(String.valueOf(dp.getSuperficieH()));
            
            // TODO Cuidado con los valores de dormitorios y banos
            this.jCheckBoxDormitorios1.setSelected(dp.getDormitoriosL()==1);
            this.jCheckBoxDormitorios2.setSelected(dp.getDormitoriosL()<=2 && 2<=dp.getDormitoriosH());
            this.jCheckBoxDormitorios3.setSelected(dp.getDormitoriosL()<=3 && 3<=dp.getDormitoriosH());
            this.jCheckBoxDormitoriosMas3.setSelected(dp.getDormitoriosH()>3);
            
            this.jCheckBoxBanos1.setSelected(dp.getBanosL()==1);
            this.jCheckBoxBanos2.setSelected(dp.getBanosL()<=2 && 2<=dp.getBanosH());
            this.jCheckBoxBanosMas2.setSelected(dp.getBanosH()>2);
           
            this.jTextAreaInfoAdic.setText(dp.getDescripcionAddicional());
            
            /// ######## Pestana Datos de Contacto ########
            this.jTextFieldContactoNombre.setText(dp.getCliente().getNombre());
            this.jTextFieldContactoApell.setText(dp.getCliente().getApellido());
            this.jTextFieldContactoEmail.setText(dp.getCliente().getEmail());
            this.jTextFieldContactoTel.setText(dp.getCliente().getTelefono());
            this.jTextFieldContactoCel.setText(dp.getCliente().getCelular());
            this.jTextFieldContactoDir.setText(dp.getCliente().getDireccion());
            this.jTextAreaContactoInfoAdic.setText(dp.getCliente().getInf_adicional());
            
            
            /// ######## Pestana Propiedades Sugeridas ########
            // Solamente si esta consultando la solicitud
            if (this.modo.equals("ConsultarSolicitud")){
                this.actualizarTablaPropiedadesSugeridas(dp.getCasas());
            }
            
        }
        catch (Exception exc){
            throw new Exception("Error creando el formulario");
        }
    }
    
    public void actualizarTablaPropiedadesSugeridas(List<DataCasa> dataCasas) throws Exception{
        Fabrica fab = new Fabrica();
        IControladorDatos icd = fab.getIControladorDatos();
                
        DefaultTableModel dtm = (DefaultTableModel)this.jTablePropSug.getModel();

        while(dtm.getRowCount()>0)
            dtm.removeRow(0);
        
        for (DataCasa dataC : dataCasas){
            Object[] row = new Object[dtm.getColumnCount()];

            row[0] = dataC.getId();

            if (dataC.getEstado().equals("ambos"))
                row[1] = "Venta y Alquiler";
            else if (dataC.getEstado().equals("alquiler"))
                row[1] = "Alquiler";
            else if (dataC.getEstado().equals("venta"))
                row[1] = "Venta";

            if (dataC.getTipo().equals("casa"))
                row[2] = "Casa";
            else if (dataC.getTipo().equals("terreno"))
                row[2] = "Terreno";

            row[3] = dataC.getZona();
            row[4] = dataC.getDireccion();

            if (dataC.getEstado().equals("ambos"))
                row[5] = "";
            else if (dataC.getEstado().equals("alquiler"))
                row[5] = dataC.getPrecioA();
            else if (dataC.getEstado().equals("venta"))
                row[5] = dataC.getPrecioV();

            try {
                if (!icd.isCasaMarcada(this.idClienteSolicitud, dataC.getId()))
                    row[6] = "Si";
                else
                    row[6] = "No";
            }
            catch (Exception exc){
                throw exc;
            }
            

            dtm.addRow(row);
        }
        this.jTablePropSug.setModel(dtm);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JTextField filtro;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonPropSugDetalles;
    private javax.swing.JButton jButtonSelCliente;
    private javax.swing.JCheckBox jCheckBoxBanos1;
    private javax.swing.JCheckBox jCheckBoxBanos2;
    private javax.swing.JCheckBox jCheckBoxBanosMas2;
    private javax.swing.JCheckBox jCheckBoxDormitorios1;
    private javax.swing.JCheckBox jCheckBoxDormitorios2;
    private javax.swing.JCheckBox jCheckBoxDormitorios3;
    private javax.swing.JCheckBox jCheckBoxDormitoriosMas3;
    private javax.swing.JCheckBox jCheckBoxOperacionAlquiler;
    private javax.swing.JCheckBox jCheckBoxOperacionCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelPropSug;
    private javax.swing.JRadioButton jRadioButtonBancoNo;
    private javax.swing.JRadioButton jRadioButtonBancoSi;
    private javax.swing.JRadioButton jRadioButtonFinanciacionNo;
    private javax.swing.JRadioButton jRadioButtonFinanciacionSi;
    private javax.swing.JRadioButton jRadioButtonPHNo;
    private javax.swing.JRadioButton jRadioButtonPHSi;
    private javax.swing.JRadioButton jRadioButtonPUNo;
    private javax.swing.JRadioButton jRadioButtonPUSi;
    private javax.swing.JRadioButton jRadioButtonTipoCasa;
    private javax.swing.JRadioButton jRadioButtonTipoTerreno;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTablePropSug;
    private javax.swing.JTable jTableZonas;
    private javax.swing.JTextArea jTextAreaContactoInfoAdic;
    private javax.swing.JTextArea jTextAreaInfoAdic;
    private javax.swing.JTextField jTextFieldContactoApell;
    private javax.swing.JTextField jTextFieldContactoCel;
    private javax.swing.JTextField jTextFieldContactoDir;
    private javax.swing.JTextField jTextFieldContactoEmail;
    private javax.swing.JTextField jTextFieldContactoNombre;
    private javax.swing.JTextField jTextFieldContactoTel;
    private javax.swing.JTextField jTextFieldPrecioAlquilerDesde;
    private javax.swing.JTextField jTextFieldPrecioAlquilerHasta;
    private javax.swing.JTextField jTextFieldPrecioCompraDesde;
    private javax.swing.JTextField jTextFieldPrecioCompraHasta;
    private javax.swing.JTextField jTextFieldSupTerrDesde;
    private javax.swing.JTextField jTextFieldSupTerrHasta;
    // End of variables declaration//GEN-END:variables

    // modo = "AltaSolicitud" | "ConsultarSolicitud" | "ModificarSolicitud" | "ConsultarSolicitudesDePropiedad"
    private String modo;
   
    private int idClienteSolicitud;
    public int idSolicitud;
    private String idPropiedadSeleccionada = "";
    

}
