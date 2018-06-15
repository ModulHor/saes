
package paq_empleados;

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

public class TipoDescuento extends Pantalla{
    private Tabla tab_tipo_descuento =  new Tabla();
     
     public TipoDescuento (){
         tab_tipo_descuento.setId("tab_tipo_descuento");   //identificador
         tab_tipo_descuento.setTabla("saes_tipo_descuento", "ide_satid", 1);
         tab_tipo_descuento.getColumna("ide_satid").setNombreVisual("CODIGO");
         tab_tipo_descuento.getColumna("nombre_satid").setNombreVisual("DESCRIPCION DESCUENTO");
         tab_tipo_descuento.getColumna("activo_satid").setNombreVisual("ACTIVO");
         tab_tipo_descuento.dibujar();
         
          PanelTabla pat_tipo_descuento = new PanelTabla();
          pat_tipo_descuento.setId("pat_tipo_descuento");
          pat_tipo_descuento.setPanelTabla(tab_tipo_descuento);
          Division div_tipo_descuento = new Division();
          div_tipo_descuento.setId("div_tipo_descuento");
          div_tipo_descuento.dividir1(pat_tipo_descuento);
          agregarComponente(div_tipo_descuento);
     }
     @Override
    public void insertar() {
        tab_tipo_descuento.insertar();
    }

    @Override
    public void guardar() {
        tab_tipo_descuento.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_tipo_descuento.eliminar();
    }

    public Tabla getTab_tipo_descuento() {
        return tab_tipo_descuento;
    }

    public void setTab_tipo_descuento(Tabla tab_tipo_descuento) {
        this.tab_tipo_descuento = tab_tipo_descuento;
    }
    
}
