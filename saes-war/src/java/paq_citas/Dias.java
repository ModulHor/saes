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

public class Dias extends Pantalla{
    private Tabla tab_dias =  new Tabla ();
    
    public Dias(){
         tab_dias.setId("tab_dias");   //identificador
         tab_dias.setTabla("saes_dias", "ide_sadias", 1);
         tab_dias.getColumna("ide_sadias").setNombreVisual("CODIGO");
         tab_dias.getColumna("descripcion_sadias").setNombreVisual("DESCRIPCION DIA");
         tab_dias.getColumna("laboral_sadias").setNombreVisual("DIA LABORABLE");
         tab_dias.getColumna("orden_sadias").setNombreVisual("ORDEN");
         tab_dias.dibujar();
         
          PanelTabla pat_dias = new PanelTabla();
          pat_dias.setId("pat_dias");
          pat_dias.setPanelTabla(tab_dias);
          Division div_dias = new Division();
          div_dias.setId("div_dias");
          div_dias.dividir1(pat_dias);
          agregarComponente(div_dias);
    }
     @Override
    public void insertar() {
        tab_dias.insertar();
    }

    @Override
    public void guardar() {
        tab_dias.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_dias.eliminar();
    }

    public Tabla getTab_dias() {
        return tab_dias;
    }

    public void setTab_dias(Tabla tab_dias) {
        this.tab_dias = tab_dias;
    }
    
}
