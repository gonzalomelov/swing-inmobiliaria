/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author taio
 */
@Entity
public class Zona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String Zona;
    private boolean NorteG;
    private boolean SurG;

    public boolean isNorteG() {
        return NorteG;
    }

    public void setNorteG(boolean NorteG) {
        this.NorteG = NorteG;
    }

    public boolean isSurG() {
        return SurG;
    }

    public void setSurG(boolean SurG) {
        this.SurG = SurG;
    }

    public String getZona() {
        return Zona;
    }

    public void setZona(String Zona) {
        this.Zona = Zona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zona)) {
            return false;
        }
        Zona other = (Zona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Objetos.Zona[ id=" + id + " ]";
    }

    
}
