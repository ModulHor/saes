
package paq_cliente;

/**
 *
 * @author Andres
 */
import framework.componentes.AutoCompletar;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.MenuPanel;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.Tabla;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import paq_clientes.ejb.ServiciosClientes;
import paq_empleados.ejb.ServiciosEmpleados;
import sistema.aplicacion.Pantalla;

public class Cliente extends Pantalla{
   private Tabla tab_clientes =  new Tabla(); 
   private Reporte rep_reporte = new Reporte();
   private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte();
   private Map map_parametros = new HashMap();
   private AutoCompletar aut_cliente = new AutoCompletar();
   private final MenuPanel mep_menu = new MenuPanel();
   private Tabla tab_tabla = new Tabla();
   
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
    @EJB
    private final ServiciosClientes ser_clientes = (ServiciosClientes) utilitario.instanciarEJB(ServiciosClientes.class);
    public Cliente (){
        
        aut_cliente.setId("aut_cliente");
        aut_cliente.setAutoCompletar(ser_clientes.getCliente());
        aut_cliente.setSize(75);
        aut_cliente.setMetodoChange("seleccionoAutocompletar");
        bar_botones.agregarComponente(new Etiqueta("Cliente :"));
        bar_botones.agregarComponente(aut_cliente);
        
        mep_menu.setMenuPanel("OPCIONES CLIENTE", "22%");
        mep_menu.agregarItem("Listado de Clientes", "dibujarDashBoard", "ui-icon-home");//15
        mep_menu.agregarItem("Datos Clientes", "dibujarCliente", "ui-icon-person");
      
        rep_reporte.setId("rep_reporte");
        agregarComponente(rep_reporte);
        bar_botones.agregarReporte();
        
        sel_rep.setId("sel_rep");
        agregarComponente(sel_rep);
        
        agregarComponente(mep_menu);      
        dibujarDashBoard();
    }
    public void seleccionoAutocompletar(){
        
        /*tab_clientes.setCondicion("ide_sacli="+aut_cliente.getValor());
        tab_clientes.ejecutarSql();
        utilitario.addUpdate("tab_clientes");*/
        if (aut_cliente != null) {
             switch (mep_menu.getOpcion()) {
                case 1:
                    dibujarCliente();
                    break;
                    default:
                    dibujarCliente();
        }
        }else {
            limpiar();
        
     
    }
}
    public void limpiar() {
        aut_cliente.limpiar();
        mep_menu.limpiar();
        dibujarDashBoard();
    }
    public void dibujarDashBoard() {

        tab_tabla = new Tabla();
        tab_tabla.setId("tab_tabla");
        tab_tabla.setNumeroTabla(1);
        tab_tabla.setSql(ser_clientes.getSqlCliente());
        tab_tabla.setLectura(true);
        tab_tabla.setCampoPrimaria("ide_sacli");
        tab_tabla.setRows(20);
        tab_tabla.getColumna("ide_sacli").setVisible(false);
        tab_tabla.getColumna("ci_dni_sacli").setNombreVisual("IDENTIDICACIÓN");
        tab_tabla.getColumna("ci_dni_sacli").setFiltroContenido();
        tab_tabla.getColumna("apellidos_sacli").setNombreVisual("APELLIDOS");
        tab_tabla.getColumna("apellidos_sacli").setFiltro(true);
        tab_tabla.getColumna("nombres_sacli").setNombreVisual("NOMBRES");
        tab_tabla.getColumna("nombres_sacli").setFiltro(true);
        tab_tabla.getColumna("telefono_sacli").setNombreVisual("TELÈFONO");
        tab_tabla.getColumna("celular_sacli").setNombreVisual("CELULAR");
        tab_tabla.getColumna("correo_sacli").setNombreVisual("CORREO");
        tab_tabla.getColumna("direccion_sacli").setNombreVisual("DIRECCIÒN");

        tab_tabla.dibujar();

        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        mep_menu.dibujar(1, "LISTADO DE CLIENTES", pat_panel);
    }
    public void dibujarCliente(){
        tab_clientes.setId("tab_clientes");   //identificador
      tab_clientes.setTabla("saes_cliente", "ide_sacli", 2);
      tab_clientes.setCondicion("ide_sacli=" + aut_cliente.getValor());
      tab_clientes.setTipoFormulario(true);
      tab_clientes.getGrid().setColumns(2);
      
      tab_clientes.getColumna("ide_sacli").setNombreVisual("CODIGO");
      tab_clientes.getColumna("IDE_SATIDEN").setNombreVisual("TIPO DOCUMENTO");
      tab_clientes.getColumna("IDE_SATIDEN").setCombo(ser_empleados.getDocumentoIdentidad());
          tab_clientes.getColumna("ci_dni_sacli").setNombreVisual("IDENTIFICACION");
          tab_clientes.getColumna("ci_dni_sacli").setUnico(true);
          tab_clientes.getColumna("nombres_sacli").setNombreVisual("NOMBRES");
          tab_clientes.getColumna("apellidos_sacli").setNombreVisual("APELLIDOS");
          tab_clientes.getColumna("direccion_sacli").setNombreVisual("DIRECCION");
          tab_clientes.getColumna("telefono_sacli").setNombreVisual("TELEFONO");
          tab_clientes.getColumna("celular_sacli").setNombreVisual("CELULAR");
          tab_clientes.getColumna("correo_sacli").setNombreVisual("CORREO");
          tab_clientes.getColumna("genero_sacli").setNombreVisual("GENERO");
          tab_clientes.getColumna("ide_sacli").setOrden(0);
          tab_clientes.getColumna("IDE_SATIDEN").setOrden(1);
          tab_clientes.getColumna("ci_dni_sacli").setOrden(2);
          tab_clientes.getColumna("nombres_sacli").setOrden(3);
          tab_clientes.getColumna("apellidos_sacli").setOrden(4);
          tab_clientes.getColumna("direccion_sacli").setOrden(5);
          tab_clientes.getColumna("genero_sacli").setOrden(6);
          tab_clientes.getColumna("telefono_sacli").setOrden(7);
          tab_clientes.getColumna("celular_sacli").setOrden(8);
          tab_clientes.getColumna("genero_sacli").setOrden(9);
      List lista = new ArrayList();
          Object fila1[] = {
            "M", "Masculino"
           };
          Object fila2[] = {
            "F", "Femenino"
           };
          lista.add(fila1);
          lista.add(fila2);
          tab_clientes.getColumna("genero_sacli").setCombo(lista);
      tab_clientes.dibujar();
      
      PanelTabla pat_clientes = new PanelTabla();
      pat_clientes.setId("pat_clientes");
      pat_clientes.setPanelTabla(tab_clientes);
      Division div_clientes = new Division();
      div_clientes.setId("div_clientes");
      div_clientes.dividir1(pat_clientes);
      agregarComponente(div_clientes);
      mep_menu.dibujar(2, "fa fa-user", "Datos generales del cliente.", div_clientes, false);
    }
    @Override
    public void insertar() {
        tab_clientes.insertar();
    }

    @Override
    public void guardar() {
        tab_clientes.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_clientes.eliminar();
    }

    public Tabla getTab_clientes() {
        return tab_clientes;
    }

    public void setTab_clientes(Tabla tab_clientes) {
        this.tab_clientes = tab_clientes;
    }

    public Reporte getRep_reporte() {
        return rep_reporte;
    }

    public void setRep_reporte(Reporte rep_reporte) {
        this.rep_reporte = rep_reporte;
    }

    public SeleccionFormatoReporte getSel_rep() {
        return sel_rep;
    }

    public void setSel_rep(SeleccionFormatoReporte sel_rep) {
        this.sel_rep = sel_rep;
    }

    public Map getMap_parametros() {
        return map_parametros;
    }

    public void setMap_parametros(Map map_parametros) {
        this.map_parametros = map_parametros;
    }

    public AutoCompletar getAut_cliente() {
        return aut_cliente;
    }

    public void setAut_cliente(AutoCompletar aut_cliente) {
        this.aut_cliente = aut_cliente;
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }
       
}
