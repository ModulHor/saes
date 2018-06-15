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
import sistema.aplicacion.Pantalla;

public class Meses extends Pantalla{
    private Tabla tab_mes =  new Tabla ();
    
    public Meses (){
        tab_mes.setId("tab_mes");   //identificador
         tab_mes.setTabla("saes_mes", "ide_sames", 1);
         tab_mes.getColumna("ide_sames").setNombreVisual("CODIGO");
         tab_mes.getColumna("descripcion_sames").setNombreVisual("DESCRIPCION MES");
         tab_mes.getColumna("activo_sames").setNombreVisual("ACTIVO");
         tab_mes.getColumna("orden_sames").setNombreVisual("ORDEN");
         tab_mes.dibujar();
         
          PanelTabla pat_mes = new PanelTabla();
          pat_mes.setId("pat_mes");
          pat_mes.setPanelTabla(tab_mes);
          Division div_mes = new Division();
          div_mes.setId("div_mes");
          div_mes.dividir1(pat_mes);
          agregarComponente(div_mes);
    }
    @Override
    public void insertar() {
        tab_mes.insertar();
    }

    @Override
    public void guardar() {
        tab_mes.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_mes.eliminar();
    }

    public Tabla getTab_mes() {
        return tab_mes;
    }

    public void setTab_mes(Tabla tab_mes) {
        this.tab_mes = tab_mes;
    }
    
}
