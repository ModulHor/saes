/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_citas;

/**
 *
 * @author Andres
 */
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.PanelTabla;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import paq_citas.ejb.ServiciosCitas;
import paq_clientes.ejb.ServiciosClientes;
import paq_empleados.ejb.ServiciosEmpleados;
import paq_servicio.ejb.ServiciosEstetica;
import sistema.aplicacion.Pantalla;

public class Citas extends Pantalla{
    private Tabla tab_citas =  new Tabla ();
    private Combo com_periodo = new Combo();
    private SeleccionTabla set_actualizar_cliente = new SeleccionTabla();
    private SeleccionTabla set_crear_cliente = new SeleccionTabla();
    private Dialogo crear_cliente_dialogo = new Dialogo();
    private Tabla tab_cliente = new Tabla();
    private AutoCompletar aut_valor = new AutoCompletar();
    private boolean boo_documento_valido = true;
    
    @EJB
    private final ServiciosCitas ser_citas = (ServiciosCitas) utilitario.instanciarEJB(ServiciosCitas.class);
    
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
    @EJB
    private final ServiciosClientes ser_clientes = (ServiciosClientes) utilitario.instanciarEJB(ServiciosClientes.class);
    
     @EJB
    private final ServiciosEstetica ser_servicios = (ServiciosEstetica) utilitario.instanciarEJB(ServiciosEstetica.class);
    public Citas(){
        
         Etiqueta eti_periodo = new Etiqueta("PERIODO: ");
         bar_botones.agregarComponente(eti_periodo);
          
         com_periodo.setId("com_periodo");
         com_periodo.setCombo(ser_citas.getPeriodo("true"));
         com_periodo.setMetodo("filtroComboPeriodo");
         bar_botones.agregarComponente(com_periodo);
        
         tab_citas.setId("tab_citas");   //identificador
         tab_citas.setTabla("saes_cita", "ide_sacita", 1);
         tab_citas.setCondicion("ide_saperi=-1");
         tab_citas.getColumna("ide_sacli").setCombo(ser_clientes.getCliente());
         tab_citas.getColumna("ide_sacli").setAutoCompletar();
         tab_citas.getColumna("ide_saperi").setVisible(false);
         tab_citas.getColumna("ide_sadias").setCombo(ser_citas.getDias("true"));
         tab_citas.getColumna("ide_saser").setCombo(ser_servicios.getServicio("true"));
         //tab_detalle.getColumna("ide_saser").setMetodoChange("asignarSubtotal");
         tab_citas.getColumna("ide_saser").setAutoCompletar();
         tab_citas.setTipoFormulario(true);
         tab_citas.getGrid().setColumns(2);
         tab_citas.getColumna("ide_sacita").setNombreVisual("CODIGO");
         tab_citas.getColumna("ide_sacli").setNombreVisual("CLIENTE");
         tab_citas.getColumna("ide_saser").setNombreVisual("SERVICIO");
         tab_citas.getColumna("ide_sadias").setNombreVisual("DIA");
         tab_citas.getColumna("fecha_cita_sacita").setNombreVisual("FECHA");
         tab_citas.getColumna("hora_sacita").setNombreVisual("HORA");
         tab_citas.getColumna("observacion_sacita").setNombreVisual("OBSERVACION");
         tab_citas.getColumna("aprobado_sacita").setNombreVisual("APROBADO");
         tab_citas.dibujar();
         
          PanelTabla pat_cita = new PanelTabla();
          pat_cita.setId("pat_cita");
          pat_cita.setPanelTabla(tab_citas);
          Division div_cita = new Division();
          div_cita.setId("div_cita");
          div_cita.dividir1(pat_cita);
          agregarComponente(div_cita);
          
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
          tab_cliente.getColumna("ci_dni_sacli").setNombreVisual("IDENTIFICACION");
          tab_cliente.getColumna("ci_dni_sacli").setUnico(true);
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
          bot_crearCliente.setIcon("ui-icon-person");
          bot_crearCliente.setMetodo("abrirDialogoCliente");
          bar_botones.agregarBoton(bot_crearCliente);
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
    public void filtroComboPeriodo(){
        
        tab_citas.setCondicion("ide_saperi="+com_periodo.getValue().toString());
        tab_citas.ejecutarSql();
        utilitario.addUpdate("tab_citas");
    }
    public void seleccionoAutocompletar(SelectEvent evt){
       //Cuando selecciona una opcion del autocompletar
       aut_valor.onSelect(evt);//siempre debe hacerse el onSelect(evt)
       utilitario.agregarMensaje("VALOR", aut_valor.getValor()); //muestra el valor que selecciono
       utilitario.agregarMensaje("NOMBRE", aut_valor.getValorArreglo(1)); //muestra el nombre que selecciono
    }
    public void abrirDialogoCliente() {
    if(com_periodo.getValue() == null){
            utilitario.agregarMensajeError("ERROR", "Seleccione el Periodo");
    return;
     }
    else {
            
            crear_cliente_dialogo.dibujar(); 
            tab_cliente.dibujar();
            tab_cliente.limpiar();
            tab_cliente.insertar();
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
                if(tab_citas.isFilaInsertada()==false){
                  tab_citas.insertar();
                  tab_citas.setValor("ide_saperi", com_periodo.getValue().toString());
                  utilitario.addUpdateTabla(tab_citas, "ide_saperi", "");
                }
                 tab_citas.getColumna("ide_sacli").actualizarCombo();
                /*
                 if(tab_factura.isFilaInsertada()==false){
                 tab_factura.insertar();	
                 }
                 */
                //CARGA EL CLIENTE Q SE INSERTO
                tab_citas.setValor("ide_sacli", tab_cliente.getValor("ide_sacli"));
                //tab_factura.setValor("ide_fadaf",  aut_factura.getValor());
                System.out.println(" valor del cliente al crear " + tab_cliente.getValor("ide_sacli"));
                utilitario.addUpdate("tab_registro_ventas");
                tab_citas.modificar(tab_citas.getFilaActual());
                    
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
     //ACTUALIZAR CLIENTE
    public void actualizarCliente() {
     if (com_periodo.getValue() == null){
         utilitario.agregarMensajeError("ERROR", "Seleccione el Periodo");
        
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
            if (tab_citas.isFilaInsertada() == false) {
            //Controla que si ya esta insertada no vuelva a insertar
                tab_citas.insertar();
                tab_citas.setValor("ide_saperi", com_periodo.getValue().toString());
                utilitario.addUpdateTabla(tab_citas, "ide_saperi", "");
            }

            tab_citas.setValor("ide_sacli", str_seleccionado);
            tab_citas.modificar(tab_citas.getFilaActual());//para que haga el update

            set_actualizar_cliente.cerrar();
            utilitario.addUpdate("tab_citas");
        } else {
            utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
        }
    }
    
     @Override
    public void insertar() {
        if(com_periodo.getValue() == null){
            utilitario.agregarMensajeError("ERROR", "Seleccione el Periodo");
        return;
        }
        else if (tab_citas.isFocus()){
       tab_citas.insertar();
      
       tab_citas.setValor("ide_saperi", com_periodo.getValue().toString());
       utilitario.addUpdateTabla(tab_citas, "ide_saperi", "");
       
       }
        
    }

    @Override
    public void guardar() {
        tab_citas.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_citas.eliminar();
    }

    public Tabla getTab_citas() {
        return tab_citas;
    }

    public void setTab_citas(Tabla tab_citas) {
        this.tab_citas = tab_citas;
    }

    public Combo getCom_periodo() {
        return com_periodo;
    }

    public void setCom_periodo(Combo com_periodo) {
        this.com_periodo = com_periodo;
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

    public AutoCompletar getAut_valor() {
        return aut_valor;
    }

    public void setAut_valor(AutoCompletar aut_valor) {
        this.aut_valor = aut_valor;
    }
    
}
