/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_citas;

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
/**
 *
 * @author Andres Redroban
 */

public class ReportesCitas extends Pantalla{
    private Reporte rep_reporte=new Reporte();
    private SeleccionFormatoReporte sel_rep=new SeleccionFormatoReporte();  
    private SeleccionCalendario sel_fechas= new SeleccionCalendario();
    private Dialogo dia_periodo = new Dialogo();
    private Combo com_dia_periodo = new Combo();
    private SeleccionTabla sel_tab_carrera = new SeleccionTabla();
    private Panel panOpcion = new Panel();
    String fechai="";
    String fechaf="";
    String periodo="";
    
     @EJB
    private final ServiciosCitas ser_citas = (ServiciosCitas) utilitario.instanciarEJB(ServiciosCitas.class);
    
    public ReportesCitas(){
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
        panOpcion.setHeader("REPORTES CITAS");
        panOpcion.setStyle("font-size:10px;color:black;text-align:center;");
        
         Grid grid_pant = new Grid();
        grid_pant.setColumns(1);
        grid_pant.setStyle("text-align:center;position:absolute;top:210px;left:535px;");
        Etiqueta eti_encab = new Etiqueta();
        grid_pant.getChildren().add(ImaReportes);
        Boton bot_imprimir = new Boton();
        bot_imprimir.setValue("Imprimir Reporte");
        bot_imprimir.setIcon("ui-icon-print");
        bot_imprimir.setMetodo("abrirListaReportes");
        bar_botones.agregarBoton(bot_imprimir);
        grid_pant.getChildren().add(bot_imprimir);
        agregarComponente(grid_pant);
        panOpcion.getChildren().add(grid_pant);
        agregarComponente(panOpcion);
        
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
    }
    @Override
    public void abrirListaReportes() {
   // TODO Auto-generated method stub
   rep_reporte.dibujar();
    }
    Map parametro = new HashMap();

    @Override
    public void aceptarReporte() {
    if (rep_reporte.getReporteSelecionado().equals("Citas Agendadas")){
        if(rep_reporte.isVisible()){
                rep_reporte.cerrar();
                dia_periodo.dibujar();
        }
        else if(dia_periodo.isVisible()){
            periodo = com_dia_periodo.getValue() + "";
            dia_periodo.cerrar();
            sel_fechas.dibujar();
        }
        else if (sel_fechas.isVisible()){
            if (sel_fechas.isFechasValidas()){
                parametro = new HashMap();
                parametro.put("pide_periodo",Integer.parseInt(periodo) );
                parametro.put("pide_fechai",sel_fechas.getFecha1String() );
                parametro.put("pide_fechaf", sel_fechas.getFecha2String());
                sel_rep.setSeleccionFormatoReporte(parametro, rep_reporte.getPath());
                sel_fechas.cerrar();
                sel_rep.dibujar(); 
            }
            
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

    public SeleccionTabla getSel_tab_carrera() {
        return sel_tab_carrera;
    }

    public void setSel_tab_carrera(SeleccionTabla sel_tab_carrera) {
        this.sel_tab_carrera = sel_tab_carrera;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Map getParametro() {
        return parametro;
    }

    public void setParametro(Map parametro) {
        this.parametro = parametro;
    }
    
}
