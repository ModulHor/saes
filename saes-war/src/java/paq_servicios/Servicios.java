package paq_servicios;

/**
 *
 * @author Andres Redroban
 */
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import paq_servicio.ejb.ServiciosEstetica;
import sistema.aplicacion.Pantalla;

public class Servicios extends Pantalla {
    private Tabla tab_servicios =  new Tabla();
    private Tabla tab_precio_servicio = new Tabla();
    String v_cuarenta = "40";
    String v_cincuenta = "50";
    
    @EJB
    private final ServiciosEstetica ser_servicios = (ServiciosEstetica) utilitario.instanciarEJB(ServiciosEstetica.class);
    
      public Servicios (){
          tab_servicios.setId("tab_servicios");   //identificador
          tab_servicios.setTabla("saes_servicio", "ide_saser", 1);
          tab_servicios.getColumna("ide_satis").setCombo(ser_servicios.getTipoServicio("true"));
          tab_servicios.getColumna("ide_saser").setNombreVisual("CODIGO");
          tab_servicios.getColumna("ide_satis").setNombreVisual("TIPO SERVICIO");
          tab_servicios.getColumna("nombre_saser").setNombreVisual("NOMBRE SERVICIO");
          tab_servicios.getColumna("descripcion_saser").setNombreVisual("OBSERVACIONES");
          tab_servicios.getColumna("fecha_saser").setNombreVisual("FECHA REGISTRO");
          tab_servicios.getColumna("activo_saser").setNombreVisual("ACTIVO");
          tab_servicios.getColumna("precio_inicial_saser").setNombreVisual("PRECIO INICIAL");
          tab_servicios.getColumna("precio_inicial_saser").setEstilo("font-size: 14px;font-weight: bold;text-align: right;");
          tab_servicios.getColumna("porcentaje_saser").setNombreVisual("PORCENTAJE EMPLEADO");
          tab_servicios.getColumna("porcentaje_saser").setEstilo("font-size: 14px;font-weight: bold;text-align: right;");
          tab_servicios.getColumna("ide_saser").setOrden(0);
          tab_servicios.getColumna("ide_satis").setOrden(1);
          tab_servicios.getColumna("nombre_saser").setOrden(2);
          tab_servicios.getColumna("descripcion_saser").setOrden(3);
          tab_servicios.getColumna("fecha_saser").setOrden(4);
          tab_servicios.getColumna("precio_inicial_saser").setOrden(5);
          tab_servicios.getColumna("porcentaje_saser").setOrden(6);
          tab_servicios.getColumna("activo_saser").setOrden(7);
          //tab_servicios.agregarRelacion(tab_precio_servicio);
          tab_servicios.dibujar();
         
          PanelTabla pat_servicios = new PanelTabla();
          pat_servicios.setId("pat_servicios");
          pat_servicios.setPanelTabla(tab_servicios);
          
        /*  tab_precio_servicio.setId("tab_precio_servicio");   //identificador
          tab_precio_servicio.setTabla("saes_precio", "ide_sapre", 2);
          tab_precio_servicio.dibujar();
          
          PanelTabla pat_precio_servicio = new PanelTabla();
          pat_precio_servicio.setId("pat_precio_servicio");
          pat_precio_servicio.setPanelTabla(tab_precio_servicio);*/
          
          Division div_servicios = new Division();
          div_servicios.setId("div_tipo_descuento");
          div_servicios.dividir1(pat_servicios);
          agregarComponente(div_servicios);
      }
      @Override
    public void insertar() {
     /*   if(tab_servicios.isFocus()){
        tab_servicios.insertar();
        }
        else if (tab_precio_servicio.isFocus()){
        tab_precio_servicio.insertar();
        }*/
     tab_servicios.insertar();
    }

    @Override
    public void guardar() {
       /* if(tab_servicios.isFocus()){
        tab_servicios.guardar();
        }
        else if (tab_precio_servicio.isFocus()){
        tab_precio_servicio.guardar();
        }*/
       if (tab_servicios.getValor("porcentaje_saser").equals(v_cincuenta) || tab_servicios.getValor("porcentaje_saser").equals(v_cuarenta)){
           tab_servicios.guardar();
           guardarPantalla();;
       }else {
           utilitario.agregarMensajeError("No se puede guardar", "El porcentaje debe ser de 40 o 50");
       }
        
    }

    @Override
    public void eliminar() {
      /*  if(tab_servicios.isFocus()){
       tab_servicios.eliminar();
        }
        else if (tab_precio_servicio.isFocus()){
            tab_precio_servicio.eliminar();
        }*/
      tab_servicios.eliminar();
    }

    public Tabla getTab_servicios() {
        return tab_servicios;
    }

    public void setTab_servicios(Tabla tab_servicios) {
        this.tab_servicios = tab_servicios;
    }

    public Tabla getTab_precio_servicio() {
        return tab_precio_servicio;
    }

    public void setTab_precio_servicio(Tabla tab_precio_servicio) {
        this.tab_precio_servicio = tab_precio_servicio;
    }
       
}
