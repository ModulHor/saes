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
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import paq_citas.ejb.ServiciosCitas;
import sistema.aplicacion.Pantalla;
public class Periodo extends Pantalla{
    private Tabla tab_periodo =  new Tabla ();
    
    @EJB
    private final ServiciosCitas ser_citas = (ServiciosCitas) utilitario.instanciarEJB(ServiciosCitas.class);
    public Periodo(){
         tab_periodo.setId("tab_periodo");   //identificador
         tab_periodo.setTabla("saes_periodo", "ide_saperi", 1);
         tab_periodo.getColumna("ide_saanio").setCombo(ser_citas.getAño("true"));
         tab_periodo.getColumna("ide_sames").setCombo(ser_citas.getMes("true"));
         tab_periodo.getColumna("ide_saperi").setNombreVisual("CODIGO");
         tab_periodo.getColumna("ide_saanio").setNombreVisual("AÑO");
         tab_periodo.getColumna("ide_sames").setNombreVisual("MES");
         tab_periodo.getColumna("activo_saperi").setNombreVisual("ACTIVO");
         
         tab_periodo.dibujar();
         
          PanelTabla pat_periodo = new PanelTabla();
          pat_periodo.setId("pat_periodo");
          pat_periodo.setPanelTabla(tab_periodo);
          Division div_periodo = new Division();
          div_periodo.setId("div_periodo");
          div_periodo.dividir1(pat_periodo);
          agregarComponente(div_periodo);
    }
    @Override
    public void insertar() {
        tab_periodo.insertar();
    }

    @Override
    public void guardar() {
        tab_periodo.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_periodo.eliminar();
    }

    public Tabla getTab_periodo() {
        return tab_periodo;
    }

    public void setTab_periodo(Tabla tab_periodo) {
        this.tab_periodo = tab_periodo;
    }
    
}
