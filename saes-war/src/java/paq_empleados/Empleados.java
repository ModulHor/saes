package paq_empleados;

/**
 *
 * @author Andres
 */
import framework.aplicacion.TablaGenerica;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Division;
import framework.componentes.Efecto;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.Grupo;
import framework.componentes.Imagen;
import framework.componentes.ItemMenu;
import framework.componentes.MenuPanel;
import framework.componentes.PanelTabla;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.Tabla;
import framework.componentes.Tabulador;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelmenu.PanelMenu;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.event.SelectEvent;
import paq_empleados.ejb.ServiciosEmpleados;
import sistema.aplicacion.Pantalla;

public final class Empleados extends Pantalla {
    private Tabla tab_tabla = new Tabla();
    private Tabla tab_empleados =  new Tabla();
    private Tabla tab_discapacidad =  new Tabla();
    private Tabla tab_documentos =  new Tabla();
    private Tabla tab_educacion =  new Tabla();
    private Tabla tab_experiencia =  new Tabla();
    private Tabla tab_cuentas =  new Tabla();
    private Tabla tab_referencias_personales =  new Tabla();
    private Reporte rep_reporte = new Reporte(); //Listado de Reportes, siempre se llama rep_reporte
    private SeleccionFormatoReporte sel_rep = new SeleccionFormatoReporte(); //formato de salida del reporte
    private Map map_parametros = new HashMap();//Parametros del reporte
    private AutoCompletar aut_empleado = new AutoCompletar();
    private boolean boo_documento_valido = true;
    private PanelMenu pam_menu = new PanelMenu();
    private Panel pan_opcion = new Panel();
    private int int_opcion=0;
    private Efecto efecto = new Efecto();
    private String str_opcion = "";// sirve para identificar la opcion que se encuentra dibujada en pantalla
    private MenuPanel menup=new MenuPanel();
    public static String p_discapacidad;
    String t_discapacidad ="";
    String t_especialidad ="";
    String t_titulo ="";
    String t_banco ="";
    String t_cooperativa ="";
    
    
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
    
    public Empleados(){
        
        //p_discapacidad = utilitario.consultar("select ide_satidi, descripcion_satidi from saes_tipo_discapacidad where ide_satidi = 6")
        
        aut_empleado.setId("aut_empleado");
        aut_empleado.setAutoCompletar(ser_empleados.getEmpleadosGeneral());
        aut_empleado.setSize(75);
        aut_empleado.setMetodoChange("seleccionoAutocompletar");
       
        bar_botones.agregarComponente(new Etiqueta("Empleado: "));
        bar_botones.agregarComponente(aut_empleado);  
        
        
        rep_reporte.setId("rep_reporte");
        agregarComponente(rep_reporte);
        bar_botones.agregarReporte();
        
        sel_rep.setId("sel_rep");
        agregarComponente(sel_rep);
        
        Boton bot_clean = new Boton();
        bot_clean.setIcon("ui-icon-cancel");
        bot_clean.setTitle("Limpiar");
        bot_clean.setMetodo("limpiar");
        bar_botones.agregarComponente(bot_clean);
        
        menup.setMenuPanel("FICHA DEL EMPLEADO", "22%");
        menup.setTransient(true);
        menup.agregarItem ("Listado de Empleados", "dibujarDashBoard", "ui-icon-home"); 
        menup.agregarItem ("Datos Personales", "dibujaDatosPersonal", "ui-icon-person"); 
        menup.agregarSubMenu("ESTUDIOS");
        menup.agregarItem ("Datos Acadèmicos", "dibujaDatosEducacion", "ui-icon-calculator");
        menup.agregarSubMenu("EXPERIENCIA LABORAL");
        menup.agregarItem ("Datos Laborales", "dibujaDatosExperiencia", "ui-icon-cart");
        menup.agregarSubMenu("CUENTAS BANCARIAS");
        menup.agregarItem ("Datos Cuentas Bancarias", "dibujaDatosCuentas", "	ui-icon-note");
        menup.agregarSubMenu("DOCUMENTOS EMPLEADO");
        menup.agregarItem ("Documentaciòn", "dibujaDocumentosEmpleados", "ui-icon-document");
        agregarComponente(menup);
        
        //cargarMenu();    
        /*pan_opcion.setId("pan_opcion");
		pan_opcion.setTransient(true);

		efecto.setType("drop");
		efecto.setSpeed(150);
		efecto.setPropiedad("mode", "'show'");
		efecto.setEvent("load");
		pan_opcion.getChildren().add(efecto);
	

	Division div_division = new Division();
	div_division.setId("div_division1");
	div_division.dividir2(pam_menu, pan_opcion, "20%", "V");
	div_division.getDivision1().setCollapsible(true);
	div_division.getDivision1().setHeader("MENU DE OPCIONES");
	agregarComponente(div_division);*/
       dibujarDashBoard();
      }
    @Override
    public void abrirListaReportes() {

        rep_reporte.dibujar();
    }
    @Override
    public void aceptarReporte() {
       if (rep_reporte.getReporteSelecionado().equals("DatosPersonalesEmpleado")){
           rep_reporte.cerrar();
           map_parametros.put("titulo", "Gestion de Empleados");
           sel_rep.setSeleccionFormatoReporte(map_parametros, rep_reporte.getPath());
           sel_rep.dibujar();
          }
    }
    
     public void seleccionoAutocompletar(SelectEvent evt){
         
      aut_empleado.onSelect(evt);
    //menup.limpiar();
    if (aut_empleado.getValor() != null) {
        
        if(menup.getOpcion()==1){
            
            dibujaDatosPersonal();  
            //utilitario.addUpdate("tab_alumno_direccion,tab_tabulador");
        }
        if(menup.getOpcion()==2){
            
            dibujaDatosEducacion();  
            //utilitario.addUpdate("tab_alumno_direccion,tab_tabulador");
        }
        if(menup.getOpcion()==3){
            
            dibujaDatosExperiencia();  
            //utilitario.addUpdate("tab_alumno_direccion,tab_tabulador");
        }
        if(menup.getOpcion()==4){
            
            dibujaDatosCuentas();  
            //utilitario.addUpdate("tab_alumno_direccion,tab_tabulador");
        }
        if(menup.getOpcion()==5){
            
            dibujaDocumentosEmpleados();  
            //utilitario.addUpdate("tab_alumno_direccion,tab_tabulador");
        }
        if(menup.getOpcion()==6){
            
            dibujarDashBoard();  
            //utilitario.addUpdate("tab_alumno_direccion,tab_tabulador");
        }
      }
    else {
           utilitario.agregarMensajeInfo("Seleccionar un Empleado", "Debe seleccionar un Empleado");
        } 
    }
    
    
    public void validar_documento (AjaxBehaviorEvent evt)
    {
        tab_empleados.modificar(evt);
        String cedula = tab_empleados.getValor("CI_DNI_SAEMP");
        String tipo_documento = tab_empleados.getValor("IDE_SATIDEN");
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
    public void cargarMenu(){
        pam_menu.setWidgetVar("100%");
        Submenu sum_empleado = new Submenu();
	sum_empleado.setLabel("FICHA EMPLEADO");
	pam_menu.getChildren().add(sum_empleado);
        
        ItemMenu itm_datos_empl = new ItemMenu();
	itm_datos_empl.setValue("DATOS PERSONALES");
	itm_datos_empl.setIcon("ui-icon-person");
	itm_datos_empl.setMetodo("dibujaDatosPersonal");
	itm_datos_empl.setUpdate("pan_opcion");
	sum_empleado.getChildren().add(itm_datos_empl);
        // agregarComponente(pam_menu);
    }
    
    public void dibujaDatosPersonal(){
        
      int_opcion=1;
      
      tab_empleados =new Tabla();
      tab_empleados.setId("tab_empleados");   //identificador
      tab_empleados.setTabla("saes_empleado", "ide_saemp", 1);
      tab_empleados.setCondicion("ide_saemp="+aut_empleado.getValor());
      List lista = new ArrayList();
      Object fila1[] = {"1", "ECUATORIANO/A"};
        Object fila2[] = {"2","EXTRANJERO/A"};
        lista.add(fila1);
        lista.add(fila2);
      tab_empleados.setTipoFormulario(true);
      tab_empleados.getGrid().setColumns(4);
      tab_empleados.getColumna("nacionalidad_saemp").setCombo(lista); 
      tab_empleados.getColumna("IDE_SATIDEN").setCombo(ser_empleados.getDocumentoIdentidad());
      tab_empleados.getColumna("ide_saemp").setNombreVisual("CODIGO");
      tab_empleados.getColumna("ide_saemp").setOrden(0);
      tab_empleados.getColumna("IDE_SATIDEN").setNombreVisual("TIPO DOCUMENTO");
      tab_empleados.getColumna("IDE_SATIDEN").setMetodoChange("validar_documento");
      tab_empleados.getColumna("CI_DNI_SAEMP").setMetodoChange("validar_documento");
      tab_empleados.getColumna("IDE_SATIDEN").setOrden(1);
      tab_empleados.getColumna("CI_DNI_SAEMP").setNombreVisual("DOCUMENTO IDENTIFICACIÓN");
      tab_empleados.getColumna("CI_DNI_SAEMP").setOrden(2);
      tab_empleados.getColumna("APELLIDOS_SAEMP").setNombreVisual("APELLIDOS");
      tab_empleados.getColumna("FECHA_NACIMIENTO_SAEMP").setOrden(3);
      tab_empleados.getColumna("NOMBRES_SAEMP").setNombreVisual("NOMBRES");
      tab_empleados.getColumna("NOMBRES_SAEMP").setOrden(4);
      tab_empleados.getColumna("FECHA_NACIMIENTO_SAEMP").setNombreVisual("FECHA DE NACIMIENTO");
      tab_empleados.getColumna("APELLIDOS_SAEMP").setOrden(5);
      tab_empleados.getColumna("ide_saesci").setCombo(ser_empleados.getEstadoCivil("true"));
      tab_empleados.getColumna("ide_saesci").setNombreVisual("ESTADO CIVIL");
      tab_empleados.getColumna("ide_saesci").setOrden(6);
      tab_empleados.getColumna("TELEFONO_SAEMP").setNombreVisual("TELEFONO");
      tab_empleados.getColumna("TELEFONO_SAEMP").setOrden(7);
      tab_empleados.getColumna("CELULAR_SAEMP").setNombreVisual("CELULAR");
      tab_empleados.getColumna("CELULAR_SAEMP").setOrden(8);
      tab_empleados.getColumna("DIRECCION_SAEMP").setNombreVisual("DIRECCION");
      tab_empleados.getColumna("DIRECCION_SAEMP").setOrden(9);
      tab_empleados.getColumna("NACIONALIDAD_SAEMP").setNombreVisual("NACIONALIDAD");
      tab_empleados.getColumna("IDE_SACARG").setCombo(ser_empleados.getCargo());
      tab_empleados.getColumna("IDE_SACARG").setNombreVisual("CARGO A OCUPAR");
      tab_empleados.getColumna("IDE_SACARG").setOrden(10);
      tab_empleados.getColumna("FECHA_ENTRADA_SAEMP").setNombreVisual("FECHA DE ENTRADA");
      tab_empleados.getColumna("FECHA_SALIDA_SAEMP").setNombreVisual("FECHA DE SALIDA");
      tab_empleados.getColumna("ACTIVO_SAEMP").setNombreVisual("ACTIVO/A");
      tab_empleados.getColumna("FOTO_SAEMP").setNombreVisual("FOTO");
      tab_empleados.getColumna("FOTO_SAEMP").setUpload();
      tab_empleados.getColumna("FOTO_SAEMP").setImagen("100", "80");
      tab_empleados.setMostrarNumeroRegistros(false);
    //  tab_empleados.agregarRelacion(tab_discapacidad);
   //   tab_empleados.agregarRelacion(tab_documentos);
      
      tab_empleados.dibujar();
      
      PanelTabla pat_panel1 = new PanelTabla();
      pat_panel1.setPanelTabla(tab_empleados);
      pat_panel1.getMenuTabla().getItem_buscar().setRendered(false);
      
       Tabulador tab_tabulador = new Tabulador();
       tab_tabulador.setId("tab_tabulador");
       
       tab_discapacidad = new Tabla();
       tab_discapacidad.setId("tab_discapacidad");   //identificador
       tab_discapacidad.setTabla("SAES_DISCAPACIDAD", "IDE_SADISC", 2);
       tab_discapacidad.setIdCompleto("tab_tabulador:tab_discapacidad");
       tab_discapacidad.setCondicion("ide_saemp="+aut_empleado.getValor());
       tab_discapacidad.setTipoFormulario(true);
       tab_discapacidad.getGrid().setColumns(4);
       tab_discapacidad.getColumna("IDE_SATIDI").setCombo(ser_empleados.getTipoDiscapacidad("true"));
       tab_discapacidad.getColumna("IDE_SADISC").setNombreVisual("CODIGO");
       tab_discapacidad.getColumna("IDE_SAEMP").setVisible(false);
       tab_discapacidad.getColumna("IDE_SATIDI").setNombreVisual("TIPO DISCAPACIDAD");
       tab_discapacidad.getColumna("IDE_SATIDI").setMetodoChange("asignarDiscapacidad");
       tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setNombreVisual("DESCRIBA DISC.");
      // tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setVisible(false);
       tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setLectura(true);
       //tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setEtiqueta();
       tab_discapacidad.getColumna("NRO_CONADIS_SADISC").setNombreVisual("Nº CARNET CONADIS");
       tab_discapacidad.getColumna("PORCENTAJE_SADISC").setNombreVisual("PORCENTAJE DISC.");
       tab_discapacidad.getColumna("ACTIVO_SADISC").setNombreVisual("ACTIVO");
       tab_discapacidad.getColumna("IDE_SADISC").setOrden(0);
       tab_discapacidad.getColumna("IDE_SATIDI").setOrden(1);
       tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setOrden(2);
       tab_discapacidad.getColumna("NRO_CONADIS_SADISC").setOrden(3);
       tab_discapacidad.getColumna("PORCENTAJE_SADISC").setOrden(4);
       tab_discapacidad.getColumna("ACTIVO_SADISC").setOrden(5);
       tab_discapacidad.setMostrarNumeroRegistros(false);
       tab_discapacidad.dibujar();
      
      PanelTabla pat_panel2 = new PanelTabla();
      pat_panel2.setPanelTabla(tab_discapacidad);
      pat_panel2.getMenuTabla().getItem_buscar().setRendered(false);      
      
      tab_referencias_personales = new Tabla();
      tab_referencias_personales.setId("tab_referencias_personales");   //identificador
      tab_referencias_personales.setTabla("saes_referencias_empleado", "ide_sarefem", 3);
      tab_referencias_personales.setIdCompleto("tab_tabulador:tab_referencias_personales");
      tab_referencias_personales.setCondicion("ide_saemp="+aut_empleado.getValor());
      tab_referencias_personales.setTipoFormulario(true);
      tab_referencias_personales.getGrid().setColumns(7);
      tab_referencias_personales.getColumna("ide_saparem").setCombo(ser_empleados.getParentezco());
      
      tab_referencias_personales.getColumna("ide_sarefem").setNombreVisual("CODIGO");
      tab_referencias_personales.getColumna("ide_saparem").setNombreVisual("PARENTEZCO");
      tab_referencias_personales.getColumna("nombres_sarefem").setNombreVisual("NOMBRES");
      tab_referencias_personales.getColumna("telefono_sarefem").setNombreVisual("TELEFONO");
      tab_referencias_personales.getColumna("celular_sarefem").setNombreVisual("CELULAR");
      tab_referencias_personales.getColumna("ide_saemp").setVisible(false);
      tab_referencias_personales.dibujar();
      PanelTabla pat_panel3 = new PanelTabla();
      pat_panel3.setPanelTabla(tab_referencias_personales);
      pat_panel3.getMenuTabla().getItem_buscar().setRendered(false);
      
      
      tab_tabulador.agregarTab("DISCAPACIDAD", pat_panel2);
      tab_tabulador.agregarTab("REFERENCIAS PERSONALES", pat_panel3);
      
      Division div1 = new Division();
    //  div1.setId("div_empleados");
      div1.dividir2(pat_panel1, tab_tabulador, "60%", "H");
     // pan_opcion.setHeader("DATOS EMPLEADOS");
     // pan_opcion.getChildren().add(div1);
      agregarComponente(div1); 
      menup.dibujar(1,"fa fa-child", "Datos personales del empleado", div1, false); 
      
    }
    public void dibujaDatosEducacion (){
        
      int_opcion=2;
      
      tab_educacion.setId("tab_educacion");   //identificador
      tab_educacion.setTabla("saes_educacion_empleado", "ide_saedem", 4);
      //tab_educacion.setIdCompleto("tab_tabulador:tab_educacion");
      tab_educacion.setCondicion("ide_saemp="+aut_empleado.getValor());
      tab_educacion.setTipoFormulario(true);
      tab_educacion.getGrid().setColumns(4);
      tab_educacion.getColumna("ide_saemp").setVisible(false);
      tab_educacion.getColumna("ide_sanied").setCombo(ser_empleados.getNivelEducacion());
      tab_educacion.getColumna("ide_saespe").setCombo(ser_empleados.getEspecialidad());
      tab_educacion.getColumna("ide_satiob").setCombo(ser_empleados.getTituloObtenido());
      tab_educacion.getColumna("ide_saedem").setNombreVisual("CODIGO");
      tab_educacion.getColumna("ide_sanied").setNombreVisual("NIVEL EDUCACION");
      tab_educacion.getColumna("aprobado_nivel_saedem").setNombreVisual("NIVEL APROBADO");
      tab_educacion.getColumna("ide_saespe").setNombreVisual("ESPECIALIDAD");
      tab_educacion.getColumna("describe_especialidad").setNombreVisual("ESPECIFIQUE ESPECIALIDAD");
      tab_educacion.getColumna("describe_especialidad").setLectura(true);
      tab_educacion.getColumna("ide_saespe").setMetodoChange("asignarEspecialidad");
      tab_educacion.getColumna("ide_satiob").setNombreVisual("TITULO OBTENIDO");
      tab_educacion.getColumna("describe_tit_obtenido").setNombreVisual("ESPECIFIQUE TIT. OBTENIDO");
      tab_educacion.getColumna("describe_tit_obtenido").setLectura(true);
      tab_educacion.getColumna("ide_satiob").setMetodoChange("asignarTitulo");
      tab_educacion.getColumna("ano_grado_saedem").setNombreVisual("AÑO FINALIZACION");
      tab_educacion.getColumna("reg_senescyt_saedem").setNombreVisual("REGISTRADO EN SENESCYT");
      tab_educacion.getColumna("observaciones_saedem").setNombreVisual("OBSERVACIONES");
      tab_educacion.dibujar();
      PanelTabla pat_panel4=new PanelTabla();
      pat_panel4.setPanelTabla(tab_educacion);
      
      Division div_division= new Division();
      div_division.dividir1(pat_panel4);//Agrego el Tabulador a una Division
      agregarComponente(div_division);//
      menup.dibujar(2,"fa fa-book", "Datos académicos del empleado",div_division, false);
    }
    
    public void dibujaDatosExperiencia(){
       
       int_opcion=3;
       tab_experiencia = new Tabla();
       tab_experiencia.setId("tab_experiencia");   //identificador
       tab_experiencia.setTabla("saes_experiencia_laboral", "ide_saexpe", 5);
       tab_experiencia.setCondicion("ide_saemp="+aut_empleado.getValor());
       tab_experiencia.setTipoFormulario(true);
       tab_experiencia.getGrid().setColumns(4);
       tab_experiencia.getColumna("ide_saemp").setVisible(false);
       tab_experiencia.getColumna("ide_saexpe").setNombreVisual("CODIGO");
       tab_experiencia.getColumna("ide_saexpe").setOrden(0);
       tab_experiencia.getColumna("institucion_saexpe").setNombreVisual("INTITUCION");
       tab_experiencia.getColumna("institucion_saexpe").setOrden(1);
       tab_experiencia.getColumna("cargo_saexpe").setNombreVisual("CARGO OCUPABA");
       tab_experiencia.getColumna("cargo_saexpe").setOrden(2);
       tab_experiencia.getColumna("area_desempeno_saexpe").setNombreVisual("AREA");
       tab_experiencia.getColumna("area_desempeno_saexpe").setOrden(3);
       tab_experiencia.getColumna("jefe_inmediato_saexpe").setNombreVisual("JEFE INMEDIATO");
       tab_experiencia.getColumna("jefe_inmediato_saexpe").setOrden(4);
       tab_experiencia.getColumna("telefono_saexpe").setNombreVisual("TELEFONO");
       tab_experiencia.getColumna("telefono_saexpe").setOrden(5);
       tab_experiencia.getColumna("fecha_ingreso_saexpe").setNombreVisual("FECHA INGRESO");
       tab_experiencia.getColumna("fecha_ingreso_saexpe").setOrden(6);
       tab_experiencia.getColumna("fecha_salida_saexpe").setNombreVisual("FECHA SALIDA");
       tab_experiencia.getColumna("fecha_ingreso_saexpe").setOrden(7);
       tab_experiencia.dibujar();
       
       PanelTabla pat_panel5=new PanelTabla();
       pat_panel5.setPanelTabla(tab_experiencia);
      
       Division div_division= new Division();
       div_division.dividir1(pat_panel5);//Agrego el Tabulador a una Division
       agregarComponente(div_division);//
       menup.dibujar(3,"fa fa-list-alt", "Experiencia laboral del empleado",div_division, false);
    }
    public void dibujaDatosCuentas(){
        int_opcion=4;
       tab_cuentas = new Tabla();
       tab_cuentas.setId("tab_cuentas");   //identificador
       tab_cuentas.setTabla("saes_cuenta_bancaria", "ide_sacuban", 6);
       tab_cuentas.setCondicion("ide_saemp="+aut_empleado.getValor());
       tab_cuentas.setTipoFormulario(true);
       tab_cuentas.getGrid().setColumns(4);
       tab_cuentas.getColumna("ide_saticu").setCombo(ser_empleados.getTipoCuenta());
       tab_cuentas.getColumna("ide_satefi").setCombo(ser_empleados.getTipoEntidadFinanciera());
       tab_cuentas.getColumna("ide_saenfi").setCombo(ser_empleados.getInstitucionFinanciera());
       tab_cuentas.getColumna("ide_sacuban").setNombreVisual("CODIGO");
       tab_cuentas.getColumna("ide_saemp").setVisible(false);
       tab_cuentas.getColumna("ide_saticu").setNombreVisual("TIPO CUENTA");
       tab_cuentas.getColumna("ide_satefi").setNombreVisual("TIPO ENTIDAD");
       tab_cuentas.getColumna("ide_satefi").setMetodoChange("cargaInstitucionFinanciera");
       tab_cuentas.getColumna("ide_saenfi").setNombreVisual("INSTITUCION FINANCIERA");
       tab_cuentas.getColumna("ide_saenfi").setMetodoChange("asignarOtrasEntidades");
       tab_cuentas.getColumna("describa_entidad_finan").setNombreVisual("DESCRIBA ENT. FINAN.");
       tab_cuentas.getColumna("describa_entidad_finan").setLectura(true);
       tab_cuentas.getColumna("numero_cuenta_sacuban").setNombreVisual("Nº CUENTA");
       tab_cuentas.getColumna("activo_sacuban").setNombreVisual("ACTIVO");
       tab_cuentas.getColumna("ide_sacuban").setOrden(0);
       tab_cuentas.getColumna("ide_saticu").setOrden(1);
       tab_cuentas.getColumna("ide_satefi").setOrden(2);
       tab_cuentas.getColumna("ide_saenfi").setOrden(3);
       tab_cuentas.getColumna("describa_entidad_finan").setOrden(4);
       tab_cuentas.getColumna("numero_cuenta_sacuban").setOrden(5);
       tab_cuentas.getColumna("activo_sacuban").setOrden(6);
       tab_cuentas.dibujar();
       
       PanelTabla pat_panel6=new PanelTabla();
       pat_panel6.setPanelTabla(tab_cuentas);
       Division div_division= new Division();
       div_division.dividir1(pat_panel6);//Agrego el Tabulador a una Division
       agregarComponente(div_division);//
       menup.dibujar(4,"fa fa-money", "Cuentas bancarias del empleado",div_division, false);
    }
    
    public void dibujaDocumentosEmpleados (){
        int_opcion=5;
      tab_documentos = new Tabla();
      tab_documentos.setId("tab_documentos");   //identificador
      tab_documentos.setTabla("saes_documentos_empleado", "ide_sadocu", 7);
     // tab_documentos.setIdCompleto("tab_tabulador:tab_documentos");
      tab_documentos.setCondicion("ide_saemp="+aut_empleado.getValor());
      tab_documentos.setTipoFormulario(true);
      tab_documentos.getGrid().setColumns(7);
      tab_documentos.getColumna("ide_satiar").setCombo(ser_empleados.getTipoArchivo("true"));
      tab_documentos.getColumna("ide_satidem").setCombo(ser_empleados.getTipoDocumentosEmpleado("true"));
      tab_documentos.getColumna("ide_sadocu").setNombreVisual("CODIGO");
      tab_documentos.getColumna("ide_satiar").setNombreVisual("TIPO ARCHIVO");
      tab_documentos.getColumna("ide_satidem").setNombreVisual("TIPO DOCUMENTO");
      tab_documentos.getColumna("detalle_sadocu").setNombreVisual("OBSERVACIONES");
      tab_documentos.getColumna("anexo_sadocu").setNombreVisual("ARCHIVO");
      tab_documentos.getColumna("ide_saemp").setVisible(false);
      tab_documentos.getColumna("anexo_sadocu").setUpload();
      tab_documentos.dibujar();
      PanelTabla pat_panel7 = new PanelTabla();
      pat_panel7.setPanelTabla(tab_documentos);
      pat_panel7.getMenuTabla().getItem_buscar().setRendered(false);
      Division div_division= new Division();
      div_division.dividir1(pat_panel7);//Agrego el Tabulador a una Division
      agregarComponente(div_division);//
      menup.dibujar(5,"fa fa-clipboard", "Documentos personales del empleado",div_division, false);
    }
    
    public void empleado(SelectEvent evt){
    aut_empleado.onSelect(evt);
    if(aut_empleado!=null){
        switch(menup.getOpcion()){
            case 1:
                dibujaDatosPersonal();
            break;
            case 2:
                dibujaDatosEducacion();
            break;
            case 3:
                dibujaDatosExperiencia();
            break;
            case 4:
                dibujaDatosCuentas();
            break;
            case 5:
                dibujaDocumentosEmpleados();
            break;
            case 6:
                dibujarDashBoard();
            break;
            default:
                dibujaDatosPersonal();
        }
      }
    }
    public void dibujarDashBoard(){
        int_opcion = 6;
        tab_tabla = new Tabla();
        tab_tabla.setId("tab_tabla");
        tab_tabla.setNumeroTabla(6);
        tab_tabla.setSql(ser_empleados.getSqlListaEmpleados());
        tab_tabla.setLectura(true);
        tab_tabla.setCampoPrimaria("ide_saemp");
        tab_tabla.setRows(20);
        tab_tabla.getColumna("ide_saemp").setVisible(false);
        tab_tabla.getColumna("ci_dni_saemp").setNombreVisual("IDENTIDICACIÓN");
        tab_tabla.getColumna("ci_dni_saemp").setFiltroContenido();
        tab_tabla.getColumna("apellidos_saemp").setNombreVisual("APELLIDOS");
        tab_tabla.getColumna("apellidos_saemp").setFiltro(true);
        tab_tabla.getColumna("nombres_saemp").setNombreVisual("NOMBRES");
        tab_tabla.getColumna("nombres_saemp").setFiltro(true);
        tab_tabla.getColumna("descripcion_sacarg").setNombreVisual("CARGO");
        tab_tabla.getColumna("telefono_saemp").setNombreVisual("TELÈFONO");
        tab_tabla.getColumna("celular_saemp").setNombreVisual("CELULAR");
        tab_tabla.getColumna("correo_saemp").setNombreVisual("CORREO");
        tab_tabla.getColumna("direccion_saemp").setNombreVisual("DIRECCIÒN");

        tab_tabla.dibujar();

        PanelTabla pat_panel = new PanelTabla();
        pat_panel.setPanelTabla(tab_tabla);

        menup.dibujar(6, "LISTADO DE CLIENTES", pat_panel);
    }
    public void asignarDiscapacidad(AjaxBehaviorEvent evt){
        tab_discapacidad.modificar(evt);
        TablaGenerica ti_discapacidad = utilitario.consultar("select ide_satidi, descripcion_satidi from saes_tipo_discapacidad where ide_satidi = 6");
        t_discapacidad = ti_discapacidad.getValor("ide_satidi");
        
        if (tab_discapacidad.getValor("ide_satidi").equals(t_discapacidad)) {
            //tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setVisible(true);
            tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setLectura(false);
            System.out.println("este es el id"+t_discapacidad);
            utilitario.addUpdateTabla(tab_discapacidad, "DESCRIPCION_OTRA_DIS_SADIC", "");
           // utilitario.addUpdate("tab_discapacidad");
        }
        else {
           // tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setVisible(false);
              tab_discapacidad.getColumna("DESCRIPCION_OTRA_DIS_SADIC").setLectura(true);
              utilitario.addUpdateTabla(tab_discapacidad, "DESCRIPCION_OTRA_DIS_SADIC", "");
             // utilitario.addUpdate("tab_discapacidad");
        }
    }
    public void asignarEspecialidad(AjaxBehaviorEvent evt){
        tab_educacion.modificar(evt);
        TablaGenerica tab_especialidad = utilitario.consultar("select ide_saespe, descripcion_saespe from saes_especialidad where ide_saespe = 7");
        t_especialidad = tab_especialidad.getValor("ide_saespe");
        
        if (tab_educacion.getValor("ide_saespe").equals(t_especialidad)) {
            tab_educacion.getColumna("describe_especialidad").setLectura(false);
           // System.out.println("este es el id"+t_discapacidad);
            utilitario.addUpdateTabla(tab_educacion, "describe_especialidad", "");
        }
        else {
              tab_educacion.getColumna("describe_especialidad").setLectura(true);
              utilitario.addUpdateTabla(tab_educacion, "describe_especialidad", "");
        }
    }
    
    public void asignarTitulo(AjaxBehaviorEvent evt){
        tab_educacion.modificar(evt);
        TablaGenerica tab_titulo = utilitario.consultar("select ide_satiob, descripcion_satiob from saes_titulo_obtenido where ide_satiob = 6");
        t_titulo = tab_titulo.getValor("ide_satiob");
        
        if (tab_educacion.getValor("ide_satiob").equals(t_titulo)) {
            tab_educacion.getColumna("describe_tit_obtenido").setLectura(false);
           // System.out.println("este es el id"+t_discapacidad);
            utilitario.addUpdateTabla(tab_educacion, "describe_tit_obtenido", "");
        }
        else {
              tab_educacion.getColumna("describe_tit_obtenido").setLectura(true);
              utilitario.addUpdateTabla(tab_educacion, "describe_tit_obtenido", "");
        }
    }
    
    public void cargaInstitucionFinanciera(){
        if (tab_cuentas.getTotalFilas()>0){
			tab_cuentas.getColumna("ide_saenfi").setCombo("select ide_saenfi, descripcion_saenfi from saes_entidad_financiera where ide_satefi ="+ tab_cuentas.getValor("ide_satefi")+ " order by descripcion_saenfi");
			utilitario.addUpdateTabla(tab_cuentas, "ide_saenfi", "");
		}
   }
    
    public void asignarOtrasEntidades(AjaxBehaviorEvent evt){
        tab_cuentas.modificar(evt);
        TablaGenerica tab_banco = utilitario.consultar("select ide_saenfi, descripcion_saenfi from saes_entidad_financiera where ide_saenfi = 26 and ide_satefi = 1");
        TablaGenerica tab_cooperativa = utilitario.consultar("select ide_saenfi, descripcion_saenfi from saes_entidad_financiera where ide_satefi = 2 and ide_saenfi = 25");
        t_banco = tab_banco.getValor("ide_saenfi");
        t_cooperativa = tab_cooperativa.getValor("ide_saenfi");
        
        if (tab_cuentas.getValor("ide_saenfi").equals(t_banco)) {
            tab_cuentas.getColumna("describa_entidad_finan").setLectura(false);
           // System.out.println("este es el id"+t_discapacidad);
            utilitario.addUpdateTabla(tab_cuentas, "describa_entidad_finan", "");
                    
        }
        else if (tab_cuentas.getValor("ide_saenfi").equals(t_cooperativa)){
            tab_cuentas.getColumna("describa_entidad_finan").setLectura(false);
           // System.out.println("este es el id"+t_discapacidad);
            utilitario.addUpdateTabla(tab_cuentas, "describa_entidad_finan", "");
        }
        else {
              tab_cuentas.getColumna("describa_entidad_finan").setLectura(true);
              utilitario.addUpdateTabla(tab_cuentas, "describa_entidad_finan", "");
        }
            }
    
    @Override
    public void insertar() {
    if (int_opcion==1) {
       if (tab_empleados.isFocus() ){
           tab_empleados.insertar();
       } else if (tab_discapacidad.isFocus()){
           tab_discapacidad.insertar();
            tab_discapacidad.setValor("ide_saemp", aut_empleado.getValor());
       } else if (tab_referencias_personales.isFocus()){
           tab_referencias_personales.insertar();
           tab_referencias_personales.setValor("ide_saemp", aut_empleado.getValor());
       }
     }
    else if(int_opcion==2){
            tab_educacion.insertar();
            tab_educacion.setValor("ide_saemp", aut_empleado.getValor());
            
           }
    else if(int_opcion==3){
            tab_experiencia.insertar();
            tab_experiencia.setValor("ide_saemp", aut_empleado.getValor());
            
           }
    else if(int_opcion==4){
            tab_cuentas.insertar();
            tab_cuentas.setValor("ide_saemp", aut_empleado.getValor());
            
           }
    else if(int_opcion==5){
            tab_documentos.insertar();
            tab_documentos.setValor("ide_saemp", aut_empleado.getValor());
            
           }
    }
    @Override
    public void guardar() {   
    if (int_opcion==1) {
      if (tab_empleados.isFocus()){
       if (tab_empleados.getValor("IDE_SATIDEN") != null){
        if (boo_documento_valido){
        tab_empleados.guardar();
        aut_empleado.actualizar();
        aut_empleado.setValor(tab_discapacidad.getValor("ide_saemp"));
        utilitario.addUpdate("aut_empleado");
        
        }
        else
        {
            utilitario.agregarMensajeError("Validación", "El documento ingresado no es válido, "
                    + "por favor corrija antes de guardar.");
        }
        }
        else
        {
            utilitario.agregarMensajeError("Información", "Debe seleccionar el tipo de documento, "
                    + "por favor corrija antes de guardar.");
        }
        }
        else if (tab_discapacidad.isFocus()){
            tab_discapacidad.guardar();
            aut_empleado.actualizar();
        }
        else if (tab_referencias_personales.isFocus()){
            tab_referencias_personales.guardar();
            aut_empleado.actualizar();
        }
      
        guardarPantalla();
      }
       else if(int_opcion==2){
            tab_educacion.guardar();
            guardarPantalla();
            aut_empleado.actualizar();
           }
    else if(int_opcion==3){
            tab_experiencia.guardar();
            guardarPantalla();
            aut_empleado.actualizar();
           }
    else if(int_opcion==4){
            tab_cuentas.guardar();
            guardarPantalla();
            aut_empleado.actualizar();
           }
    else if(int_opcion==5){
            tab_documentos.guardar();
            guardarPantalla();
            aut_empleado.actualizar();
           }
    }

    @Override
    public void eliminar() {
    if (int_opcion==1) {
        if (tab_empleados.isFocus()){
            tab_empleados.eliminar();
        }
        else if (tab_discapacidad.isFocus()){
            tab_discapacidad.eliminar();
        } else if (tab_referencias_personales.isFocus()){
            tab_referencias_personales.eliminar();
        }
      }
     else if(int_opcion==2){
            tab_educacion.eliminar();
     }
    else if(int_opcion==3){
            tab_experiencia.eliminar();
     }
    else if(int_opcion==4){
            tab_cuentas.eliminar();
     }
    else if(int_opcion==5){
            tab_documentos.eliminar();
     }
    }
    public void limpiar() {
        aut_empleado.limpiar();
        menup.limpiar();
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
    
    public Tabla getTab_empleados() {
        return tab_empleados;
    }

    public void setTab_empleados(Tabla tab_empleados) {
        this.tab_empleados = tab_empleados;
    }

    public AutoCompletar getAut_empleado() {
        return aut_empleado;
    }

    public void setAut_empleado(AutoCompletar aut_empleado) {
        this.aut_empleado = aut_empleado;
    }

    public Tabla getTab_discapacidad() {
        return tab_discapacidad;
    }

    public void setTab_discapacidad(Tabla tab_discapacidad) {
        this.tab_discapacidad = tab_discapacidad;
    }

    public PanelMenu getPam_menu() {
        return pam_menu;
    }

    public void setPam_menu(PanelMenu pam_menu) {
        this.pam_menu = pam_menu;
    }

    public Panel getPan_opcion() {
        return pan_opcion;
    }

    public void setPan_opcion(Panel pan_opcion) {
        this.pan_opcion = pan_opcion;
    }

    public Efecto getEfecto() {
        return efecto;
    }

    public void setEfecto(Efecto efecto) {
        this.efecto = efecto;
    }

    public String getStr_opcion() {
        return str_opcion;
    }

    public void setStr_opcion(String str_opcion) {
        this.str_opcion = str_opcion;
    }

    public MenuPanel getMenup() {
        return menup;
    }

    public void setMenup(MenuPanel menup) {
        this.menup = menup;
    }

    public Tabla getTab_documentos() {
        return tab_documentos;
    }

    public void setTab_documentos(Tabla tab_documentos) {
        this.tab_documentos = tab_documentos;
    }

    public Tabla getTab_educacion() {
        return tab_educacion;
    }

    public void setTab_educacion(Tabla tab_educacion) {
        this.tab_educacion = tab_educacion;
    }

    public Tabla getTab_experiencia() {
        return tab_experiencia;
    }

    public void setTab_experiencia(Tabla tab_experiencia) {
        this.tab_experiencia = tab_experiencia;
    }

    public Tabla getTab_cuentas() {
        return tab_cuentas;
    }

    public void setTab_cuentas(Tabla tab_cuentas) {
        this.tab_cuentas = tab_cuentas;
    }

    public Tabla getTab_referencias_personales() {
        return tab_referencias_personales;
    }

    public void setTab_referencias_personales(Tabla tab_referencias_personales) {
        this.tab_referencias_personales = tab_referencias_personales;
    }

    public Tabla getTab_tabla() {
        return tab_tabla;
    }

    public void setTab_tabla(Tabla tab_tabla) {
        this.tab_tabla = tab_tabla;
    }
    
}
