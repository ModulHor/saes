
package paq_servicios;

/**
 *
 * @author Andres
 */
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sistema.aplicacion.Pantalla;

public class TipoServicio extends Pantalla{
     private Tabla tab_tipo_servicios =  new Tabla();
     
       public TipoServicio (){
          tab_tipo_servicios.setId("tab_tipo_servicios");   //identificador
          tab_tipo_servicios.setTabla("saes_tipo_servicio", "ide_satis", 1);
          tab_tipo_servicios.getColumna("ide_satis").setNombreVisual("CODIGO");
          tab_tipo_servicios.getColumna("nombre_satis").setNombreVisual("NOMBRE T. SERVICIO");
          tab_tipo_servicios.getColumna("descripcion_satis").setNombreVisual("DESCRIPCION");
          tab_tipo_servicios.getColumna("activo_satis").setNombreVisual("ACTIVO");
          tab_tipo_servicios.dibujar();
         
          PanelTabla pat_tipo_servicios = new PanelTabla();
          pat_tipo_servicios.setId("pat_tipo_servicios");
          pat_tipo_servicios.setPanelTabla(tab_tipo_servicios);
          Division div_tipo_servicios = new Division();
          div_tipo_servicios.setId("div_tipo_servicios");
          div_tipo_servicios.dividir1(pat_tipo_servicios);
          agregarComponente(div_tipo_servicios);
       }
       @Override
    public void insertar() {
        tab_tipo_servicios.insertar();
    }

    @Override
    public void guardar() {
        tab_tipo_servicios.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_tipo_servicios.eliminar();
    }

    public Tabla getTab_tipo_servicios() {
        return tab_tipo_servicios;
    }

    public void setTab_tipo_servicios(Tabla tab_tipo_servicios) {
        this.tab_tipo_servicios = tab_tipo_servicios;
    }
    
}
