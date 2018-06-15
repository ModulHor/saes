
package paq_ventas;

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
import paq_empleados.ejb.ServiciosEmpleados;
import paq_servicio.ejb.ServiciosEstetica;
import sistema.aplicacion.Pantalla;

public class TipoDocumento extends Pantalla {
    private Tabla tab_tipo_documento =  new Tabla();
    
      public TipoDocumento () {
         tab_tipo_documento.setId("tab_tipo_documento");   //identificador
         tab_tipo_documento.setTabla("saes_tipo_documento", "ide_satido", 1);
         tab_tipo_documento.dibujar();
         
          PanelTabla pat_ventas = new PanelTabla();
          pat_ventas.setId("pat_ventas");
          pat_ventas.setPanelTabla(tab_tipo_documento);
          Division div_ventas = new Division();
          div_ventas.setId("div_ventas");
          div_ventas.dividir1(pat_ventas);
          agregarComponente(div_ventas);
      }
      @Override
    public void insertar() {
        tab_tipo_documento.insertar();
    }

    @Override
    public void guardar() {
        tab_tipo_documento.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_tipo_documento.eliminar();
    }

    public Tabla getTab_tipo_documento() {
        return tab_tipo_documento;
    }

    public void setTab_tipo_documento(Tabla tab_tipo_documento) {
        this.tab_tipo_documento = tab_tipo_documento;
    }
    
}
