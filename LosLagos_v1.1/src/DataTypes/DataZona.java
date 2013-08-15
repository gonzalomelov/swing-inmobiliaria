/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

/**
 *
 * @author taio
 */
public class DataZona {
    String Zona;
    boolean NorteG;
    boolean SurG;

    public boolean isSurG() {
        return SurG;
    }

    public void setSurG(boolean SurG) {
        this.SurG = SurG;
    }
    
    public boolean isNorteG() {
        return NorteG;
    }

    public void setNorteG(boolean NorteG) {
        this.NorteG = NorteG;
    }

    public String getZona() {
        return Zona;
    }

    public void setZona(String Zona) {
        this.Zona = Zona;
    }

    public DataZona() {
    }
    
    
}
