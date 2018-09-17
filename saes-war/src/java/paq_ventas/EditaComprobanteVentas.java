/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_ventas;

/**
 *
 * @author Andres
 */
import framework.componentes.Barra;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.Grupo;
import framework.componentes.Imagen;
import framework.componentes.ItemMenu;
import framework.componentes.Mascara;
import framework.componentes.MenuPanel;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import framework.componentes.Texto;
import framework.componentes.VisualizarPDF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import paq_clientes.ejb.ServiciosClientes;
import paq_empleados.ejb.ServiciosEmpleados;
import paq_servicio.ejb.ServiciosEstetica;
import paq_ventas.ejb.ServiciosVentas;
import sistema.aplicacion.Pantalla;

public class EditaComprobanteVentas extends Pantalla{
    
    private Tabla tab_tabla =  new Tabla();
    private Combo com_tipo_documento = new Combo();
    private final MenuPanel mep_menu = new MenuPanel();
    private final Mascara mas_num_factua = new Mascara();
    private Tabla tab_cabecera = new Tabla();
    private Tabla tab_detalle = new Tabla();
    private VisualizarPDF vipdf_comprobante = new VisualizarPDF();
    
    @EJB
    private final ServiciosVentas ser_ventas = (ServiciosVentas) utilitario.instanciarEJB(ServiciosVentas.class);
    
    @EJB
    private final ServiciosClientes ser_clientes = (ServiciosClientes) utilitario.instanciarEJB(ServiciosClientes.class);
    
    @EJB
    private final ServiciosEstetica ser_servicios = (ServiciosEstetica) utilitario.instanciarEJB(ServiciosEstetica.class);
    
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
    public EditaComprobanteVentas(){
        bar_botones.quitarBotonInsertar();
        bar_botones.quitarBotonsNavegacion();
        bar_botones.agregarComponente(new Etiqueta("Tipo de Documento :"));
        com_tipo_documento.setId("com_tipo_documento");
        com_tipo_documento.setCombo(ser_ventas.getTipoDocumento());
        com_tipo_documento.setMetodo("filtroComboDocumento");
        com_tipo_documento.eliminarVacio();
        bar_botones.agregarComponente(com_tipo_documento);
        
        
        bar_botones.agregarComponente(new Etiqueta("Número Secuencial :"));
        mas_num_factua.setMask("99999999");
        mas_num_factua.setSize(10);
        mas_num_factua.setStyle("font-size: 14px;font-weight: bold;text-align: right;");
        bar_botones.agregarComponente(mas_num_factua);
        
         Boton bot_buscar = new Boton();
         bot_buscar.setIcon("ui-icon-person");
         bot_buscar.setValue("Buscar Documento");
         bot_buscar.setMetodo("buscarDocumento");
         bar_botones.agregarBoton(bot_buscar);
        
        vipdf_comprobante.setId("vipdf_comprobante");
        vipdf_comprobante.setTitle("COMPROBANTE DE VENTA");
        agregarComponente(vipdf_comprobante);
         
        mep_menu.setMenuPanel("COMPROBANTES DE VENTAS", "22%");
        mep_menu.agregarItem("Listado de Comprobantes", "dibujarFacturas", "ui-icon-home");//15
        mep_menu.agregarItem("Editar Factura", "dibujarEdicion", "ui-icon-person");
        agregarComponente(mep_menu); 
        dibujarFacturas();
    }
    public void filtroComboDocumento(){
        
       // tab_tabla.setCondicion("ide_satido="+com_tipo_documento.getValue().toString());
        tab_tabla.setSql(ser_ventas.getSqlFacturas(com_tipo_documento.getValue().toString()));
        tab_tabla.ejecutarSql();
       // tab_tabla.ejecutarValorForanea(tab_tabla.getValorSeleccionado());
        utilitario.addUpdate("tab_tabla");       
    }
    public void dibujarFacturas() {
        Barra bar_menu = new Barra();
        bar_menu.setId("bar_menu");
        bar_menu.limpiar();
        Boton bot_ver = new Boton();
        bot_ver.setValue("IMPRIMIR COMPROBANTE");
        bot_ver.setTitle("Imprimir");
        bot_ver.setMetodo("imprimirFactura");
        bot_ver.setIcon("ui-icon-search");
        bar_menu.agregarComponente(bot_ver);
        
        Boton bot_editar = new Boton();
        bot_editar.setValue("EDITAR COMPROBANTE");
        bot_editar.setTitle("Editar");
        bot_editar.setMetodo("cargarFactura");
        bot_editar.setIcon("ui-icon-search");
        bar_menu.agregarComponente(bot_editar);
        
        tab_tabla = new Tabla();
        tab_tabla.setId("tab_tabla");
        tab_tabla.setSql(ser_ventas.getSqlFacturas(com_tipo_documento.getValue().toString()));
        tab_tabla.setCampoPrimaria("ide_saven");
        tab_tabla.getColumna("ide_saven").setVisible(false);
        tab_tabla.getColumna("ide_satido").setVisible(false);
        tab_tabla.getColumna("numero_secuencial_venta_saven").setFiltroContenido();
        tab_tabla.getColumna("fecha_saven").setFiltroContenido();
        tab_tabla.getColumna("nombre_cliente").setFiltroContenido();
        tab_tabla.getColumna("numero_secuencial_venta_saven").setNombreVisual("SECUENCIAL");
        tab_tabla.getColumna("descripcion_safopa").setNombreVisual("FORMA DE PAGO");
        tab_tabla.getColumna("fecha_saven").setNombreVisual("FECHA");
        tab_tabla.getColumna("nombre_cliente").setNombreVisual("NOMBRE");
        tab_tabla.getColumna("iva_fac_saven").setNombreVisual("IVA");
        tab_tabla.getColumna("total_saven").setNombreVisual("TOTAL");
        tab_tabla.getColumna("observacion_saven").setNombreVisual("OBSERVACIÓN");
        tab_tabla.getColumna("iva_fac_saven").alinearDerecha();
        tab_tabla.getColumna("total_saven").alinearDerecha();
        tab_tabla.getColumna("total_saven").setEstilo("font-size: 12px;font-weight: bold;");
        tab_tabla.getColumna("iva_fac_saven").setEstilo("font-size: 12px;font-weight: bold;");
        tab_tabla.setRows(13);
        tab_tabla.setLectura(true);
        tab_tabla.dibujar();
        PanelTabla pat_panel = new PanelTabla();
        pat_panel.getMenuTabla().quitarSubmenuOtros();
        pat_panel.setPanelTabla(tab_tabla);
        
        /*ItemMenu itemedita = new ItemMenu();
        itemedita.setValue("Modificar Vendedor");
        itemedita.setIcon("ui-icon-pencil");
        itemedita.setMetodo("abrirModificarVendedor");
        pat_panel.getMenuTabla().getChildren().add(itemedita);*/
        
        Grid grid = new Grid();
        grid.setStyle("position: relative;left:400px;");
        Imagen factura = new Imagen();
        //factura.setStyle("text-align:center;position:absolute;top:160px;left:500px;");
        factura.setValue("imagenes/factura_pequeña.png");
        grid.getChildren().add(factura);
        Grupo gru = new Grupo();
        gru.getChildren().add(grid);
        gru.getChildren().add(bar_menu);
        gru.getChildren().add(pat_panel);

        mep_menu.dibujar(1, "Listado de Comprobantes", gru);
    }
    public void dibujarEdicion(){
        tab_cabecera.setId("tab_cabecera");
        tab_cabecera.setTabla("saes_venta", "ide_saven", 1);
         tab_cabecera.setCondicion("ide_satido=-1");
         //tab_registro_ventas.setCondicion("ide_saven=-1");
         //tab_factura.setCampoOrden("ide_fafac desc");
         tab_cabecera.getColumna("ide_sacli").setCombo(ser_clientes.getCliente());
         tab_cabecera.getColumna("ide_sacli").setAutoCompletar();
         tab_cabecera.getColumna("ide_satido").setVisible(false);
         tab_cabecera.getColumna("ide_safopa").setCombo(ser_ventas.getFormaPago());
         tab_cabecera.agregarRelacion(tab_detalle);
         tab_cabecera.setTipoFormulario(true);
         tab_cabecera.getColumna("total_saven").setEtiqueta();
         tab_cabecera.getColumna("total_saven").setEstilo("font-size:18px;font-weight: bold;text-decoration: underline;color:green");
         tab_cabecera.getColumna("fecha_saven").setValorDefecto(utilitario.getFechaActual());
         tab_cabecera.getColumna("ide_saven").setNombreVisual("CODIGO");
         tab_cabecera.getColumna("ide_safopa").setNombreVisual("FORMA DE PAGO");
         tab_cabecera.getColumna("numero_secuencial_venta_saven").setNombreVisual("Nº SECUENCIAL");
         tab_cabecera.getColumna("ide_sacli").setNombreVisual("CLIENTE");
         tab_cabecera.getColumna("ide_sacli").setMetodoChange("datosCliente");
         tab_cabecera.getColumna("direccion_clie_saven").setNombreVisual("DIRECCIÒN");
         tab_cabecera.getColumna("fecha_saven").setNombreVisual("FECHA");
         tab_cabecera.getColumna("total_saven").setNombreVisual("TOTAL");
         tab_cabecera.getColumna("IVA_FAC_SAVEN").setNombreVisual("TOTAL IVA");
         tab_cabecera.getColumna("observacion_saven").setNombreVisual("OBSERVACIÒNES");
         tab_cabecera.getColumna("IVA_FAC_SAVEN").setEstilo("font-size:18px;font-weight: bold;text-decoration: underline;color:black");
         tab_cabecera.getColumna("IVA_FAC_SAVEN").setEtiqueta();
         tab_cabecera.getColumna("subtotal_fac_saven").setVisible(false);
         tab_cabecera.getColumna("ide_saven").setOrden(1);
         tab_cabecera.getColumna("numero_secuencial_venta_saven").setOrden(2);
         tab_cabecera.getColumna("ide_sacli").setOrden(3);
         tab_cabecera.getColumna("direccion_clie_saven").setOrden(4);
         tab_cabecera.getColumna("telefono_clie_saven").setOrden(5);
         tab_cabecera.getColumna("ide_safopa").setOrden(6);
         tab_cabecera.getColumna("fecha_saven").setOrden(7);
         tab_cabecera.getColumna("total_saven").setOrden(8);
         tab_cabecera.getColumna("numero_secuencial_venta_saven").setEstilo("font-size: 14px;font-weight: bold;text-align: right;");
         tab_cabecera.getColumna("numero_secuencial_venta_saven").setLongitud(10);
         tab_cabecera.getColumna("numero_secuencial_venta_saven").setMascara("99999999");
         tab_cabecera.getGrid().setColumns(4);
         tab_cabecera.dibujar();
         
         PanelTabla pat_ventas = new PanelTabla();
          pat_ventas.setId("pat_ventas");
          pat_ventas.getMenuTabla().quitarSubmenuOtros();
          pat_ventas.setPanelTabla(tab_cabecera);
          
          tab_detalle.setId("tab_detalle");   //identificador
          tab_detalle.setTabla("saes_detalle", "ide_sadet", 2);
          tab_detalle.getColumna("ide_sadet").setOrden(1);
          tab_detalle.getColumna("ide_saser").setOrden(2);
          tab_detalle.getColumna("ide_saemp").setOrden(3);
          tab_detalle.getColumna("cantidad_sadet").setOrden(4);
          tab_detalle.getColumna("valor_sadet").setOrden(5);
          tab_detalle.getColumna("cantidad_sadet").setValorDefecto("0");
          tab_detalle.getColumna("ide_saser").setCombo(ser_servicios.getServicio("true"));
          tab_detalle.getColumna("ide_saser").setMetodoChange("asignarSubtotal");
          tab_detalle.getColumna("ide_saser").setAutoCompletar();
          //tab_detalle.getColumna("ide_saser").setMetodoChange("calcularGanancias");
          tab_detalle.getColumna("ide_saemp").setCombo(ser_empleados.getEmpleado("true"));
          tab_detalle.getColumna("cantidad_sadet").setEstilo("font-size:13px;font-weight:bold;");
          tab_detalle.getColumna("valor_sadet").setEstilo("font-size:13px;font-weight:bold;");
          tab_detalle.getColumna("subtotal_sadet").setEtiqueta();
          tab_detalle.getColumna("subtotal_sadet").setEstilo("font-size:14px;font-weight:bold;color:green");
          tab_detalle.getColumna("gana_empre_sadet").setEtiqueta();
          tab_detalle.getColumna("gana_empre_sadet").setEstilo("font-size:13px;font-weight:bold;");
          tab_detalle.getColumna("gana_emplea_sadet").setEtiqueta();
          tab_detalle.getColumna("gana_emplea_sadet").setEstilo("font-size:13px;font-weight:bold;");
          tab_detalle.getColumna("cantidad_sadet").setMetodoChange("calcularDetalle");
          tab_detalle.getColumna("valor_sadet").setMetodoChange("calcularDetalle");
          tab_detalle.getColumna("ide_sadet").setNombreVisual("CODIGO");
          tab_detalle.getColumna("ide_saser").setNombreVisual("SERVICIO");
          tab_detalle.getColumna("ide_saemp").setNombreVisual("EMPLEADO");
          tab_detalle.getColumna("cantidad_sadet").setNombreVisual("CANTIDAD");
          tab_detalle.getColumna("valor_sadet").setNombreVisual("VALOR");
          tab_detalle.getColumna("subtotal_sadet").setNombreVisual("SUBTOTAL");
          tab_detalle.getColumna("gana_empre_sadet").setNombreVisual("G. EMPRESA");
          tab_detalle.getColumna("gana_emplea_sadet").setNombreVisual("G. EMPLEADO");
          tab_detalle.dibujar();
                  
          PanelTabla pat_detalle = new PanelTabla();
          pat_detalle.setId("pat_detalle");
          pat_detalle.getMenuTabla().quitarSubmenuOtros();
          pat_detalle.setPanelTabla(tab_detalle);
          
          Division div_division = new Division();
          div_division.setId("div_division");
          div_division.dividir2(pat_ventas, pat_detalle, "50%", "H");
          agregarComponente(div_division);
          mep_menu.dibujar(1, "fa fa-user", "Edicion de comprobantes", div_division, false);
    }
    public void cargarFactura() {
       // String ide_factura = tab_tabla.getValorSeleccionado();
//        System.out.println("factura "+ide_factura);
         dibujarEdicion();
         tab_cabecera.setCondicion("ide_saven ="+tab_tabla.getValor("ide_saven"));
         tab_cabecera.ejecutarSql();
         tab_detalle.ejecutarValorForanea(tab_cabecera.getValorSeleccionado());
      /*  if (ide_factura != null) {
            
        }*/
    }
    public void buscarDocumento(){
        System.out.println("valor "+mas_num_factua.getValue().toString());
        if (mas_num_factua.getValue() != null || mas_num_factua.getValue().toString().isEmpty() == false){
            try{
                dibujarEdicion();
                tab_cabecera.setCondicion("numero_secuencial_venta_saven = '"+mas_num_factua.getValue()+"'");
                tab_cabecera.ejecutarSql();
                tab_detalle.ejecutarValorForanea(tab_cabecera.getValorSeleccionado());
            }catch(Exception e){
                utilitario.agregarMensajeError("Error", " "+e);
            }
        }
        else{
            utilitario.agregarMensajeError("Ingrese un numero secuencial", "");
        }
    }
    public void imprimirFactura(){
        if (tab_tabla.getValorSeleccionado() != null) {
                        Map parametro = new HashMap();
                        parametro.put("pide_venta", Integer.parseInt(tab_tabla.getValorSeleccionado()));
                        vipdf_comprobante.setVisualizarPDF("rep_ventas/rep_comprobante_consumidor_final.jasper", parametro);
                        vipdf_comprobante.dibujar();
                        utilitario.addUpdate("vipdf_comprobante");
        } else {
            utilitario.agregarMensajeInfo("Seleccione un comprobante para poder imprimir", "");
        }
    }
    @Override
    public void insertar() {
        tab_tabla.insertar();
    }

    @Override
    public void guardar() {
        tab_tabla.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_tabla.eliminar();
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }

    public Combo getCom_tipo_documento() {
        return com_tipo_documento;
    }

    public void setCom_tipo_documento(Combo com_tipo_documento) {
        this.com_tipo_documento = com_tipo_documento;
    }
    
    public Tabla getTab_cabecera() {
        return tab_cabecera;
    }

    public void setTab_cabecera(Tabla tab_cabecera) {
        this.tab_cabecera = tab_cabecera;
    }

    public Tabla getTab_detalle() {
        return tab_detalle;
    }

    public void setTab_detalle(Tabla tab_detalle) {
        this.tab_detalle = tab_detalle;
    }
    
}
    
