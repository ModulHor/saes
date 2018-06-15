
package paq_ventas;

/**
 *
 * @author Andres
 */
import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.Grupo;
import framework.componentes.Lista;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import paq_citas.ejb.ServiciosCitas;
import paq_ventas.ejb.ServiciosVentas;
import paq_clientes.ejb.ServiciosClientes;
import paq_empleados.ejb.ServiciosEmpleados;
import paq_servicio.ejb.ServiciosEstetica;
import sistema.aplicacion.Pantalla;

public class RegistroVentas extends Pantalla {
    private Tabla tab_registro_ventas =  new Tabla();
    private Tabla tab_detalle =  new Tabla();
    private AutoCompletar aut_valor = new AutoCompletar();
    double dou_total = 0;
    double dou_base_aprobada = 0;
    private Combo com_tipo_documento = new Combo(); 
    private SeleccionTabla set_actualizar_cliente = new SeleccionTabla();
    private SeleccionTabla set_crear_cliente = new SeleccionTabla();
    private SeleccionTabla set_facturar_cita = new SeleccionTabla();
    private Dialogo crear_cliente_dialogo = new Dialogo();
    private Tabla tab_cliente = new Tabla();
    private Dialogo dia_periodo = new Dialogo();
    private Combo com_dia_periodo = new Combo();
    private String modalidad = "";
    private boolean boo_documento_valido = true;
    private Reporte rep_reporte=new Reporte();
    private SeleccionFormatoReporte sel_rep=new SeleccionFormatoReporte();  
    
    @EJB
    private final ServiciosClientes ser_clientes = (ServiciosClientes) utilitario.instanciarEJB(ServiciosClientes.class);
    
    @EJB
    private final ServiciosVentas ser_ventas = (ServiciosVentas) utilitario.instanciarEJB(ServiciosVentas.class);
    
    @EJB
    private final ServiciosEstetica ser_servicios = (ServiciosEstetica) utilitario.instanciarEJB(ServiciosEstetica.class);
    
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
    @EJB
    private final ServiciosCitas ser_citas = (ServiciosCitas) utilitario.instanciarEJB(ServiciosCitas.class);
    
       public RegistroVentas (){
         
         rep_reporte.setId("rep_reporte");
         rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");
         agregarComponente(rep_reporte);
         bar_botones.agregarReporte();
         sel_rep.setId("sel_rep");
         agregarComponente(sel_rep);
           
         Etiqueta eti_documento = new Etiqueta("TIPO DE DOCUMENTO:");
         bar_botones.agregarComponente(eti_documento);
          
         com_tipo_documento.setId("com_tipo_documento");
         com_tipo_documento.setCombo(ser_ventas.getTipoDocumento());
         com_tipo_documento.setMetodo("filtroComboDocumento");
         bar_botones.agregarComponente(com_tipo_documento);
                 
         
        // creo dialogo para seleccionar el periodo
        dia_periodo.setId("dia_periodo");
        dia_periodo.setTitle("PERIODO");
        dia_periodo.setWidth("40%");
        dia_periodo.setHeight("18%");
        dia_periodo.getBot_aceptar().setMetodo("dialogoPeriodo");
        dia_periodo.setResizable(false);
        
        com_dia_periodo.setId("com_dia_periodo");
        com_dia_periodo.setCombo(ser_citas.getPeriodo("true"));
        
        Grupo gru_cuerpo = new Grupo();
        Etiqueta eti_mensaje = new Etiqueta();
        eti_mensaje.setValue("Seleccione el Periodo                                              ");
        eti_mensaje.setStyle("font-size: 13px;border: none;text-shadow: 0px 2px 3px #ccc;background: none;");
        
        gru_cuerpo.getChildren().add(eti_mensaje);
        gru_cuerpo.getChildren().add(com_dia_periodo);

        dia_periodo.setDialogo(gru_cuerpo);
        agregarComponente(dia_periodo);
          
         tab_registro_ventas.setHeader("FACTURACION");
         tab_registro_ventas.setId("tab_registro_ventas");   //identificador
         tab_registro_ventas.setTabla("saes_venta", "ide_saven", 1);
         tab_registro_ventas.setCondicion("ide_satido=-1");
         //tab_registro_ventas.setCondicion("ide_saven=-1");
         //tab_factura.setCampoOrden("ide_fafac desc");
         tab_registro_ventas.getColumna("ide_sacli").setCombo(ser_clientes.getCliente());
         tab_registro_ventas.getColumna("ide_sacli").setAutoCompletar();
         tab_registro_ventas.getColumna("ide_satido").setVisible(false);
         tab_registro_ventas.getColumna("ide_safopa").setCombo(ser_ventas.getFormaPago());
         tab_registro_ventas.agregarRelacion(tab_detalle);
         tab_registro_ventas.setTipoFormulario(true);
         tab_registro_ventas.getColumna("total_saven").setEtiqueta();
         tab_registro_ventas.getColumna("total_saven").setEstilo("font-size:15px;font-weight: bold;text-decoration: underline;color:red");
         tab_registro_ventas.getColumna("fecha_saven").setValorDefecto(utilitario.getFechaActual());
         tab_registro_ventas.getColumna("ide_saven").setNombreVisual("CODIGO");
         tab_registro_ventas.getColumna("ide_safopa").setNombreVisual("FORMA DE PAGO");
         tab_registro_ventas.getColumna("numero_secuencial_venta_saven").setNombreVisual("Nº SECUENCIAL");
         tab_registro_ventas.getColumna("ide_sacli").setNombreVisual("CLIENTE");
         tab_registro_ventas.getColumna("fecha_saven").setNombreVisual("FECHA");
         tab_registro_ventas.getColumna("total_saven").setNombreVisual("TOTAL");
         tab_registro_ventas.getColumna("ide_saven").setOrden(1);
         tab_registro_ventas.getColumna("numero_secuencial_venta_saven").setOrden(2);
         tab_registro_ventas.getColumna("ide_sacli").setOrden(3);
         tab_registro_ventas.getColumna("ide_safopa").setOrden(4);
         tab_registro_ventas.getColumna("fecha_saven").setOrden(5);
         tab_registro_ventas.getColumna("total_saven").setOrden(6);
         tab_registro_ventas.getColumna("numero_secuencial_venta_saven").setEstilo("font-size: 14px;font-weight: bold;text-align: right;");
         tab_registro_ventas.getColumna("numero_secuencial_venta_saven").setLongitud(10);
         tab_registro_ventas.getColumna("numero_secuencial_venta_saven").setMascara("99999999");
         tab_registro_ventas.getGrid().setColumns(2);
         tab_registro_ventas.dibujar();
                  
          PanelTabla pat_ventas = new PanelTabla();
          pat_ventas.setId("pat_ventas");
          pat_ventas.setPanelTabla(tab_registro_ventas);
          
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
          pat_detalle.setPanelTabla(tab_detalle);
          
          Division div_ventas = new Division();
          div_ventas.setId("div_ventas");
          div_ventas.dividir2(pat_ventas, pat_detalle, "50%", "H");
          agregarComponente(div_ventas);      
          
          Boton bot_actualizar_cliente = new Boton();
          bot_actualizar_cliente.setIcon("ui-icon-person");
          bot_actualizar_cliente.setValue("Buscar Cliente");
          bot_actualizar_cliente.setMetodo("actualizarCliente");
          bar_botones.agregarBoton(bot_actualizar_cliente);
          
          set_actualizar_cliente.setId("set_actualizar_cliente");
          set_actualizar_cliente.setSeleccionTabla(ser_clientes.getCliente(), "ide_sacli");
          set_actualizar_cliente.getTab_seleccion().getColumna("ci_dni_sacli").setFiltro(true);
          set_actualizar_cliente.getTab_seleccion().getColumna("apellidos_sacli").setFiltro(true);
          set_actualizar_cliente.setRadio();
          set_actualizar_cliente.getBot_aceptar().setMetodo("aceptarCliente");
          agregarComponente(set_actualizar_cliente);
          
           //PANTALLA CREAR CLIENTE
          crear_cliente_dialogo.setId("crear_cliente_dialogo");
          crear_cliente_dialogo.setTitle("CREAR CLIENTE");
          crear_cliente_dialogo.setWidth("55%");
          crear_cliente_dialogo.setHeight("55%");
          
          Grid gri_cuerpo = new Grid();
          tab_cliente.setId("tab_cliente");
          tab_cliente.setTabla("saes_cliente", "ide_sacli", 3);
          tab_cliente.setTipoFormulario(true);
          //tab_cliente.setCondicion("ide_sacli=-1");//para que aparesca vacia
          tab_cliente.getGrid().setColumns(2);
          //oculto todos los campos
           for (int i = 0; i < tab_cliente.getTotalColumnas(); i++) {
            tab_cliente.getColumnas()[i].setVisible(true);
           }
          tab_cliente.getColumna("ide_sacli").setNombreVisual("CODIGO");
          tab_cliente.getColumna("IDE_SATIDEN").setNombreVisual("TIPO DOCUMENTO");
          tab_cliente.getColumna("IDE_SATIDEN").setCombo(ser_empleados.getDocumentoIdentidad());
          tab_cliente.getColumna("ci_dni_sacli").setNombreVisual("DOCUMENTO IDENTIFICACIÓN");
          tab_cliente.getColumna("nombres_sacli").setNombreVisual("NOMBRES");
          tab_cliente.getColumna("apellidos_sacli").setNombreVisual("APELLIDOS");
          tab_cliente.getColumna("direccion_sacli").setNombreVisual("DIRECCION");
          tab_cliente.getColumna("telefono_sacli").setNombreVisual("TELEFONO");
          tab_cliente.getColumna("celular_sacli").setNombreVisual("CELULAR");
          tab_cliente.getColumna("correo_sacli").setNombreVisual("CORREO");
          tab_cliente.getColumna("genero_sacli").setNombreVisual("GENERO");
          tab_cliente.getColumna("ide_sacli").setOrden(0);
          tab_cliente.getColumna("IDE_SATIDEN").setOrden(1);
          tab_cliente.getColumna("ci_dni_sacli").setOrden(2);
          tab_cliente.getColumna("nombres_sacli").setOrden(3);
          tab_cliente.getColumna("apellidos_sacli").setOrden(4);
          tab_cliente.getColumna("direccion_sacli").setOrden(5);
          tab_cliente.getColumna("genero_sacli").setOrden(6);
          tab_cliente.getColumna("telefono_sacli").setOrden(7);
          tab_cliente.getColumna("celular_sacli").setOrden(8);
          tab_cliente.getColumna("genero_sacli").setOrden(9);
          tab_cliente.getColumna("IDE_SATIDEN").setMetodoChange("validar_documento");
          tab_cliente.getColumna("CI_DNI_SACLI").setMetodoChange("validar_documento");
          
          List lista = new ArrayList();
          Object fila1[] = {
            "M", "Masculino"
           };
          Object fila2[] = {
            "F", "Femenino"
           };
          lista.add(fila1);
          lista.add(fila2);
          tab_cliente.getColumna("genero_sacli").setCombo(lista);
          tab_cliente.setMostrarNumeroRegistros(false);//PARA Q NO MUESTRE EL TITULO REGSITRO 1 DE 1
          tab_cliente.dibujar();
          gri_cuerpo.getChildren().add(tab_cliente);
          crear_cliente_dialogo.getBot_aceptar().setMetodo("guardarCliente");
          crear_cliente_dialogo.setDialogo(gri_cuerpo);
          agregarComponente(crear_cliente_dialogo);

          //BOTON CREAR CLIENTE
          Boton bot_crearCliente = new Boton();
          bot_crearCliente.setValue("Crear Cliente");
          bot_crearCliente.setIcon("ui-icon-contact");
          bot_crearCliente.setMetodo("abrirDialogoCliente");
          bar_botones.agregarBoton(bot_crearCliente);
          
          
          Boton bot_citas = new Boton();
          bot_citas.setValue("Facturar Citas Agendadas");
          bot_citas.setIcon("ui-icon-circle-zoomout");
          bot_citas.setMetodo("abrirDialogoCita");
          bar_botones.agregarBoton(bot_citas);
          
          set_facturar_cita.setId("set_facturar_cita");
          set_facturar_cita.setTitle("CITAS AGENDADAS");
          set_facturar_cita.setSeleccionTabla(ser_citas.getConsultaCitasAprobadas("true", "-1"), "ide_sacita");
          set_facturar_cita.setWidth("80%");
          set_facturar_cita.setHeight("70%");
          set_facturar_cita.setRadio();
         // set_facturar_cita.getTab_seleccion().getColumna("documento_identidad_gtemp").setFiltroContenido();
          //set_facturar_cita.getTab_seleccion().getColumna("nombres_empleado").setFiltroContenido();
          set_facturar_cita.getBot_aceptar().setMetodo("aceptarCita");
          agregarComponente(set_facturar_cita);
          
          Boton bot_imprimir = new Boton();
         bot_imprimir.setValue("Imprimir Factura");
         bot_imprimir.setIcon("ui-icon-print");
         bot_imprimir.setMetodo("abrirListaReportes");
         bar_botones.agregarBoton(bot_imprimir);

       }
       @Override
    public void insertar() {
        if(com_tipo_documento.getValue() == null){
            utilitario.agregarMensajeError("ERROR", "Seleccione el Tipo de Documento");
        return;
        }
        else if (tab_registro_ventas.isFocus()){
       tab_registro_ventas.insertar();
      
       TablaGenerica secuencial = utilitario.consultar("select ((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1) as maximo,\n" +
                                                       "length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') as longitud,\n" +
                                                       "(case when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 1 then '0000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 2 then '000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 3 then '00000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 4 then '0000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 5 then '000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 6 then '00'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 7 then '0'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "end) as numero from saes_venta");
       
       tab_registro_ventas.setValor("numero_secuencial_venta_saven", secuencial.getValor("numero"));
       
       tab_registro_ventas.setValor("ide_satido", com_tipo_documento.getValue().toString());
       utilitario.addUpdateTabla(tab_registro_ventas, "ide_satido", "");
       utilitario.addUpdate("tab_registro_ventas");
       }
        else if (tab_detalle.isFocus()){
            tab_detalle.insertar();
        }
    }
    public void abrirDialogoCita(){
        if(com_tipo_documento.getValue() == null){
            utilitario.agregarMensajeError("ERROR", "Seleccione el Tipo de Documento");
        return;
        } else {
        dia_periodo.dibujar();
                }
    }
    public void aceptarCita(){
         
         String str_cita = set_facturar_cita.getValorSeleccionado();
        TablaGenerica tab_dat_citas = utilitario.consultar("select * from saes_cita where ide_sacita in ("+str_cita+")  and aprobado_sacita = true order by fecha_cita_sacita");
        //TablaGenerica tab_dat_citas = utilitario.consultar(ser_citas.getCitasAprobadasTabla(str_cita, "true"));
        for (int i=0;i<tab_dat_citas.getTotalFilas();i++){
                if(tab_registro_ventas.isFilaInsertada()==false){
                 tab_registro_ventas.insertar();	
                 TablaGenerica secuencial = utilitario.consultar("select ((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1) as maximo,\n" +
                                                       "length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') as longitud,\n" +
                                                       "(case when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 1 then '0000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 2 then '000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 3 then '00000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 4 then '0000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 5 then '000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 6 then '00'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 7 then '0'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "end) as numero from saes_venta");
                tab_registro_ventas.setValor("numero_secuencial_venta_saven", secuencial.getValor("numero"));
                 }
                
                tab_registro_ventas.setValor("ide_sacli", tab_dat_citas.getValor(i,"ide_sacli"));
                tab_registro_ventas.setValor("ide_satido", com_tipo_documento.getValue().toString());
                utilitario.addUpdateTabla(tab_registro_ventas, "ide_satido", "");
                tab_registro_ventas.guardar();

                guardarPantalla();
	       }
            // tab_empleado.guardar();
            // guardarPantalla();
             set_facturar_cita.cerrar();
             	     utilitario.addUpdate("tab_registro_ventas");
             citaDetalle();
    }
    public void citaDetalle(){
        String str_cita = set_facturar_cita.getValorSeleccionado();
        String ide_venta = tab_registro_ventas.getValorSeleccionado();
        TablaGenerica tab_dat_citas = utilitario.consultar("select * from saes_cita where ide_sacita in ("+str_cita+")  and aprobado_sacita = true order by fecha_cita_sacita");
         for (int i=0;i<tab_dat_citas.getTotalFilas();i++){        
                tab_detalle.insertar();
                tab_detalle.setValor("ide_saser",tab_dat_citas.getValor(i,"ide_saser"));
                asignarSubtotal();
                tab_detalle.guardar();
                guardarPantalla();
         }
      //  String ejecutarSql = utilitario.getConexion().ejecutarSql("select numero_secuencial(("+ide_venta+"))");
        utilitario.getConexion().ejecutarSql("update saes_cita set aprobado_sacita = false where ide_sacita = "+str_cita);
        utilitario.addUpdate("set_facturar_cita"); 
        utilitario.addUpdate("tab_detalle"); 
    }
    
     public void dialogoPeriodo(){
    
    if(com_dia_periodo.getValue() == null){
            utilitario.agregarMensajeInfo("ADVERTENCIA", "Seleccione el Periodo");
        return;
        }
        else {
        modalidad = com_dia_periodo.getValue()+"";
        dia_periodo.cerrar();
        set_facturar_cita.getTab_seleccion().setSql(ser_citas.getConsultaCitasAprobadas("true", com_dia_periodo.getValue().toString()));
        set_facturar_cita.getTab_seleccion().ejecutarSql();              
            set_facturar_cita.dibujar();
            
                }
}
    
     public void validar_documento (AjaxBehaviorEvent evt)
    {
        tab_cliente.modificar(evt);
        String cedula = tab_cliente.getValor("CI_DNI_SACLI");
        String tipo_documento = tab_cliente.getValor("IDE_SATIDEN");
        //valido la cedula ide_tipo_documento = 1
        if (tipo_documento != null && tipo_documento.equalsIgnoreCase("2"))
        {
            boo_documento_valido = utilitario.validarCedula(cedula);
        }
        //valido el ruc ide_tipo_documento = 2
        if (tipo_documento != null && tipo_documento.equalsIgnoreCase("5"))
        {
            boo_documento_valido = utilitario.validarRUC(cedula);
        }
    }
     
    public void guardarCliente(){
        
    if (tab_cliente.getValor("IDE_SATIDEN") != null){
        if (boo_documento_valido){
    if (tab_cliente.guardar()) {
                if (utilitario.getConexion().ejecutarListaSql().isEmpty()) {
                    utilitario.agregarMensaje("El Cliente se guardo correctamente", "");
                    
                    //Se guardo correctamente
                    
                crear_cliente_dialogo.cerrar();
                if(tab_registro_ventas.isFilaInsertada()==false){
                 tab_registro_ventas.insertar();	
                 TablaGenerica secuencial = utilitario.consultar("select ((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1) as maximo,\n" +
                                                       "length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') as longitud,\n" +
                                                       "(case when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 1 then '0000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 2 then '000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 3 then '00000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 4 then '0000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 5 then '000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 6 then '00'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 7 then '0'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "end) as numero from saes_venta");
                tab_registro_ventas.setValor("numero_secuencial_venta_saven", secuencial.getValor("numero"));
                 }
                tab_registro_ventas.getColumna("ide_sacli").actualizarCombo();
                tab_registro_ventas.setValor("ide_satido", com_tipo_documento.getValue().toString());
                utilitario.addUpdateTabla(tab_registro_ventas, "ide_satido", "");
                 
                //CARGA EL CLIENTE Q SE INSERTO
                tab_registro_ventas.setValor("ide_sacli", tab_cliente.getValor("ide_sacli"));
                //tab_factura.setValor("ide_fadaf",  aut_factura.getValor());
                System.out.println(" valor del cliente al crear " + tab_cliente.getValor("ide_sacli"));
                utilitario.addUpdate("tab_registro_ventas");
                tab_registro_ventas.modificar(tab_registro_ventas.getFilaActual());
                    
                }
            }
        }
        else
        {
            utilitario.agregarMensajeError("Validación", "El documento de identificación ingresado no es válido, "
                    + "por favor corrija antes de guardar.");
        }
        }
        else
        {
            utilitario.agregarMensajeError("Información", "Debe seleccionar el tipo de documento, "
                    + "por favor corrija antes de guardar.");
        }
}
       
    @Override
    public void guardar() {
        
        if(tab_registro_ventas.isFocus()){
      
        tab_registro_ventas.guardar();
        }
        else if (tab_detalle.isFocus()){
            tab_detalle.guardar();
        }
      
      guardarPantalla();
      
 
    }
    @Override
    public void eliminar() {
        if(tab_registro_ventas.isFocus()){
                  tab_registro_ventas.eliminar();
        }
        else if (tab_detalle.isFocus()){
            tab_detalle.eliminar();
        }
    }
    public void calcular() {
        //Variables para almacenar y calcular el total del detalle
        double dou_cantidad_sadet = 0;
        double dou_valor_sadet = 0;
        double dou_subtotal_sadet = 0;
        double dou_gana_emplea_sadet = 0;
        double dou_gana_empre_sadet = 0;
        String v_cin =  "50";
        String v_cua =  "40";
        String num_porcentaje="";
        String ide_saser = tab_detalle.getValor("ide_saser");
        System.out.println("imprimo servicio "+ide_saser);
        TablaGenerica tab_servicios = utilitario.consultar("select ide_saser, porcentaje_saser from saes_servicio where ide_saser = "+ide_saser+"");
        for (int i=0;i<tab_servicios.getTotalFilas();i++){
        num_porcentaje = tab_servicios.getValor("porcentaje_saser");
        System.out.println("imprimo porcntaje "+num_porcentaje);
        }
        System.out.println("imprimo porcntaje "+num_porcentaje);

        if (num_porcentaje.equals(v_cua)){
        try {
            //Obtenemos el valor de la cantidad
            dou_cantidad_sadet = Double.parseDouble(tab_detalle.getValor("cantidad_sadet"));
        } catch (Exception e) {
        }

        try {
            //Obtenemos el valor
            dou_valor_sadet = Double.parseDouble(tab_detalle.getValor("valor_sadet"));
        } catch (Exception e) {
        }

        //Calculamos el subtotal y ganancias
        dou_subtotal_sadet = dou_cantidad_sadet * dou_valor_sadet;
        dou_gana_empre_sadet = dou_subtotal_sadet * 0.60;
        dou_gana_emplea_sadet = dou_subtotal_sadet * 0.40;

        //Asignamos el total a la tabla detalle, con 2 decimales
        tab_detalle.setValor("subtotal_sadet", utilitario.getFormatoNumero(dou_subtotal_sadet, 3));
        
        try {
         //Obtenemos el valor del subtotal
           dou_valor_sadet = Double.parseDouble(tab_detalle.getValor("subtotal_sadet"));
        } catch (Exception e) {
        }
        //Asignamos ganancia de los empleados
        tab_detalle.setValor("gana_empre_sadet", utilitario.getFormatoNumero(dou_gana_empre_sadet, 2));
        tab_detalle.setValor("gana_emplea_sadet", utilitario.getFormatoNumero(dou_gana_emplea_sadet, 2));
        //Actualizamos el campo de la tabla AJAX
        utilitario.addUpdateTabla(tab_detalle, "subtotal_sadet", "tab_detalle");
        utilitario.addUpdateTabla(tab_detalle, "gana_empre_sadet", "tab_detalle");
        utilitario.addUpdateTabla(tab_detalle, "gana_emplea_sadet", "tab_detalle");
        utilitario.addUpdate("tab_detalle");
        }
        if (num_porcentaje.equals(v_cin)){
        try {
            //Obtenemos el valor de la cantidad
            dou_cantidad_sadet = Double.parseDouble(tab_detalle.getValor("cantidad_sadet"));
        } catch (Exception e) {
        }

        try {
            //Obtenemos el valor
            dou_valor_sadet = Double.parseDouble(tab_detalle.getValor("valor_sadet"));
        } catch (Exception e) {
        }

        //Calculamos el subtotal y ganancias
        dou_subtotal_sadet = dou_cantidad_sadet * dou_valor_sadet;
        dou_gana_empre_sadet = dou_subtotal_sadet * 0.50;
        dou_gana_emplea_sadet = dou_subtotal_sadet * 0.50;

        //Asignamos el total a la tabla detalle, con 2 decimales
        tab_detalle.setValor("subtotal_sadet", utilitario.getFormatoNumero(dou_subtotal_sadet, 3));
        
        try {
         //Obtenemos el valor del subtotal
           dou_valor_sadet = Double.parseDouble(tab_detalle.getValor("subtotal_sadet"));
        } catch (Exception e) {
        }
        //Asignamos ganancia de los empleados
        tab_detalle.setValor("gana_empre_sadet", utilitario.getFormatoNumero(dou_gana_empre_sadet, 2));
        tab_detalle.setValor("gana_emplea_sadet", utilitario.getFormatoNumero(dou_gana_emplea_sadet, 2));
        //Actualizamos el campo de la tabla AJAX
        utilitario.addUpdateTabla(tab_detalle, "subtotal_sadet", "tab_detalle");
        utilitario.addUpdateTabla(tab_detalle, "gana_empre_sadet", "tab_detalle");
        utilitario.addUpdateTabla(tab_detalle, "gana_emplea_sadet", "tab_detalle");
        utilitario.addUpdate("tab_detalle");
        }
        calcularTotal();
    }
    public void calcularDetalle(AjaxBehaviorEvent evt){
        tab_detalle.modificar(evt);
        calcular(); 
    }
    public void calcularTotal(){
        dou_total = 0;
        dou_base_aprobada = 0;
        for (int i = 0; i < tab_detalle.getTotalFilas(); i++) {
            dou_base_aprobada += Double.parseDouble(tab_detalle.getValor(i, "subtotal_sadet"));
        }
        tab_registro_ventas.setValor("total_saven", utilitario.getFormatoNumero(dou_base_aprobada, 3));
        tab_registro_ventas.modificar(tab_registro_ventas.getFilaActual());//para que haga el update        
        utilitario.addUpdate("tab_registro_ventas");
        tab_detalle.guardar();
        tab_registro_ventas.guardar();
        guardarPantalla();
    }
    public void prueba (AjaxBehaviorEvent evt){
        /*tab_adquisiones.modificar(evt);
        String valor=tab_adquisiones.getValor("IDE_ADEMAP");
        TablaGenerica empleado = utilitario.consultar("select ide_ademap,FECHA_INGRE from ADQ_EMPLEADO_APRUEBA where IDE_ADEMAP="+valor);
        tab_adquisiones.setValor("uso_adcomp", empleado.getValor("FECHA_INGRE"));
        utilitario.addUpdate("tab_adquisiones");*/
        
    }
    public void asignarSubtotal(){
        String ide_saser = tab_detalle.getValor("ide_saser");
        TablaGenerica tab_serv = utilitario.consultar("select ide_saser, nombre_saser, precio_inicial_saser from saes_servicio where ide_saser = "+ide_saser+"");
          for (int i=0;i<tab_serv.getTotalFilas();i++){
              tab_detalle.setValor("valor_sadet",tab_serv.getValor(i,"precio_inicial_saser"));
              //utilitario.agregarMensaje("este es el id", ide_saser);
          }
        utilitario.addUpdateTabla(tab_detalle, "subtotal_sadet", "tab_detalle");
        utilitario.addUpdate("tab_detalle");
    }
    
    public void filtroComboDocumento(){
        
        tab_registro_ventas.setCondicion("ide_satido="+com_tipo_documento.getValue().toString());
        tab_registro_ventas.ejecutarSql();
        utilitario.addUpdate("tab_registro_ventas");
    }
    public void seleccionoAutocompletar(SelectEvent evt){
       //Cuando selecciona una opcion del autocompletar
       aut_valor.onSelect(evt);//siempre debe hacerse el onSelect(evt)
       utilitario.agregarMensaje("VALOR", aut_valor.getValor()); //muestra el valor que selecciono
       utilitario.agregarMensaje("NOMBRE", aut_valor.getValorArreglo(1)); //muestra el nombre que selecciono
}
    //ACTUALIZAR CLIENTE
    public void actualizarCliente() {
        if(com_tipo_documento.getValue() == null){
            utilitario.agregarMensajeError("ERROR", "Seleccione el Tipo de Documento");
        return;
        }
        else{
        set_actualizar_cliente.getTab_seleccion().setSql(ser_clientes.getCliente());
        set_actualizar_cliente.getTab_seleccion().ejecutarSql();
        set_actualizar_cliente.dibujar();
        }
    }
        public void aceptarCliente() {
        String str_seleccionado = set_actualizar_cliente.getValorSeleccionado();
        System.out.println("Entrar al aceptar" + str_seleccionado);
        if (str_seleccionado != null) {
            //Inserto los clientes seleccionados en la tabla  
            if (tab_registro_ventas.isFilaInsertada() == false) {
            //Controla que si ya esta insertada no vuelva a insertar
                tab_registro_ventas.insertar();
        TablaGenerica secuencial = utilitario.consultar("select ((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1) as maximo,\n" +
                                                       "length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') as longitud,\n" +
                                                       "(case when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 1 then '0000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 2 then '000000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 3 then '00000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 4 then '0000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 5 then '000'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 6 then '00'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "when length(((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)||'') = 7 then '0'||((case when max(cast(numero_secuencial_venta_saven as integer)) is null then  0 else max(cast(numero_secuencial_venta_saven as integer)) end) + 1)\n" +
                                                       "end) as numero from saes_venta");
           tab_registro_ventas.setValor("numero_secuencial_venta_saven", secuencial.getValor("numero"));

            }
            tab_registro_ventas.setValor("ide_sacli", str_seleccionado);
            tab_registro_ventas.modificar(tab_registro_ventas.getFilaActual());//para que haga el update
            tab_registro_ventas.setValor("ide_satido", com_tipo_documento.getValue().toString());
            set_actualizar_cliente.cerrar();
                   

       utilitario.addUpdateTabla(tab_registro_ventas, "ide_satido", "");
            utilitario.addUpdate("tab_registro_ventas");
        } else {
            utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
        }
    }
    public void abrirDialogoCliente() {
    if(com_tipo_documento.getValue() == null){
            utilitario.agregarMensajeError("ERROR", "Seleccione el Tipo de Documento");
    return;
     }
    else {
            
            crear_cliente_dialogo.dibujar(); 
            tab_cliente.dibujar();
            tab_cliente.limpiar();
            tab_cliente.insertar();
         }
     }
    @Override
    public void abrirListaReportes() {
   // TODO Auto-generated method stub
   rep_reporte.dibujar();
    }
    Map parametro = new HashMap();

    @Override
    public void aceptarReporte() {
    if (rep_reporte.getReporteSelecionado().equals("Comprobante de Pago Nota de Venta")){
                
        if(rep_reporte.isVisible()){
                rep_reporte.cerrar();
                parametro = new HashMap();
                parametro.put("pide_venta", Integer.parseInt(tab_registro_ventas.getValorSeleccionado()));
                sel_rep.setSeleccionFormatoReporte(parametro, rep_reporte.getPath());
                sel_rep.dibujar();
                }
        else{
            utilitario.agregarMensajeInfo("No se puede continuar", "No ha selccionado ningun registro");
        }
      }
    }
    
    public Tabla getTab_registro_ventas() {
        return tab_registro_ventas;
    }

    public void setTab_registro_ventas(Tabla tab_registro_ventas) {
        this.tab_registro_ventas = tab_registro_ventas;
    }

    public Tabla getTab_detalle() {
        return tab_detalle;
    }

    public void setTab_detalle(Tabla tab_detalle) {
        this.tab_detalle = tab_detalle;
    }

    public AutoCompletar getAut_valor() {
        return aut_valor;
    }

    public void setAut_valor(AutoCompletar aut_valor) {
        this.aut_valor = aut_valor;
    }

    public double getDou_total() {
        return dou_total;
    }

    public void setDou_total(double dou_total) {
        this.dou_total = dou_total;
    }

    public double getDou_base_aprobada() {
        return dou_base_aprobada;
    }

    public void setDou_base_aprobada(double dou_base_aprobada) {
        this.dou_base_aprobada = dou_base_aprobada;
    }

    public Combo getCom_tipo_documento() {
        return com_tipo_documento;
    }

    public void setCom_tipo_documento(Combo com_tipo_documento) {
        this.com_tipo_documento = com_tipo_documento;
    }

    public SeleccionTabla getSet_actualizar_cliente() {
        return set_actualizar_cliente;
    }

    public void setSet_actualizar_cliente(SeleccionTabla set_actualizar_cliente) {
        this.set_actualizar_cliente = set_actualizar_cliente;
    }

    public SeleccionTabla getSet_crear_cliente() {
        return set_crear_cliente;
    }

    public void setSet_crear_cliente(SeleccionTabla set_crear_cliente) {
        this.set_crear_cliente = set_crear_cliente;
    }

    public Dialogo getCrear_cliente_dialogo() {
        return crear_cliente_dialogo;
    }

    public void setCrear_cliente_dialogo(Dialogo crear_cliente_dialogo) {
        this.crear_cliente_dialogo = crear_cliente_dialogo;
    }

    public Tabla getTab_cliente() {
        return tab_cliente;
    }

    public void setTab_cliente(Tabla tab_cliente) {
        this.tab_cliente = tab_cliente;
    }

    public SeleccionTabla getSet_facturar_cita() {
        return set_facturar_cita;
    }

    public void setSet_facturar_cita(SeleccionTabla set_facturar_cita) {
        this.set_facturar_cita = set_facturar_cita;
    }

    public Dialogo getDia_periodo() {
        return dia_periodo;
    }

    public void setDia_periodo(Dialogo dia_periodo) {
        this.dia_periodo = dia_periodo;
    }

    public Combo getCom_dia_periodo() {
        return com_dia_periodo;
    }

    public void setCom_dia_periodo(Combo com_dia_periodo) {
        this.com_dia_periodo = com_dia_periodo;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public boolean isBoo_documento_valido() {
        return boo_documento_valido;
    }

    public void setBoo_documento_valido(boolean boo_documento_valido) {
        this.boo_documento_valido = boo_documento_valido;
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

    public Map getParametro() {
        return parametro;
    }

    public void setParametro(Map parametro) {
        this.parametro = parametro;
    }
    
}
