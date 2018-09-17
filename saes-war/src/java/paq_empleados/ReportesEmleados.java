/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_empleados;

/**
 *
 * @author Andres
 */

import com.lowagie.text.xml.xmp.LangAlt;
import framework.aplicacion.TablaGenerica;
import framework.componentes.Boton;
import framework.componentes.Combo;
import framework.componentes.Dialogo;
import framework.componentes.Division;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.Grupo;
import framework.componentes.Imagen;
import framework.componentes.PanelTabla;
import framework.componentes.Radio;
import framework.componentes.Reporte;
import framework.componentes.SeleccionCalendario;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.SeleccionTabla;
import framework.componentes.Tabla;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import sistema.aplicacion.Pantalla;
import framework.componentes.Panel;
import paq_citas.ejb.ServiciosCitas;
import paq_empleados.ejb.ServiciosEmpleados;

public class ReportesEmleados extends Pantalla{
    private Reporte rep_reporte=new Reporte();
    private SeleccionFormatoReporte sel_rep=new SeleccionFormatoReporte();  
    private SeleccionCalendario sel_fechas= new SeleccionCalendario();
    private Dialogo dia_tipo = new Dialogo();
    private Radio rad_tipo_justificacion = new Radio();
    private SeleccionTabla sel_tab_empleado = new SeleccionTabla();
    private SeleccionTabla sel_tab_periodo = new SeleccionTabla();
    private Panel panOpcion = new Panel();
    String fechai="";
    String fechaf="";
    String str_periodo="";
    String emple="";
    
    private Dialogo dia_periodo = new Dialogo();
    private Combo com_dia_periodo = new Combo();
    
    
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
    @EJB
    private final ServiciosCitas ser_citas = (ServiciosCitas) utilitario.instanciarEJB(ServiciosCitas.class);
    
    public ReportesEmleados(){
        bar_botones.getBot_insertar().setRendered(false);
        bar_botones.getBot_guardar().setRendered(false);
        bar_botones.getBot_eliminar().setRendered(false);
        bar_botones.quitarBotonsNavegacion();
        
        rep_reporte.setId("rep_reporte");
       rep_reporte.getBot_aceptar().setMetodo("aceptarReporte");
       agregarComponente(rep_reporte);
       bar_botones.agregarReporte();
       sel_rep.setId("sel_rep");
       agregarComponente(sel_rep); 
       
       sel_fechas.setId("sec_rango_fechas");
       sel_fechas.getBot_aceptar().setMetodo("aceptarReporte");
       sel_fechas.setFechaActual();
       agregarComponente(sel_fechas);
       
       //Diseño de fondo reporte asistencia//
        Imagen ImaReportes = new Imagen();
        ImaReportes.setValue("imagenes/repor_saes.png");
        
        panOpcion.setId("pan_opcion");
        panOpcion.setTransient(true);
        panOpcion.setHeader("REPORTES EMPLEADOS");
        panOpcion.setStyle("font-size:18px;color:black;text-align:center;");
        
        Grid grid_pant = new Grid();
        grid_pant.setColumns(1);
        grid_pant.setStyle("text-align:center;position:absolute;top:150px;left:515px;");
        Etiqueta eti_encab = new Etiqueta();
        grid_pant.getChildren().add(ImaReportes);
        Boton bot_imprimir = new Boton();
        bot_imprimir.setValue("Imprimir Reporte");
        bot_imprimir.setIcon("ui-icon-print");
        bot_imprimir.setMetodo("abrirListaReportes");
        //bar_botones.agregarBoton(bot_imprimir);
        grid_pant.getChildren().add(bot_imprimir);
        agregarComponente(grid_pant);
        panOpcion.getChildren().add(grid_pant);
        agregarComponente(panOpcion);
        
        sel_tab_empleado.setId("sel_tab_empleado");
        sel_tab_empleado.setTitle("SELECCIONE EL EMPLEADO");
        sel_tab_empleado.setSeleccionTabla(ser_empleados.getEmpleadoTabla("true"), "ide_saemp");
        //select ide_yhodia, descripcion_yhodia from yavirac_hora_dia order by orden_yhodia asc", "ide_yhodia
        //set_tab_dias.getTab_seleccion().getColumna("descripcion_ystjor").setNombreVisual("Jornada");
        sel_tab_empleado.setWidth("40%");
        sel_tab_empleado.setHeight("50%");
        sel_tab_empleado.setRadio();
        sel_tab_empleado.getBot_aceptar().setMetodo("aceptarReporte");
        agregarComponente(sel_tab_empleado);
        
        // creo dialogo para seleccionar el periodo
        dia_periodo.setId("dia_periodo");
        dia_periodo.setTitle("PERIODO");
        dia_periodo.setWidth("40%");
        dia_periodo.setHeight("18%");
        dia_periodo.getBot_aceptar().setMetodo("aceptarReporte");
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
        
       /* sel_tab_periodo.setId("sel_tab_periodo");
        sel_tab_periodo.setTitle("SELECCIONE EL PERIODO");
        sel_tab_periodo.setSeleccionTabla(ser_citas.getPeriodo("true"),"ide_saperi");
        //select ide_yhodia, descripcion_yhodia from yavirac_hora_dia order by orden_yhodia asc", "ide_yhodia
        //set_tab_dias.getTab_seleccion().getColumna("descripcion_ystjor").setNombreVisual("Jornada");
        sel_tab_periodo.setWidth("40%");
        sel_tab_periodo.setHeight("40%");
        sel_tab_periodo.setRadio();
        sel_tab_periodo.getBot_aceptar().setMetodo("aceptarReporte");
        agregarComponente(sel_tab_periodo);*/
        
    }
    @Override
    public void abrirListaReportes() {
   // TODO Auto-generated method stub
   rep_reporte.dibujar();
    }
    Map parametro = new HashMap();

    @Override
    public void aceptarReporte() {
    if (rep_reporte.getReporteSelecionado().equals("Datos Generales Empleados")){
                
        if(rep_reporte.isVisible()){
                rep_reporte.cerrar();
                parametro = new HashMap();
                parametro.put("titulo", "Reportes");
                sel_rep.setSeleccionFormatoReporte(parametro, rep_reporte.getPath());
                sel_rep.dibujar();
                }
        else{
            utilitario.agregarMensajeInfo("No se puede continuar", "No ha selccionado ningun registro");
        }
    }
     if (rep_reporte.getReporteSelecionado().equals("Rol Empleado Individual")){
         if(rep_reporte.isVisible()){
                rep_reporte.cerrar();
                sel_fechas.dibujar();
        }
         else if (sel_fechas.isVisible()){
                
                fechai=sel_fechas.getFecha1String();
                fechaf=sel_fechas.getFecha2String();
                sel_fechas.cerrar();
                sel_tab_empleado.dibujar();
        }
         else if (sel_tab_empleado.isVisible()){
             if (sel_tab_empleado.getValorSeleccionado()!= null && sel_tab_empleado.getValorSeleccionado().isEmpty() == false) {
                //String str_seleccionado = sel_tab_empleado.getValorSeleccionado();
              //  System.out.println("entre a ver el id"+str_seleccionado);
              //  if (str_seleccionado != null) {
                parametro = new HashMap();
                
                parametro.put("pide_fechai",fechai );
                parametro.put("pide_fechaf", fechaf);
               // parametro.put("pide_empleado", Long.parseLong(sel_tab_empleado.getValorSeleccionado()));
              //  parametro.put("pide_empleado", str_seleccionado);
                parametro.put("pide_empleado",Integer.parseInt(sel_tab_empleado.getValorSeleccionado()));
                
                sel_rep.setSeleccionFormatoReporte(parametro, rep_reporte.getPath());
                sel_tab_empleado.cerrar();
                sel_rep.dibujar();    
                //}
               /* else {
                    utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
                }*/
                } else {
                    utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
                }
        }
         else{
            utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ningun registro");
        }
     }
     if (rep_reporte.getReporteSelecionado().equals("Ganancias Empresa Empleados General")){
         if(rep_reporte.isVisible()){
                rep_reporte.cerrar();
                sel_fechas.dibujar();
        }
         else if (sel_fechas.isVisible()){
                
                fechai=sel_fechas.getFecha1String();
                fechaf=sel_fechas.getFecha2String();
                sel_fechas.cerrar();
                parametro = new HashMap();
                parametro.put("pide_fechai",fechai );
                parametro.put("pide_fechaf", fechaf);
                sel_rep.setSeleccionFormatoReporte(parametro, rep_reporte.getPath());
                sel_rep.dibujar(); 
        }
         else {
                    utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
                }
     }
     else{
            utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ningun registro");
        }
     if (rep_reporte.getReporteSelecionado().equals("Descuentos no Cancelados Empleado")){
         if(rep_reporte.isVisible()){
                rep_reporte.cerrar();
                sel_fechas.dibujar();
        }
         else if (sel_fechas.isVisible()){
              fechai=sel_fechas.getFecha1String();
              fechaf=sel_fechas.getFecha2String();
              sel_fechas.cerrar();
              sel_tab_empleado.dibujar();
         }
         else if (sel_tab_empleado.isVisible()){
             if (sel_tab_empleado.getValorSeleccionado()!= null && sel_tab_empleado.getValorSeleccionado().isEmpty() == false) {
                 emple = sel_tab_empleado.getValorSeleccionado();
                 sel_tab_empleado.cerrar();
                 dia_periodo.dibujar();
             }
         }
        else if (dia_periodo.isVisible()){
                
               
                parametro = new HashMap();
                
                parametro.put("pide_periodo",Integer.parseInt(com_dia_periodo.getValue() + ""));
                System.out.println("pide_periodo "+com_dia_periodo.getValue() + "");
                parametro.put("pide_empleado",Integer.parseInt(emple));
                System.out.println("pide_empleado "+emple);
                System.out.println("fechai "+fechai);
                System.out.println("fechaf "+fechaf);
                parametro.put("pide_fechai",fechai);
                parametro.put("pide_fechaf", fechaf);
                sel_rep.setSeleccionFormatoReporte(parametro, rep_reporte.getPath());
                dia_periodo.cerrar();
                sel_rep.dibujar();
                
       }
         else {
                    utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
                }
    }
     else{
            utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ningun registro");
        }
     if (rep_reporte.getReporteSelecionado().equals("Ganancias de Empleados Generales")){
         if(rep_reporte.isVisible()){
                rep_reporte.cerrar();
                sel_fechas.dibujar();
        }
         else if (sel_fechas.isVisible()){
                
                fechai=sel_fechas.getFecha1String();
                fechaf=sel_fechas.getFecha2String();
                sel_fechas.cerrar();
                parametro = new HashMap();
                parametro.put("pide_fechai",fechai );
                parametro.put("pide_fechaf", fechaf);
                sel_rep.setSeleccionFormatoReporte(parametro, rep_reporte.getPath());
                sel_rep.dibujar(); 
        }
         else {
                    utilitario.agregarMensajeInfo("Debe seleccionar al menos un registro", "");
                }
     }
     else{
            utilitario.agregarMensajeInfo("No se puede continuar", "No ha seleccionado ningun registro");
        }
    }
    @Override
    public void insertar() {
    }

    @Override
    public void guardar() {

    }

    @Override
    public void eliminar() {
       
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

    public SeleccionCalendario getSel_fechas() {
        return sel_fechas;
    }

    public void setSel_fechas(SeleccionCalendario sel_fechas) {
        this.sel_fechas = sel_fechas;
    }

    public Dialogo getDia_tipo() {
        return dia_tipo;
    }

    public void setDia_tipo(Dialogo dia_tipo) {
        this.dia_tipo = dia_tipo;
    }

    public Radio getRad_tipo_justificacion() {
        return rad_tipo_justificacion;
    }

    public void setRad_tipo_justificacion(Radio rad_tipo_justificacion) {
        this.rad_tipo_justificacion = rad_tipo_justificacion;
    }

    public SeleccionTabla getSel_tab_carrera() {
        return sel_tab_empleado;
    }

    public void setSel_tab_carrera(SeleccionTabla sel_tab_carrera) {
        this.sel_tab_empleado = sel_tab_carrera;
    }

    public Panel getPanOpcion() {
        return panOpcion;
    }

    public void setPanOpcion(Panel panOpcion) {
        this.panOpcion = panOpcion;
    }

    public String getFechai() {
        return fechai;
    }

    public void setFechai(String fechai) {
        this.fechai = fechai;
    }

    public String getFechaf() {
        return fechaf;
    }

    public void setFechaf(String fechaf) {
        this.fechaf = fechaf;
    }

    public SeleccionTabla getSel_tab_empleado() {
        return sel_tab_empleado;
    }

    public void setSel_tab_empleado(SeleccionTabla sel_tab_empleado) {
        this.sel_tab_empleado = sel_tab_empleado;
    }

    public String getStr_periodo() {
        return str_periodo;
    }

    public void setStr_periodo(String str_periodo) {
        this.str_periodo = str_periodo;
    }

    public String getEmple() {
        return emple;
    }

    public void setEmple(String emple) {
        this.emple = emple;
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

    public Map getParametro() {
        return parametro;
    }

    public void setParametro(Map parametro) {
        this.parametro = parametro;
    }

    public SeleccionTabla getSel_tab_periodo() {
        return sel_tab_periodo;
    }

    public void setSel_tab_periodo(SeleccionTabla sel_tab_periodo) {
        this.sel_tab_periodo = sel_tab_periodo;
    }
    
}
