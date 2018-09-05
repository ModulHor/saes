
package paq_empleados;

/**
 *
 * @author Andres
 */
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import paq_citas.ejb.ServiciosCitas;
import paq_empleados.ejb.ServiciosEmpleados;
import sistema.aplicacion.Pantalla;

public class DescuentoEmpleado extends Pantalla {
    private Tabla tab_descuento_empleado =  new Tabla();
    private Combo com_periodo = new Combo();
    
    @EJB
    private final ServiciosCitas ser_citas = (ServiciosCitas) utilitario.instanciarEJB(ServiciosCitas.class);
    
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
      public DescuentoEmpleado (){
          
          Etiqueta eti_periodo = new Etiqueta("PERIODO: ");
          bar_botones.agregarComponente(eti_periodo);
          
          com_periodo.setId("com_periodo");
          com_periodo.setCombo(ser_citas.getPeriodo("true"));
          com_periodo.setMetodo("filtroComboPeriodo");
          bar_botones.agregarComponente(com_periodo);
          
          tab_descuento_empleado.setId("tab_descuento_empleado");   //identificador
          tab_descuento_empleado.setTabla("saes_descuento", "ide_sades", 1);
          tab_descuento_empleado.setCondicion("ide_saperi=-1");
          tab_descuento_empleado.getColumna("ide_satid").setCombo(ser_empleados.getTipoDescuento("true"));
          tab_descuento_empleado.getColumna("ide_saemp").setCombo(ser_empleados.getEmpleado("true"));
          tab_descuento_empleado.getColumna("ide_saemp").setAutoCompletar();
          tab_descuento_empleado.getColumna("ide_saperi").setVisible(false);
          tab_descuento_empleado.getColumna("ide_sades").setNombreVisual("CODIGO");
          tab_descuento_empleado.getColumna("ide_saemp").setNombreVisual("EMPLEADO");
          tab_descuento_empleado.getColumna("ide_satid").setNombreVisual("TIPO DESCUENTO");
          tab_descuento_empleado.getColumna("observacion_sades").setNombreVisual("OBSERVACION");
          tab_descuento_empleado.getColumna("fecha_sades").setNombreVisual("FECHA");
          tab_descuento_empleado.getColumna("valor_sades").setNombreVisual("VALOR");
          tab_descuento_empleado.getColumna("valor_sades").setRequerida(true);
          tab_descuento_empleado.getColumna("valor_sades").setEstilo("font-size: 14px;font-weight: bold;text-align: right;");
          tab_descuento_empleado.getColumna("estado_sades").setNombreVisual("CANCELADO");
          tab_descuento_empleado.getColumna("ide_sades").setOrden(0);
          tab_descuento_empleado.getColumna("fecha_sades").setOrden(1);
          tab_descuento_empleado.getColumna("ide_satid").setOrden(2);
          tab_descuento_empleado.getColumna("ide_saemp").setOrden(3);
          tab_descuento_empleado.getColumna("observacion_sades").setOrden(4);
          tab_descuento_empleado.getColumna("valor_sades").setOrden(5);
          tab_descuento_empleado.getColumna("estado_sades").setOrden(6);
          tab_descuento_empleado.dibujar();
          
          PanelTabla pat_descuento_empleado = new PanelTabla();
          pat_descuento_empleado.setId("pat_descuento_empleado");
          pat_descuento_empleado.setPanelTabla(tab_descuento_empleado);
          Division div_descuento_empleado = new Division();
          div_descuento_empleado.setId("div_descuento_empleado");
          div_descuento_empleado.dividir1(pat_descuento_empleado);
          agregarComponente(div_descuento_empleado);
      }
      
    public void filtroComboPeriodo(){
        
        tab_descuento_empleado.setCondicion("ide_saperi="+com_periodo.getValue().toString());
        tab_descuento_empleado.ejecutarSql();
        utilitario.addUpdate("tab_descuento_empleado");
    }  
      
    @Override
    public void insertar() {
        if(com_periodo.getValue() == null){
            utilitario.agregarMensajeError("ERROR", "Seleccione el Periodo");
        return;
        }
        else if (tab_descuento_empleado.isFocus()){
        tab_descuento_empleado.insertar();
        
        tab_descuento_empleado.setValor("ide_saperi", com_periodo.getValue().toString());
        utilitario.addUpdateTabla(tab_descuento_empleado, "ide_saperi", "");
        }
    }
    
    @Override
    public void guardar() {
        String str_descuento = tab_descuento_empleado.getValorSeleccionado();
        tab_descuento_empleado.guardar();
        if (tab_descuento_empleado.getColumna("estado_sades") == null){
        utilitario.getConexion().ejecutarSql("update saes_descuento set estado_sades = false where ide_sades = "+str_descuento);
        utilitario.addUpdate("tab_descuento_empleado"); 
        }
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_descuento_empleado.eliminar();
    }

    public Tabla getTab_descuento_empleado() {
        return tab_descuento_empleado;
    }

    public void setTab_descuento_empleado(Tabla tab_descuento_empleado) {
        this.tab_descuento_empleado = tab_descuento_empleado;
    }

    public Combo getCom_periodo() {
        return com_periodo;
    }

    public void setCom_periodo(Combo com_periodo) {
        this.com_periodo = com_periodo;
    }
    
}
