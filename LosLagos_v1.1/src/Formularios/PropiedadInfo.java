/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import DataTypes.DataCasa;
import DataTypes.DataPedido;
import Interface.Fabrica;
import Interface.IControladorCasas;
import Interface.IControladorClientes;
import Interface.IControladorDatos;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author gonza
 */
public class PropiedadInfo extends javax.swing.JInternalFrame {
    

    /**
     * Creates new form PropiedadInfo
     */
     public String abrirImagen(){
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileFilter(new FiltroJPG());
            chooser.setAcceptAllFileFilterUsed(false);
            int e = chooser.showOpenDialog(null);

            path = "";
            if (e == JFileChooser.APPROVE_OPTION){
                if (chooser.getSelectedFile() != null) {
                    path = chooser.getSelectedFile().getPath();

                }
            }else if (e == JFileChooser.CANCEL_OPTION)
                return "";

            
            
            return path;
    }

    public PropiedadInfo(){
        initComponents();
        this.modo = "AltaPropiedad";
        this.setTitle("Alta Propiedad");
        this.jButtonAceptar.setText("Crear Propiedad");
        this.jTextFieldPrecioAlquiler.setEnabled(false);
        this.jTextFieldPrecioVenta.setEnabled(false);
        // Elimino la pestana de sugerencia de propiedades
        this.jTabbedPane1.remove(this.jPanelSolSug);
        
        Fabrica f = new Fabrica();
        IControladorCasas icc = f.getIControladorCasas();
        try {
            List<String> zonas = icc.listarLugares();
            this.jComboBoxZona.removeAllItems();
            for (String lugar: zonas) {
                this.jComboBoxZona.addItem(lugar);
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
            JDesktopPane desktopPane = this.getDesktopPane();
            desktopPane.remove(this);
            desktopPane.updateUI();
        }
            
        this.jLabelMarcarSolicitud.setVisible(false);
        this.jRadioButtonMarcarSolicitudNo.setVisible(false);
        this.jRadioButtonMarcarSolicitudSi.setVisible(false);
    }
    
    // idClienteSOlicitud solo para el caso de ConsultarPropiedadesDeSolicitud
    public PropiedadInfo(String modo, String idCasa, int idClienteSolicitud) {
        initComponents();
        
        this.modo = modo;
        
        // Seteo los botones
        if (this.modo.equals("ModificarPropiedad")){
            this.setTitle("Modificar Propiedad");
            this.jButtonAceptar.setText("Modificar");
        }else if (this.modo.equals("ConsultarPropiedad")){
            this.setTitle("Consultar Propiedad");
            this.jButtonAceptar.setText("Aceptar");
        }else if (this.modo.equals("ConsultarPropiedadesDeSolicitud")){
            this.setTitle("Consultar Propiedades de Solicitud");
            this.jButtonAceptar.setText("Aceptar");
        }
        
        // Lleno la lista con las zonas
        Fabrica f = new Fabrica();
        IControladorCasas icc = f.getIControladorCasas();
        try {
            List<String> zonas = icc.listarLugares();
            this.jComboBoxZona.removeAllItems();
            for (String lugar: zonas) {
                this.jComboBoxZona.addItem(lugar);
            }
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
            JDesktopPane desktopPane = this.getDesktopPane();
            desktopPane.remove(this);
            desktopPane.updateUI();
        }
        
        try{
            
            this.idPropiedadInicial = idCasa;
            this.idClienteSolicitud = idClienteSolicitud;
            
            this.crearFormularioDesdeDataCasa(idCasa, idClienteSolicitud);
           
            // Dependiendo del tipo veo que mostrar o dejar modificable
            if (this.modo.equals("ModificarPropiedad")){
                // Elimino la pestana de sugerencia de solicitudes
                this.jTabbedPane1.remove(this.jPanelSolSug);
                this.jLabelMarcarSolicitud.setVisible(false);
                this.jRadioButtonMarcarSolicitudNo.setVisible(false);
                this.jRadioButtonMarcarSolicitudSi.setVisible(false);
            } else if (this.modo.equals("ConsultarPropiedad") || this.modo.equals("ConsultarPropiedadesDeSolicitud")){
                
                if (this.modo.equals("ConsultarPropiedad")){
                    this.jLabelMarcarSolicitud.setVisible(false);
                    this.jRadioButtonMarcarSolicitudNo.setVisible(false);
                    this.jRadioButtonMarcarSolicitudSi.setVisible(false);
                }
                
                if (this.modo.equals("ConsultarPropiedadesDeSolicitud")){
                    // Elimino la pestana de sugerencia de solicitudes
                    this.jTabbedPane1.remove(this.jPanelSolSug);
                }
                
                // Inhabilito los inputs 
                /// ######## Pestana Informacion Basica ########
                this.jTextFieldId.setEditable(false);
                this.jRadioButtonTipoCasa.setEnabled(false);
                this.jRadioButtonTipoTerreno.setEnabled(false);
                this.jCheckBoxOperacionAlquiler.setEnabled(false);
                this.jCheckBoxOperacionVenta.setEnabled(false);
                this.jCheckBoxPH.setEnabled(false);
                this.jCheckBoxPU.setEnabled(false);
                this.jCheckBoxBanco.setEnabled(false);
                this.jCheckBoxFinanciacion.setEnabled(false);
                
                this.jTextFieldPrecioVenta.setEditable(false);
                this.jTextFieldPrecioAlquiler.setEditable(false);
                this.jComboBoxZona.setEnabled(false);
                this.jButtonAgregarZona.setEnabled(false);
                this.jButtonEliminarZona.setEnabled(false);
                this.jRadioButtonZonaGianNorte.setEnabled(false);
                this.jRadioButtonZonaGianSur.setEnabled(false);
                this.jTextFieldDireccion.setEditable(false);
                this.jButtonCargarFoto.setEnabled(false);
                
                /// ######## Pestana Descripcion de la Propiedad ########
                this.jTextFieldSupTerr.setEditable(false);
                this.jTextFieldDormitorios.setEditable(false);
                this.jTextFieldBanos.setEditable(false);
                this.jTextAreaInfoAdic.setEditable(false);
                
                /// ######## Pestana Datos de Contacto ########
                this.jTextFieldContactoNombre.setEditable(false);
                this.jTextFieldContactoApell.setEditable(false);
                this.jTextFieldContactoEmail.setEditable(false);
                this.jTextFieldContactoTel.setEditable(false);
                this.jTextFieldContactoCel.setEditable(false);
                this.jTextFieldContactoDir.setEditable(false);
                this.jTextAreaContactoInfoAdic.setEditable(false);   
            }
        }
        catch (Exception exc){
            JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
            JDesktopPane desktopPane = this.getDesktopPane();
            desktopPane.remove(this);
            desktopPane.updateUI();
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

        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxZona = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jRadioButtonZonaGianNorte = new javax.swing.JRadioButton();
        jRadioButtonZonaGianSur = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldDireccion = new javax.swing.JTextField();
        jButtonAgregarZona = new javax.swing.JButton();
        jButtonEliminarZona = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldPrecioAlquiler = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldPrecioVenta = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jCheckBoxPH = new javax.swing.JCheckBox();
        jCheckBoxPU = new javax.swing.JCheckBox();
        jCheckBoxFinanciacion = new javax.swing.JCheckBox();
        jCheckBoxBanco = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jCheckBoxOperacionAlquiler = new javax.swing.JCheckBox();
        jCheckBoxOperacionVenta = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jRadioButtonTipoCasa = new javax.swing.JRadioButton();
        jRadioButtonTipoTerreno = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jButtonCargarFoto = new javax.swing.JButton();
        jLabelMarcarSolicitud = new javax.swing.JLabel();
        jRadioButtonMarcarSolicitudSi = new javax.swing.JRadioButton();
        jRadioButtonMarcarSolicitudNo = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInfoAdic = new javax.swing.JTextArea();
        jTextFieldSupTerr = new javax.swing.JTextField();
        jTextFieldDormitorios = new javax.swing.JTextField();
        jTextFieldBanos = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
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
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaContactoInfoAdic = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldContactoCel = new javax.swing.JTextField();
        jPanelSolSug = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableSolSug = new javax.swing.JTable();
        filtro = new javax.swing.JTextField();
        jButtonSolSugDetalles = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        setTitle("Alta Propiedad");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la Propiedad"));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Foto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ubicacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel5.setText("Zona: ");

        jLabel6.setText("Ubicacion respecto a Giannattasio: ");

        buttonGroup1.add(jRadioButtonZonaGianNorte);
        jRadioButtonZonaGianNorte.setText("Norte");

        buttonGroup1.add(jRadioButtonZonaGianSur);
        jRadioButtonZonaGianSur.setText("Sur");

        jLabel7.setText("Direccion: ");

        jTextFieldDireccion.setColumns(20);

        jButtonAgregarZona.setText("+");
        jButtonAgregarZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarZonaActionPerformed(evt);
            }
        });

        jButtonEliminarZona.setText("-");
        jButtonEliminarZona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarZonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAgregarZona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonEliminarZona))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonZonaGianNorte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonZonaGianSur))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxZona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAgregarZona)
                    .addComponent(jButtonEliminarZona))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jRadioButtonZonaGianNorte)
                    .addComponent(jRadioButtonZonaGianSur))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Precio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel8.setText("Alquiler: ");

        jTextFieldPrecioAlquiler.setColumns(10);

        jLabel19.setText("Venta: ");

        jTextFieldPrecioVenta.setColumns(10);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPrecioAlquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldPrecioAlquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextFieldPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Basico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bitstream Vera Sans", 1, 12))); // NOI18N

        jLabel3.setText("Propiedad Horizontal: ");

        jLabel4.setText("Padron Unico: ");

        jLabel20.setText("Financiacion:");

        jLabel22.setText("Banco:");

        jLabel1.setText("Operacion: ");

        jCheckBoxOperacionAlquiler.setText("Alquiler");
        jCheckBoxOperacionAlquiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOperacionAlquilerActionPerformed(evt);
            }
        });

        jCheckBoxOperacionVenta.setText("Venta");
        jCheckBoxOperacionVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOperacionVentaActionPerformed(evt);
            }
        });

        jLabel2.setText("Tipo: ");

        buttonGroup2.add(jRadioButtonTipoCasa);
        jRadioButtonTipoCasa.setText("Casa");

        buttonGroup2.add(jRadioButtonTipoTerreno);
        jRadioButtonTipoTerreno.setText("Terreno");

        jLabel18.setText("Identificador: ");

        jTextFieldId.setColumns(20);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel18))
                .addGap(36, 36, 36)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBoxBanco)
                    .addComponent(jCheckBoxFinanciacion)
                    .addComponent(jCheckBoxPU)
                    .addComponent(jCheckBoxPH)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxOperacionAlquiler)
                            .addComponent(jRadioButtonTipoCasa))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonTipoTerreno)
                            .addComponent(jCheckBoxOperacionVenta)))
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButtonTipoCasa)
                    .addComponent(jRadioButtonTipoTerreno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCheckBoxOperacionAlquiler)
                        .addComponent(jCheckBoxOperacionVenta)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBoxPH)
                    .addComponent(jLabel3))
                .addGap(6, 6, 6)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jCheckBoxPU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jCheckBoxFinanciacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addComponent(jCheckBoxBanco))
                .addGap(8, 8, 8))
        );

        jButtonCargarFoto.setText("Cargar Foto");
        jButtonCargarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargarFotoActionPerformed(evt);
            }
        });

        jLabelMarcarSolicitud.setText("Tomar en cuenta para solicitud:");

        buttonGroup5.add(jRadioButtonMarcarSolicitudSi);
        jRadioButtonMarcarSolicitudSi.setText("Si");
        jRadioButtonMarcarSolicitudSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMarcarSolicitudSiActionPerformed(evt);
            }
        });

        buttonGroup5.add(jRadioButtonMarcarSolicitudNo);
        jRadioButtonMarcarSolicitudNo.setText("No");
        jRadioButtonMarcarSolicitudNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMarcarSolicitudNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jButtonCargarFoto)
                            .addGap(120, 120, 120)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelMarcarSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButtonMarcarSolicitudSi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonMarcarSolicitudNo)
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMarcarSolicitud)
                    .addComponent(jRadioButtonMarcarSolicitudSi)
                    .addComponent(jRadioButtonMarcarSolicitudNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCargarFoto)
                .addGap(20, 20, 20))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Informacion Basica", jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Descriptivos"));

        jLabel9.setText("Superficie del Terreno: ");

        jLabel10.setText("Dormitorios: ");

        jLabel11.setText("Banos: ");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion Adicional", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jTextAreaInfoAdic.setColumns(20);
        jTextAreaInfoAdic.setRows(5);
        jScrollPane1.setViewportView(jTextAreaInfoAdic);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextFieldSupTerr.setColumns(10);

        jTextFieldDormitorios.setColumns(10);

        jTextFieldBanos.setColumns(10);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBanos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDormitorios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldSupTerr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 463, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldSupTerr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldDormitorios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldBanos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Descripcion", jPanel3);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Contacto"));

        jLabel13.setText("Nombre: ");

        jLabel14.setText("Apellido: ");

        jLabel15.setText("Email: ");

        jLabel16.setText("Telefono: ");

        jLabel17.setText("Direccion: ");

        jTextFieldContactoNombre.setColumns(20);

        jTextFieldContactoApell.setColumns(20);

        jTextFieldContactoEmail.setColumns(20);

        jTextFieldContactoTel.setColumns(20);

        jTextFieldContactoDir.setColumns(20);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agente de la Propiedad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jTextAreaContactoInfoAdic.setColumns(20);
        jTextAreaContactoInfoAdic.setRows(5);
        jScrollPane2.setViewportView(jTextAreaContactoInfoAdic);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel21.setText("Celular");

        jTextFieldContactoCel.setColumns(20);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel21))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldContactoCel)
                            .addComponent(jTextFieldContactoDir)
                            .addComponent(jTextFieldContactoTel)
                            .addComponent(jTextFieldContactoEmail)
                            .addComponent(jTextFieldContactoApell)
                            .addComponent(jTextFieldContactoNombre))
                        .addGap(0, 441, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldContactoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldContactoApell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldContactoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldContactoTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldContactoCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldContactoDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contacto", jPanel6);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Solicitudes Sugeridas"));

        jTableSolSug.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cliente", "Tomar en cuenta", "IdCliente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSolSug.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSolSugMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableSolSug);

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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addComponent(filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonSolSugDetalles.setText("Ver Detalles");
        jButtonSolSugDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSolSugDetallesActionPerformed(evt);
            }
        });

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSolSugLayout = new javax.swing.GroupLayout(jPanelSolSug);
        jPanelSolSug.setLayout(jPanelSolSugLayout);
        jPanelSolSugLayout.setHorizontalGroup(
            jPanelSolSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSolSugLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSolSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSolSugLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSolSugDetalles)))
                .addContainerGap())
        );
        jPanelSolSugLayout.setVerticalGroup(
            jPanelSolSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSolSugLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSolSugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSolSugDetalles)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Solicitudes", jPanelSolSug);

        jButtonAceptar.setText("Crear Propiedad");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCancelar)
                        .addGap(28, 28, 28)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAceptar)
                    .addComponent(jButtonCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCargarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargarFotoActionPerformed

        path = this.abrirImagen();
        if (!path.equals("")){
            //si la imagen
            ImageIcon image = new ImageIcon(path);
            ImageIcon imageScalada = new ImageIcon(image.getImage().getScaledInstance(this.jLabel12.getWidth(), this.jLabel12.getHeight(), 1));
            this.jLabel12.setIcon(imageScalada);
        }
        
        /*BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
        JLabel picLabel = new JLabel(new ImageIcon( myPicture ))
        add( picLabel )*/
    }//GEN-LAST:event_jButtonCargarFotoActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        javax.swing.JDesktopPane desktopPane =  this.getDesktopPane();
        desktopPane.remove(this);
        desktopPane.updateUI();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        Fabrica fab = new Fabrica();
        IControladorCasas icc = fab.getIControladorCasas();
        
        if (this.modo.equals("ConsultarPropiedad") || this.modo.equals("ConsultarPropiedadesDeSolicitud")){
            // Cierro la ventana
            JDesktopPane desktopPane = this.getDesktopPane();
            desktopPane.remove(this);
            desktopPane.updateUI();
        } else if (this.modo.equals("AltaPropiedad") || this.modo.equals("ModificarPropiedad")){
            DataCasa dc;
            try{
                dc = this.crearDataCasaDesdeFormulario();
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
                return;
            }
            
            if (this.modo.equals("AltaPropiedad")){
                int respuesta;
                respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea crear la propiedad?", "Crear Propiedad", 0);
                if (respuesta == 0){
                    // Quiere crear la propiedad
                    try{
                        List<DataPedido> solSugeridas = icc.altaCasa(dc);
                        int quiereVerSolicitudes;
                        quiereVerSolicitudes = JOptionPane.showConfirmDialog(this,
                                "Propiedad creada con exito.\nDesea ver las solicitudes cuyos requisitos son cumplidos por la nueva propiedad?",
                                "Ver Solicitudes", 0);
                        if (quiereVerSolicitudes == 0){
                            SolicitudLista ifSolLista = new SolicitudLista(solSugeridas);
                            this.getDesktopPane().add(ifSolLista);
                            ifSolLista.setVisible(true);
                        } else if (quiereVerSolicitudes == 1){
                            // Nada
                        }
                        JDesktopPane desktopPane = this.getDesktopPane();
                        desktopPane.remove(this);
                        desktopPane.updateUI();
                    }
                    catch (Exception exc){
                        JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else if (respuesta == 1){
                    // No la quiere crear, nada
                    JOptionPane.showMessageDialog(this, "No se ha creado la propiedad","Info",JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (this.modo.equals("ModificarPropiedad")){
                int respuesta;
                respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea modificar la propiedad?", "Modificar Propiedad", 0);
                if (respuesta == 0){
                    // Quiere modificar la propiedad
                    try{
                        
                        List<DataPedido> solSugeridas = icc.modificarCasa(this.idPropiedadInicial,dc);
                        
                        Component[] ventanas = this.getDesktopPane().getComponents();
                        PropiedadLista ventanaListaPropiedades = null;
                        for(Component comp: ventanas){
                            if (comp instanceof PropiedadLista){
                                ventanaListaPropiedades = (PropiedadLista)comp;
                                break;
                            }
                        }
                        if (ventanaListaPropiedades != null){
                            ventanaListaPropiedades.actualizarTablaPropiedades(icc.listarCasas());
                        }
                            
                        
                        int quiereVerSolicitudes;
                        quiereVerSolicitudes = JOptionPane.showConfirmDialog(this,
                                "Propiedad modificada con exito.\nDesea ver las solicitudes cuyos requisitos son cumplidos por la nueva propiedad?",
                                "Ver Solicitudes", 0);
                        if (quiereVerSolicitudes == 0){
                            SolicitudLista ifSolLista = new SolicitudLista(solSugeridas);
                            this.getDesktopPane().add(ifSolLista);
                            ifSolLista.setVisible(true);
                        } else if (quiereVerSolicitudes == 1){
                            // Nada
                        }
                        
                        JDesktopPane desktopPane = this.getDesktopPane();
                        desktopPane.remove(this);
                        desktopPane.updateUI();
                    }
                    catch (Exception exc){
                        JOptionPane.showMessageDialog(this, exc.getMessage(),"Error",0);
                    }
                } else if (respuesta == 1){
                    // No la quiere modificar, cierro la ventana
                    JOptionPane.showMessageDialog(this, "No se ha modificado la propiedad","Info",0);
                }
            }
        }
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jCheckBoxOperacionAlquilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOperacionAlquilerActionPerformed
        this.jTextFieldPrecioAlquiler.setEnabled(this.jCheckBoxOperacionAlquiler.isSelected());
        if (!this.jCheckBoxOperacionAlquiler.isSelected())
            this.jTextFieldPrecioAlquiler.setText("");
    }//GEN-LAST:event_jCheckBoxOperacionAlquilerActionPerformed

    private void jCheckBoxOperacionVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOperacionVentaActionPerformed
        this.jTextFieldPrecioVenta.setEnabled(this.jCheckBoxOperacionVenta.isSelected());
        if (!this.jCheckBoxOperacionVenta.isSelected())
            this.jTextFieldPrecioVenta.setText("");
    }//GEN-LAST:event_jCheckBoxOperacionVentaActionPerformed

    private void jTableSolSugMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSolSugMouseClicked
        this.idSolicitudSeleccionada = (Integer)this.jTableSolSug.getValueAt(this.jTableSolSug.rowAtPoint(evt.getPoint()), 0);
    }//GEN-LAST:event_jTableSolSugMouseClicked

    private void jButtonSolSugDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSolSugDetallesActionPerformed
        if (this.idSolicitudSeleccionada != -1){
            SolicitudInfo ifSolicitudInfo;
            ifSolicitudInfo = new SolicitudInfo("ConsultarSolicitudesDePropiedad",this.idSolicitudSeleccionada);
            this.getDesktopPane().add(ifSolicitudInfo);
            ifSolicitudInfo.setVisible(true);
        } else{
            JOptionPane.showMessageDialog(this, "Debe seleccionar una solicitud de la lista","Info",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonSolSugDetallesActionPerformed

    private void jButtonAgregarZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarZonaActionPerformed
        Fabrica f = new Fabrica();
        IControladorCasas icc = f.getIControladorCasas();
        
        String zona = JOptionPane.showInputDialog(this, "Ingrese una zona", "Zona", JOptionPane.INFORMATION_MESSAGE);
        
        if (zona == null || zona.equals("")){
            JOptionPane.showMessageDialog(this, "No se ha creado ninguna zona", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            icc.ingresarLugar(zona);
            List<String> zonas = icc.listarLugares();
            
            this.jComboBoxZona.removeAllItems();
            for (String lugar: zonas) {
                this.jComboBoxZona.addItem(lugar);
            }
            this.jComboBoxZona.setSelectedItem(zona);
            JOptionPane.showMessageDialog(this, "Se ha agregado la zona exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAgregarZonaActionPerformed

    private void jButtonEliminarZonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarZonaActionPerformed
        Fabrica f = new Fabrica();
        IControladorCasas icc = f.getIControladorCasas();
        
        List<String> zonas = icc.listarLugares();
        if (zonas.isEmpty()){
            JOptionPane.showMessageDialog(this, "No existen zonas en el sistema", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
            
        String zona = (String) JOptionPane.showInputDialog(this,"Seleccione una zona a eliminar",null,
            JOptionPane.INFORMATION_MESSAGE, null, zonas.toArray(),zonas.get(0));
        
        if (zona == null || zona.equals("")){
            JOptionPane.showMessageDialog(this, "No se ha eliminado niguna zona", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try{
            JOptionPane.showConfirmDialog(this, "Esta seguro? Debera modificar las propiedades creadas con esa zona para no generar inconsisencias", "Cuidado", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
            icc.eliminarLugar(zona);
            zonas = icc.listarLugares();
            this.jComboBoxZona.removeAllItems();
            for (String lugar: zonas){
                this.jComboBoxZona.addItem(lugar);
            }
            JOptionPane.showMessageDialog(this, "Se ha eliminado la zona exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(Exception exc){
            JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonEliminarZonaActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:

        if (!path.equals("")){
            VisorImg visor = new VisorImg(path);
            this.getDesktopPane().add(visor);
            visor.setVisible(true);
        }
    }//GEN-LAST:event_jLabel12MouseClicked

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
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(this.jTableSolSug.getModel());
        this.jTableSolSug.setRowSorter(sorter);
        
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

    private void jRadioButtonMarcarSolicitudSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMarcarSolicitudSiActionPerformed
        Fabrica fab = new Fabrica();
        IControladorDatos icd = fab.getIControladorDatos();
        try {
            icd.desMarcarCasa(this.idClienteSolicitud, this.idPropiedadInicial);
        }
        catch (Exception exc){
            JOptionPane.showMessageDialog(this, "No se ha podido tomar en cuenta la propiedad", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        Component[] ventanas = this.getDesktopPane().getComponents();
        SolicitudInfo ventanaSolicitudInfo = null;
        for(Component comp: ventanas){
            if (comp instanceof SolicitudInfo){
                ventanaSolicitudInfo = (SolicitudInfo)comp;
                break;
            }
        }
        if (ventanaSolicitudInfo != null){
            IControladorClientes icc = fab.getIControladorClientes();
            try{
                ventanaSolicitudInfo.actualizarTablaPropiedadesSugeridas(icc.obtenerSolicitud(ventanaSolicitudInfo.idSolicitud).getCasas());
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(this, "No se han podido actualizar las propiedades", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }//GEN-LAST:event_jRadioButtonMarcarSolicitudSiActionPerformed

    private void jRadioButtonMarcarSolicitudNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMarcarSolicitudNoActionPerformed
        Fabrica fab = new Fabrica();
        IControladorDatos icd = fab.getIControladorDatos();
        try {
            icd.marcarCasa(this.idClienteSolicitud, this.idPropiedadInicial);
        }
        catch (Exception exc){
            JOptionPane.showMessageDialog(this, "No se ha podido dejar de tomar en cuenta la propiedad", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        Component[] ventanas = this.getDesktopPane().getComponents();
        SolicitudInfo ventanaSolicitudInfo = null;
        for(Component comp: ventanas){
            if (comp instanceof SolicitudInfo){
                ventanaSolicitudInfo = (SolicitudInfo)comp;
                break;
            }
        }
        if (ventanaSolicitudInfo != null){
            IControladorClientes icc = fab.getIControladorClientes();
            try{
                ventanaSolicitudInfo.actualizarTablaPropiedadesSugeridas(icc.obtenerSolicitud(ventanaSolicitudInfo.idSolicitud).getCasas());
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(this, "No se han podido actualizar las propiedades", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_jRadioButtonMarcarSolicitudNoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Fabrica fab = new Fabrica();
        IControladorDatos icd = fab.getIControladorDatos();
        icd.imprimirJTable(this.jTableSolSug,"Solicitudes Sugeridas");
    }//GEN-LAST:event_jButton1ActionPerformed

    private DataCasa crearDataCasaDesdeFormulario() throws Exception{
        DataCasa dc = new DataCasa();
        
        /// ######## Pestana Informacion Basica ########
        if (this.jTextFieldId.getText().equals(""))
            throw new Exception("Identificador no puede ser vacio");
        
        dc.setId(this.jTextFieldId.getText());

        if (this.jRadioButtonTipoCasa.isSelected())
            dc.setTipo("casa");
        else if (this.jRadioButtonTipoTerreno.isSelected())
            dc.setTipo("terreno");
        else
            throw new Exception("Debe seleccionar un Tipo (Casa o Terreno)");

        if (this.jCheckBoxOperacionAlquiler.isSelected())
            dc.setEstado("alquiler");
        if (this.jCheckBoxOperacionVenta.isSelected())
            dc.setEstado("venta");
        if (this.jCheckBoxOperacionAlquiler.isSelected() && this.jCheckBoxOperacionVenta.isSelected())
            dc.setEstado("ambos");
        if (!this.jCheckBoxOperacionAlquiler.isSelected() && !this.jCheckBoxOperacionVenta.isSelected())
            throw new Exception("Debe seleccionar el tipo de Operacion (Alquiler o Venta)");

        
        dc.setPH(this.jCheckBoxPH.isSelected());
        dc.setPU(this.jCheckBoxPU.isSelected());
            
        dc.setAceptaBanco(this.jCheckBoxBanco.isSelected());
        dc.setTieneF(this.jCheckBoxFinanciacion.isSelected());

        try{
            if (this.jCheckBoxOperacionAlquiler.isSelected())
                dc.setPrecioA(Integer.parseInt(this.jTextFieldPrecioAlquiler.getText()));

            if (this.jCheckBoxOperacionVenta.isSelected())
                dc.setPrecioV(Integer.parseInt(this.jTextFieldPrecioVenta.getText()));
            }
        catch(NumberFormatException exc){
            throw new Exception("Valores de precio en pestana Informacion Basica incorrectos");
        }

        dc.setZona((String)this.jComboBoxZona.getSelectedItem());

        if (this.jRadioButtonZonaGianNorte.isSelected())
            dc.setNorteG(true);
        else if (this.jRadioButtonZonaGianSur.isSelected())
            dc.setNorteG(false);
        else
            throw new Exception("Debe seleccionar una ubicacion respecto a Giannattasio");
        
        dc.setDireccion(this.jTextFieldDireccion.getText());

        List<String> images = new ArrayList<String>();
        images.add(this.path);
        dc.setImages(images);
        
        /// ######## Pestana Descripcion de la Propiedad ########
        try{
            if (this.jRadioButtonTipoCasa.isSelected()){
                dc.setSuperficie(Integer.parseInt(this.jTextFieldSupTerr.getText()));
                dc.setDormitorios(Integer.parseInt(this.jTextFieldDormitorios.getText()));
                dc.setBanos(Integer.parseInt(this.jTextFieldBanos.getText()));
            }
            
        }
        catch(NumberFormatException exc){
            throw new Exception("Valores en pestana Descripcion de Propiedad incorrectos");
        }

        dc.setEspecificacion(this.jTextAreaInfoAdic.getText());

        
        
        /// ######## Pestana Datos de Contacto ########
        dc.setNombre(this.jTextFieldContactoNombre.getText());
        dc.setApellido(this.jTextFieldContactoApell.getText());
        dc.setEmail(this.jTextFieldContactoEmail.getText());
        dc.setTelefono(this.jTextFieldContactoTel.getText());
        dc.setCelular(this.jTextFieldContactoCel.getText());
        dc.setDireccionDueno(this.jTextFieldContactoDir.getText());
        dc.setPrecedencia(this.jTextAreaContactoInfoAdic.getText());
        
        return dc;
    }
    
    private void crearFormularioDesdeDataCasa(String idCasa, int idClienteSolicitud) throws Exception {
        Fabrica fab = new Fabrica();
        IControladorCasas icc = fab.getIControladorCasas();
        IControladorDatos icd = fab.getIControladorDatos();
        DataCasa dc;
        try{
             dc = icc.obtenerCasa(idCasa);
        
             if (this.modo.equals("ConsultarPropiedadesDeSolicitud")){  
                boolean isPropiedadMarcada = icd.isCasaMarcada(idClienteSolicitud, this.idPropiedadInicial);
                this.jRadioButtonMarcarSolicitudSi.setSelected(!isPropiedadMarcada);
                this.jRadioButtonMarcarSolicitudNo.setSelected(isPropiedadMarcada);
             }
             
             
            /// ######## Pestana Informacion Basica ########
            this.jTextFieldId.setText(dc.getId());

            if (dc.getTipo().equals("casa")){
                this.jRadioButtonTipoCasa.setSelected(true);
                this.jRadioButtonTipoTerreno.setSelected(false);
            } else if (dc.getTipo().equals("terreno")){
                this.jRadioButtonTipoTerreno.setSelected(true);
                this.jRadioButtonTipoCasa.setSelected(false);
            }

            if (dc.getEstado().equals("alquiler")){
                this.jCheckBoxOperacionAlquiler.setSelected(true);
            }
            if (dc.getEstado().equals("venta")){
                this.jCheckBoxOperacionVenta.setSelected(true);
            }
            if (dc.getEstado().equals("ambos")){
                this.jCheckBoxOperacionAlquiler.setSelected(true);
                this.jCheckBoxOperacionVenta.setSelected(true);
            }

            this.jCheckBoxPH.setSelected(dc.isPH());
            this.jCheckBoxPU.setSelected(dc.isPU());
            
            this.jCheckBoxBanco.setSelected(dc.isAceptaBanco());
            this.jCheckBoxFinanciacion.setSelected(dc.isTieneF());

            if (dc.getEstado().equals("alquiler")){
                this.jTextFieldPrecioAlquiler.setText(String.valueOf(dc.getPrecioA()));
                this.jTextFieldPrecioVenta.setEnabled(false);
            } else if (dc.getEstado().equals("venta")){
                this.jTextFieldPrecioVenta.setText(String.valueOf(dc.getPrecioV()));
                this.jTextFieldPrecioAlquiler.setEnabled(false);
            } else if (dc.getEstado().equals("ambos")){
                this.jTextFieldPrecioAlquiler.setText(String.valueOf(dc.getPrecioA()));
                this.jTextFieldPrecioVenta.setText(String.valueOf(dc.getPrecioV()));
            }

            this.jComboBoxZona.setSelectedItem(dc.getZona());

            if (dc.isNorteG()){
                this.jRadioButtonZonaGianNorte.setSelected(true);
                this.jRadioButtonZonaGianSur.setSelected(false);
            } else {
                this.jRadioButtonZonaGianSur.setSelected(true);
                this.jRadioButtonZonaGianNorte.setSelected(false);
            }

            this.jTextFieldDireccion.setText(dc.getDireccion());

            if (dc.getImages().size()>0)
                this.path = dc.getImages().get(0);
            // Colocar la foto!
            if (!path.equals("")){
                ImageIcon image = new ImageIcon(path);
                // 313, 241
                int width = 313;
                int height = 241;
                if (this.jLabel12.getWidth()!=0)
                    width = this.jLabel12.getWidth();
                if (this.jLabel12.getHeight()!=0)
                    height = this.jLabel12.getHeight();
                ImageIcon imageScalada = new ImageIcon(image.getImage().getScaledInstance(width, height, 1));
                this.jLabel12.setIcon(imageScalada);
            }

            /// ######## Pestana Descripcion de la Propiedad ########
            if (dc.getSuperficie() != 0)
                this.jTextFieldSupTerr.setText(String.valueOf(dc.getSuperficie()));
            if (dc.getDormitorios() != 0)
                this.jTextFieldDormitorios.setText(String.valueOf(dc.getDormitorios()));
            if (dc.getBanos() != 0)
                this.jTextFieldBanos.setText(String.valueOf(dc.getBanos()));
            
            this.jTextAreaInfoAdic.setText(dc.getEspecificacion());

            /// ######## Pestana Datos de Contacto ########
            this.jTextFieldContactoNombre.setText(dc.getNombre());
            this.jTextFieldContactoApell.setText(dc.getApellido());
            this.jTextFieldContactoEmail.setText(dc.getEmail());
            this.jTextFieldContactoTel.setText(dc.getTelefono());
            this.jTextFieldContactoCel.setText(dc.getCelular());
            this.jTextFieldContactoDir.setText(dc.getDireccionDueno());
            this.jTextAreaContactoInfoAdic.setText(dc.getPrecedencia());
            
            /// ######## Pestana Solicitudes Sugeridas ########
            // Solamente si esta consultando la propiedad
            if (this.modo.equals("ConsultarPropiedad")){
                DefaultTableModel dtm = (DefaultTableModel)this.jTableSolSug.getModel();

                int i = 0;
                for (DataPedido dataP : dc.getPedidos()){
                    Object[] row = new Object[dtm.getColumnCount()];

                    row[0] = dataP.getId();
                    row[1] = dataP.getCliente().getNombre()+dataP.getCliente().getApellido();
                    
                    if (!icd.isCasaMarcada(dataP.getCliente().getId(), this.idPropiedadInicial))
                        row[2] = "Si";
                    else 
                        row[2] = "No";
                    
                    
                    row[3] = dataP.getCliente().getId();

                    dtm.addRow(row);
                    i++;
                }
                this.jTableSolSug.setModel(dtm);
                this.jTableSolSug.getColumnModel().removeColumn(this.jTableSolSug.getColumnModel().getColumn(this.jTableSolSug.getModel().getColumnCount()-1));
            }
        }
        catch(Exception exc){
            throw new Exception("Error creando el formulario");
        }
        
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
    private javax.swing.JButton jButtonAgregarZona;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonCargarFoto;
    private javax.swing.JButton jButtonEliminarZona;
    private javax.swing.JButton jButtonSolSugDetalles;
    private javax.swing.JCheckBox jCheckBoxBanco;
    private javax.swing.JCheckBox jCheckBoxFinanciacion;
    private javax.swing.JCheckBox jCheckBoxOperacionAlquiler;
    private javax.swing.JCheckBox jCheckBoxOperacionVenta;
    private javax.swing.JCheckBox jCheckBoxPH;
    private javax.swing.JCheckBox jCheckBoxPU;
    private javax.swing.JComboBox jComboBoxZona;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMarcarSolicitud;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelSolSug;
    private javax.swing.JRadioButton jRadioButtonMarcarSolicitudNo;
    private javax.swing.JRadioButton jRadioButtonMarcarSolicitudSi;
    private javax.swing.JRadioButton jRadioButtonTipoCasa;
    private javax.swing.JRadioButton jRadioButtonTipoTerreno;
    private javax.swing.JRadioButton jRadioButtonZonaGianNorte;
    private javax.swing.JRadioButton jRadioButtonZonaGianSur;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTableSolSug;
    private javax.swing.JTextArea jTextAreaContactoInfoAdic;
    private javax.swing.JTextArea jTextAreaInfoAdic;
    private javax.swing.JTextField jTextFieldBanos;
    private javax.swing.JTextField jTextFieldContactoApell;
    private javax.swing.JTextField jTextFieldContactoCel;
    private javax.swing.JTextField jTextFieldContactoDir;
    private javax.swing.JTextField jTextFieldContactoEmail;
    private javax.swing.JTextField jTextFieldContactoNombre;
    private javax.swing.JTextField jTextFieldContactoTel;
    private javax.swing.JTextField jTextFieldDireccion;
    private javax.swing.JTextField jTextFieldDormitorios;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldPrecioAlquiler;
    private javax.swing.JTextField jTextFieldPrecioVenta;
    private javax.swing.JTextField jTextFieldSupTerr;
    // End of variables declaration//GEN-END:variables

    // modo = "AltaPropiedad" | "ConsultarPropiedad" | "ModificarPropiedad" | "ConsultarPropiedadesDeSolicitud"
    private String modo;
    private String path="";
    private int idSolicitudSeleccionada = -1;
    private String idPropiedadInicial = "";
    
    private int idClienteSolicitud;
}
