/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_empleados;

import paq_ventas.*;
import framework.componentes.AutoCompletar;
import framework.componentes.Boton;
import framework.componentes.Calendario;
import framework.componentes.Combo;
import framework.componentes.Espacio;
import framework.componentes.Etiqueta;
import framework.componentes.Grid;
import framework.componentes.Imagen;
import framework.componentes.Panel;
import framework.componentes.Reporte;
import framework.componentes.SeleccionFormatoReporte;
import framework.componentes.Tabla;
import framework.componentes.VisualizarPDF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import paq_empleados.ejb.ServiciosEmpleados;
import sistema.aplicacion.Pantalla;
import persistencia.Conexion;

/**
 *
 * @author Andres
 */
public class RolPagos extends Pantalla{
    private Calendario fechaInicio = new Calendario();
    private Calendario fechaFin = new Calendario();
    private Panel panOpcion = new Panel();
    private Map p_parametros = new HashMap();
    private AutoCompletar aut_empleado = new AutoCompletar();
    private VisualizarPDF vipdf_comprobante = new VisualizarPDF();
    
    @EJB
    private final ServiciosEmpleados ser_empleados = (ServiciosEmpleados) utilitario.instanciarEJB(ServiciosEmpleados.class);
    
    public RolPagos(){
        Panel tabp2 = new Panel();
        tabp2.setStyle("font-size:19px;color:black;text-align:center;");
        tabp2.setHeader("ROL DE PAGOS INDIVIDUAL EMPLEADOS");
        panOpcion.setId("panOpcion");
        panOpcion.setStyle("font-size:13px;color:black;text-align:left;");
        panOpcion.setTransient(true);
        panOpcion.setHeader("SELECCIONE PARAMETROS DE REPORTE");
        
        Imagen quinde = new Imagen();
        quinde.setStyle("text-align:center;position:absolute;top:160px;left:500px;");
        quinde.setValue("imagenes/ima_empresa.png");
        panOpcion.getChildren().add(quinde);
        
        Espacio esp = new Espacio();
        esp.setEspacio("10", "30");
        panOpcion.getChildren().add(esp);
        
        Grid griCuerpo = new Grid();
        griCuerpo.setColumns(2);
        
        
        
        griCuerpo.getChildren().add(new Etiqueta("FECHA INICIAL: "));
        fechaInicio.setId("fechaInicio");
        fechaInicio.setFechaActual();
        fechaInicio.setTipoBoton(true);
        griCuerpo.getChildren().add(fechaInicio);

        griCuerpo.getChildren().add(new Etiqueta("FECHA FINAL: "));
        fechaFin.setId("fechaFin");
        fechaFin.setFechaActual();
        fechaFin.setTipoBoton(true);
        griCuerpo.getChildren().add(fechaFin);
        
        
        griCuerpo.getChildren().add(new Etiqueta("EMPLEADO: "));
        aut_empleado.setId("aut_empleado");
        aut_empleado.setAutoCompletar(ser_empleados.getEmpleado("true"));
        aut_empleado.setSize(45);
        griCuerpo.getChildren().add(aut_empleado);
        
        Espacio esp2 = new Espacio();
        esp2.setEspacio("10", "30");
        griCuerpo.getChildren().add(esp2);
        
        panOpcion.getChildren().add(griCuerpo);
        
        Espacio esp3 = new Espacio();
        esp3.setEspacio("10", "40");
        
        Boton botPrint = new Boton();
        botPrint.setId("botPrint");
        botPrint.setValue("IMPRIMIR ROL DE PAGOS");
        botPrint.setIcon("ui-icon-print");
        botPrint.setMetodo("imprimirRol");

        tabp2.getChildren().add(panOpcion);
        tabp2.getChildren().add(esp3);
        
        tabp2.getChildren().add(botPrint);
        agregarComponente(tabp2);
        
        vipdf_comprobante.setId("vipdf_comprobante");
        vipdf_comprobante.setTitle("ROL DE PAGOS INDIVIDUAL");
        agregarComponente(vipdf_comprobante);
    }
    @Override
    public void insertar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void imprimirRol(){
        if (aut_empleado.getValor() != null) {
                        ///////////AQUI ABRE EL REPORTE
                       
                        p_parametros.put("pide_fechai", fechaInicio.getFecha() + "");
                        p_parametros.put("pide_fechaf", fechaFin.getFecha() + "");
                        p_parametros.put("pide_empleado",Integer.parseInt(aut_empleado.getValor()));
                      
                        //System.out.println(" " + str_titulos);
                        vipdf_comprobante.setVisualizarPDF("rep_empleados/rep_empleado_rol_individual.jasper", p_parametros);
                        vipdf_comprobante.dibujar();
                        utilitario.addUpdate("vipdf_comprobante");
        } else {
            utilitario.agregarMensajeInfo("Seleccione los paràmetros", "");
        }
    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Calendario getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Calendario fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendario getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendario fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Panel getPanOpcion() {
        return panOpcion;
    }

    public void setPanOpcion(Panel panOpcion) {
        this.panOpcion = panOpcion;
    }

    public Map getP_parametros() {
        return p_parametros;
    }

    public void setP_parametros(Map p_parametros) {
        this.p_parametros = p_parametros;
    }

    public AutoCompletar getAut_empleado() {
        return aut_empleado;
    }

    public void setAut_empleado(AutoCompletar aut_empleado) {
        this.aut_empleado = aut_empleado;
    }

    public VisualizarPDF getVipdf_comprobante() {
        return vipdf_comprobante;
    }

    public void setVipdf_comprobante(VisualizarPDF vipdf_comprobante) {
        this.vipdf_comprobante = vipdf_comprobante;
    }
    
}
