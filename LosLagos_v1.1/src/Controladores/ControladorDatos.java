/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;

import Interface.IControladorDatos;
import Objetos.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;



/**
 *
 * @author Santiago
 */
public class ControladorDatos implements IControladorDatos{
    private int tamañoLetraTabla=9;

    public ControladorDatos(){
    }

    public void exportarDatos(String path, boolean c,boolean p,boolean s) throws Exception {
        //esta funcion crea el archivo pdf

        boolean abrir=false;

        if (path.equals("")){
            path="temp.pdf";
            abrir=true;
        }


        if (!path.contains(".pdf"))
          path=path+".pdf";
        
        // Se crea el documento
        Document documento = new Document();

        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream(path);

        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);

        // Se abre el documento.
        
        documento.open();
        
        documento.addAuthor("u-bite.com"); 
        documento.addSubject("informe de datos, inmobiliaria los lagos");
        
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("ES"));
        Date fechaDate = new Date();
        String fecha = formateador.format(fechaDate);
        documento.add(new Paragraph("Informe generado automaticamente - "+fecha));
                

        documento.add(new Paragraph("División inmobiliaria",
				FontFactory.getFont("arial",   // fuente
				26,                            // tamaño
				Font.NORMAL,                   // estilo
				BaseColor.DARK_GRAY)));             // color
        
        //printLinea(documento);
 

        try
        {
            Image foto = Image.getInstance("images/logo.jpg");
            //foto.scaleToFit(100, 100);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);
        }
        catch ( Exception e )
        {
        }
        
        //ahora ingresamos los datos de las tablas de la base de datos
        
        if (c){
            //listamos los clientes
            ManejadorDatos.getInstance().getEm().getTransaction().begin();
            String sql = "select c from Cliente c;" ;
            java.util.List<Cliente> list = (java.util.List<Cliente>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
       
            documento.add(new Paragraph("Clientes",
				FontFactory.getFont("arial",   // fuente
				16,                            // tamaño
				Font.ITALIC,                   // estilo
				BaseColor.DARK_GRAY)));             // color
            
            documento.add(new Paragraph(" "));
            documento.add(new LineSeparator());
            documento.add(new Paragraph(" "));
            
            PdfPTable tabla = new PdfPTable(7);
            
            if (list!=null & !list.isEmpty()){
                tabla.addCell("ID");
                tabla.addCell("Nombre");
                tabla.addCell("Apellido");
                tabla.addCell("Direccion");
                tabla.addCell("Email");
                tabla.addCell("Telefono");
                tabla.addCell("Celular");
                
                for (Cliente cli:list){
                    tabla.addCell(Integer.toString(cli.getId()));
                    tabla.addCell(cli.getNombre());
                    tabla.addCell(cli.getApellido());
                    tabla.addCell(cli.getDireccion());
                    tabla.addCell(cli.getEmail());
                    tabla.addCell(cli.getTelefono());
                    tabla.addCell(cli.getCelular());
                }
                documento.add(tabla);
            }
        
        }
        
        if (p){
            //listamos los clientes
            //documento.add(new DottedLineSeparator());
            
            
            ManejadorDatos.getInstance().getEm().getTransaction().begin();   
            String sql = "select c from Casa c;" ;
            java.util.List<Casa> list2 = (java.util.List<Casa>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
            documento.add(new Paragraph("Propiedades",
				FontFactory.getFont("arial",   // fuente
				16,                            // tamaño
				Font.ITALIC,                   // estilo
				BaseColor.DARK_GRAY)));             // color
            
            documento.add(new Paragraph(" "));
            documento.add(new LineSeparator());
            documento.add(new Paragraph(" "));
            
            if (list2!=null & !list2.isEmpty()){
                               
                for (Casa cli:list2){
                    documento.add(new Paragraph("Tipo: "+cli.getTipo()));
                    documento.add(new Paragraph("ID: "+cli.getId()));
                    
                    java.util.List<Path> l = cli.getImages();
                    if (l!=null){
                        for (Path pa:l){
                            try
                            {
                                Image foto = Image.getInstance(pa.getPath());
                                //foto.scaleToFit(100, 100);
                                foto.setAlignment(Chunk.ALIGN_MIDDLE);
                                foto.scaleToFit(350, 250);
                                documento.add(foto);
                            }
                            catch ( Exception e )
                            {
                            }
                        }
                    }
                    
                    //para esta casa, creamos una tabla con la informacion basica
                    
                    PdfPTable tabla = new PdfPTable(8);
                    tabla.addCell("Estado");
                    tabla.addCell("Zona");
                    tabla.addCell("Ubi. G");
                    tabla.addCell("Dirección");
                    tabla.addCell("PU");
                    tabla.addCell("PH");
                    tabla.addCell("Precio Ven");
                    tabla.addCell("Precio Alq");

                    tabla.addCell(cli.getEstado());
                    tabla.addCell(cli.getZona());
                    if (cli.isNorte())
                        tabla.addCell("Norte");
                    else
                        tabla.addCell("Sur");
                    tabla.addCell(cli.getDireccion());
                    if (cli.isPH())
                        tabla.addCell("SI");
                    else
                        tabla.addCell("NO");
                    if (cli.isPU())
                        tabla.addCell("SI");
                    else
                        tabla.addCell("NO");
                    
                    tabla.addCell(Integer.toString(cli.getPrecioV()));
                    tabla.addCell(Integer.toString(cli.getPrecioA()));
                    
                    //ahora creamos otra tabla para la descripcion de la casa:
                    
                    PdfPTable tabla2 = new PdfPTable(4);
                    PdfPTable tabla3 = new PdfPTable(7);
                    
                    tabla2.addCell("Superficie");
                    tabla2.addCell("Dormitorios");
                    tabla2.addCell("Baños");
                    tabla2.addCell("Especificacion");
                    
                    tabla2.addCell(Integer.toString(cli.getSuperficie()));
                    tabla2.addCell(Integer.toString(cli.getDormitorios()));
                    tabla2.addCell(Integer.toString(cli.getBanos()));
                    tabla2.addCell(cli.getEspecificacion());
                    
                    
                    tabla3.addCell("Precedencia");
                    tabla3.addCell("Nombre");
                    tabla3.addCell("Apellido");
                    tabla3.addCell("Direccion");
                    tabla3.addCell("Email");
                    tabla3.addCell("Telefono");
                    tabla3.addCell("Celular");
                    
                    tabla3.addCell(cli.getPrecedencia());
                    tabla3.addCell(cli.getNombre());
                    tabla3.addCell(cli.getApellido());
                    tabla3.addCell(cli.getDireccionDueno());
                    tabla3.addCell(cli.getEmail());
                    tabla3.addCell(cli.getTelefono());
                    tabla3.addCell(cli.getCelular());
                    
                    documento.add(new Paragraph("Información basica:"));
                    documento.add(new Paragraph(" "));
                    
                    documento.add(tabla);
                    
                    documento.add(new Paragraph("Descripción:"));
                    documento.add(new Paragraph(" "));
                    documento.add(tabla2);
                    
                    documento.add(new Paragraph("Información de contacto:"));
                    documento.add(new Paragraph(" "));
                    documento.add(tabla3);
                    
                    documento.add(new Paragraph(" "));
                    documento.add(new LineSeparator());
                    documento.add(new Paragraph(" "));
            
                }
                
            }
        
        }
        
        if (s){
            //listamos los clientes
            //documento.add(new DottedLineSeparator());
            
            
            ManejadorDatos.getInstance().getEm().getTransaction().begin();   
            String sql = "select p from Pedido p;" ;
            java.util.List<Pedido> list2 = (java.util.List<Pedido>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
            documento.add(new Paragraph("Solicitudes",
				FontFactory.getFont("arial",   // fuente
				16,                            // tamaño
				Font.ITALIC,                   // estilo
				BaseColor.DARK_GRAY)));             // color
            
            documento.add(new Paragraph(" "));
            documento.add(new LineSeparator());
            documento.add(new Paragraph(" "));
            
            if (list2!=null & !list2.isEmpty()){
                               
                for (Pedido cli:list2){
                    documento.add(new Paragraph("Tipo: "+cli.getTipo()));
                    documento.add(new Paragraph("ID: "+cli.getId()));
                    
                    //para esta solicitud, creamos una tabla con la informacion basica
                    
                    PdfPTable tabla = new PdfPTable(5);
                    tabla.addCell("Estado");
                    tabla.addCell("PU");
                    tabla.addCell("PH");
                    tabla.addCell("Precio Ven");
                    tabla.addCell("Precio Alq");

                    tabla.addCell(cli.getEstado());
                    tabla.addCell(cli.getPU());
                    tabla.addCell(cli.getPH());
                    
                    tabla.addCell(Integer.toString(cli.getPrecioVL())+" a "+Integer.toString(cli.getPrecioVH()));
                    tabla.addCell(Integer.toString(cli.getPrecioAL())+" a "+Integer.toString(cli.getPrecioAH()));
                    
                    //ahora creamos otra tabla para la descripcion de la casa:
                    
                    PdfPTable tabla4 = new PdfPTable(3);
                    tabla4.addCell("Zona");
                    tabla4.addCell("Norte de G.");
                    tabla4.addCell("Sur de G.");
                    
                    
                    for (Zona z: cli.getZonas()){
                        tabla4.addCell(z.getZona());
                        if (z.isNorteG())
                            tabla4.addCell("Si");
                        else
                            tabla4.addCell("No");
                        
                        if (z.isSurG())
                            tabla4.addCell("Si");
                        else
                            tabla4.addCell("No");
                    }
                    
                    PdfPTable tabla2 = new PdfPTable(3);
                    
                    tabla2.addCell("Superficie");
                    tabla2.addCell("Dormitorios");
                    tabla2.addCell("Baños");

                    
                    tabla2.addCell(Integer.toString(cli.getSuperficieL()) + " a " + cli.getSuperficieH());
                    tabla2.addCell(Integer.toString(cli.getDormitoriosL()) + " a " + cli.getDormitoriosH());
                    tabla2.addCell(Integer.toString(cli.getBanosL())+" a " + cli.getBanosH());

                    documento.add(new Paragraph("Información basica:"));
                    documento.add(new Paragraph(" "));
                    
                    documento.add(tabla);
                    
                    documento.add(new Paragraph("Zonas:"));
                    documento.add(new Paragraph(" "));
                    documento.add(tabla4);
                    
                    documento.add(new Paragraph("Descripción:"));
                    documento.add(new Paragraph(" "));
                    documento.add(tabla2);
                    

                    documento.add(new Paragraph(" "));
                    documento.add(new LineSeparator());
                    documento.add(new Paragraph(" "));
            
                }
                
            }
        
        }
        
        try
        {
            Image foto = Image.getInstance("images/firma.jpg");
            foto.scaleToFit(100, 100);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);
        }
        catch ( Exception e )
        {
        }
        
        documento.close();

        if (abrir){
            //abrimos el documento para que lo imprima
            try {
                File file = new File("temp.pdf");
                Desktop.getDesktop().open(file);
            } catch(Exception e) {
            }

        }

    }

    @Override
    public void marcarCasa(int idCliente, String idCasa) throws Exception {

        String str = Integer.toString(idCliente);
        str=str+idCasa;

        Historia h = new Historia();
        h.setId(str);
        ManejadorDatos.getInstance().persist(h);
    }

    @Override
    public void desMarcarCasa(int idCliente, String idCasa) throws Exception {
        ManejadorDatos.getInstance().getEm().getTransaction().begin();

        String str = Integer.toString(idCliente);
        str=str+idCasa;

        Historia h  = ManejadorDatos.getInstance().getEm().find(Historia.class,str);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();

        if (h==null){
            throw new Exception("No se puede desmarcar, el cliente no estaba marcado con la casa");
        }else{
            ManejadorDatos.getInstance().getEm().getTransaction().begin();
            ManejadorDatos.getInstance().getEm().remove(h);
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
        }

    }

    @Override
    public boolean isCasaMarcada(int idCliente, String idCasa) throws Exception {
        String str = Integer.toString(idCliente);
        str=str+idCasa;

        ManejadorDatos.getInstance().getEm().getTransaction().begin();
        Historia h  = ManejadorDatos.getInstance().getEm().find(Historia.class,str);
        ManejadorDatos.getInstance().getEm().getTransaction().commit();

        if (h==null)
            return false;
        else
            return true;
    }

    @Override
    public void imprimirPropiedades(String path, int tamañoLetra) throws Exception {
        //id-Estado-Tipo-Ubicacion-Dir-PrecioV-A - Sup-Dorm-Baños

        tamañoLetraTabla = tamañoLetra;
        boolean abrir=false;

        if (path.equals("")){
            path="temp.pdf";
            abrir=true;
        }

        if (!path.contains(".pdf"))
          path=path+".pdf";
        
        // Se crea el documento
        Document documento = new Document();

        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream(path);

        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
        PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);

        // Se abre el documento.
        
        documento.open();
        
        imprimirCabeza(documento,"Propiedades");
        
     
            ManejadorDatos.getInstance().getEm().getTransaction().begin();   
            String sql = "select c from Casa c;" ;
            java.util.List<Casa> list2 = (java.util.List<Casa>) ManejadorDatos.getInstance().getEm().createQuery(sql).getResultList();
            ManejadorDatos.getInstance().getEm().getTransaction().commit();
        
            documento.add(new Paragraph(" "));
            documento.add(new LineSeparator());
            documento.add(new Paragraph(" "));
            
            if (list2!=null & !list2.isEmpty()){
                
                float[] medidaCeldas = {tam("id"), tam("Estado"), tam("Zona"), tam("Dir"),
                    tam("PrecioV"), tam("PrecioA"), tam("Sup"), tam("Dorm"), tam("Baños")
                };

                PdfPTable tabla = new PdfPTable(9);
                tabla.addCell(c("Id"));
                tabla.addCell(c("Estado"));
                tabla.addCell(c("Zona"));
                tabla.addCell(c("Dir"));
                tabla.addCell(c("PrecioV"));
                tabla.addCell(c("PrecioA"));
                tabla.addCell(c("Sup"));
                tabla.addCell(c("Dorm"));
                tabla.addCell(c("Baños"));


                for (Casa cli:list2){
                    if (tam(cli.getId())>medidaCeldas[0])
                        medidaCeldas[0]=tam(cli.getId());
                    tabla.addCell(c(cli.getId()));

                    if (tam(cli.getEstado())>medidaCeldas[1])
                        medidaCeldas[1]=tam(cli.getEstado());
                    tabla.addCell(c(cli.getEstado()));

                    if (tam(cli.getZona())>medidaCeldas[2])
                        medidaCeldas[2]=tam(cli.getZona());
                    tabla.addCell(c(cli.getZona()));

                    if (tam(cli.getDireccion())>medidaCeldas[3])
                        medidaCeldas[3]=tam(cli.getDireccion());
                    tabla.addCell(c(cli.getDireccion()));
                    
                    if (tam(Integer.toString(cli.getPrecioV()))>medidaCeldas[4])
                        medidaCeldas[4]=tam((Integer.toString(cli.getPrecioV())));
                    tabla.addCell(c(Integer.toString(cli.getPrecioV())));

                    if (tam(Integer.toString(cli.getPrecioA()))>medidaCeldas[5])
                        medidaCeldas[5]=tam((Integer.toString(cli.getPrecioA())));
                    tabla.addCell(c(Integer.toString(cli.getPrecioA())));

                    if (tam(Integer.toString(cli.getSuperficie()))>medidaCeldas[6])
                        medidaCeldas[6]=tam((Integer.toString(cli.getSuperficie())));
                    tabla.addCell(c(Integer.toString(cli.getSuperficie())));

                    if (tam(Integer.toString(cli.getDormitorios()))>medidaCeldas[7])
                        medidaCeldas[7]=tam((Integer.toString(cli.getDormitorios())));
                    tabla.addCell(c(Integer.toString(cli.getDormitorios())));

                    if (tam(Integer.toString(cli.getBanos()))>medidaCeldas[8])
                        medidaCeldas[8]=tam((Integer.toString(cli.getBanos())));

                    tabla.addCell(c(Integer.toString(cli.getBanos())));
                    //ahora creamos otra tabla para la descripcion de la casa:       
                }

                //tabla.setTotalWidth(medidaCeldas);
                tabla.setWidthPercentage(100);

                tabla.setWidths(medidaCeldas);
                documento.add(tabla);
        }

        try
        {
            Image foto = Image.getInstance("images/firma.jpg");
            foto.scaleToFit(100, 100);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            documento.add(foto);
        }
        catch ( Exception e )
        {
        }

        documento.close();


        if (abrir){
            //abrimos el documento para que lo imprima
            try {
                File file = new File("temp.pdf");
                Desktop.getDesktop().open(file);
            } catch(Exception e) {
            }
        
        }
    }

    public PdfPCell c(String texto){
        PdfPCell celda =new PdfPCell (new Paragraph(texto,
                 FontFactory.getFont("arial",   // fuente
                 this.tamañoLetraTabla,                            // tamaño
                Font.NORMAL,                   // estilo
                BaseColor.BLACK)));             // color

                return celda;
    }

    public float tam(String a){
        String texto = a;
        // Declaramos el tipo de fuente
        BaseFont FUENTE_BASE;
        try {
                FUENTE_BASE = BaseFont.createFont(BaseFont.HELVETICA,
                                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                // Aquí declaramos el tipo de fuente que tendra nuestra cabecera de
                // la tabla, tipo helvetica, negrita.
                Font FUENTE_CABECERA_TABLA = new Font(FUENTE_BASE, this.tamañoLetraTabla, Font.BOLD);
                // Nuestro texto, con su fuente.
                Chunk wTexto = new Chunk(texto.toString(), FUENTE_CABECERA_TABLA);
                return new Float(String.valueOf(wTexto.getWidthPoint()));
                
        } catch (DocumentException e) {
                // TODO Auto-generated catch block
        } catch (IOException e) {
                // TODO Auto-generated catch block
        }
        return (float) 1.0;
    }

    @Override
    public void imprimirJTable(JTable t, String titulo) {
        FileOutputStream ficheroPdf = null;

        String path = "temp.pdf";

        try {
            Random rnd = new Random();
            Document documento = new Document();

            boolean crear = false;

            while (!crear){
                try{
                    ficheroPdf = new FileOutputStream(path);
                    crear=true;
                    System.out.println("Se ha creado el archivo"+path);
                }catch(Exception e){
                    path=path+rnd.nextInt();
                }
            }
            PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);
            // Se abre el documento.
            documento.open();


            imprimirCabeza(documento, titulo);


            TableModel modelo = t.getModel();
            int c = modelo.getColumnCount();
            PdfPTable tabla = new PdfPTable(c);

            float[] medidaCeldas = new float[c];

            this.tamañoLetraTabla=9;

            for (int i = 0; i < c; i++) {
                tabla.addCell(c(modelo.getColumnName(i)));
                medidaCeldas[i]=tam(modelo.getColumnName(i));
            }

            for (int i = 0; i < modelo.getRowCount(); i++) {
                for (int j = 0; j < c; j++) {
                    if (tam(modelo.getValueAt(i, j).toString())>medidaCeldas[j])
                        medidaCeldas[j]=tam(modelo.getValueAt(i, j).toString());
                    
                    String str = modelo.getValueAt(i, j).toString();
                    if (str.equals("true"))
                        str = "Si";
                    if (str.equals("false"))
                        str = "No";
                    
                    tabla.addCell(c(str));
            }
        
            }
            tabla.setWidths(medidaCeldas);
            tabla.setWidthPercentage(100);

            documento.add(tabla);
            try
            {
                Image foto = Image.getInstance("images/firma.jpg");
                foto.scaleToFit(70, 70);
                foto.setAlignment(Chunk.ALIGN_MIDDLE);
                documento.add(foto);
            }
            catch ( Exception e )
            {
            }

            documento.close();

            try {
                File file = new File(path);
                Desktop.getDesktop().open(file);
            } catch(Exception e) {
            }

        } catch (Exception ex) {
            Logger.getLogger(ControladorDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void imprimirCabeza(Document documento, String titulo) {
        try
        {
            documento.addAuthor("u-bite.com");
            documento.addSubject("informe de datos, inmobiliaria los lagos");
            SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("ES"));
            Date fechaDate = new Date();
            String fecha = formateador.format(fechaDate);
            documento.add(new Paragraph("Informe generado automaticamente - " + fecha));
            documento.add(new Paragraph(titulo, FontFactory.getFont("arial", 26, Font.NORMAL, BaseColor.DARK_GRAY))); // color
            try {
                Image foto = Image.getInstance("images/logo.jpg");
                //foto.scaleToFit(100, 100);
                foto.setAlignment(Chunk.ALIGN_MIDDLE);
                documento.add(foto);
            } catch (Exception e) {
            }
        }
        catch ( DocumentException ex )
        {
            Logger.getLogger(ControladorDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


