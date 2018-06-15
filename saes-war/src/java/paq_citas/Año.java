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

public class Año extends Pantalla{
    private Tabla tab_anio =  new Tabla ();
    
    public Año(){
        tab_anio.setId("tab_anio");   //identificador
         tab_anio.setTabla("saes_anio", "ide_saanio", 1);
         tab_anio.getColumna("ide_saanio").setNombreVisual("CODIGO");
         tab_anio.getColumna("descripcion_saanio").setNombreVisual("DESCRIPCION AÑO");
         tab_anio.getColumna("activo_saanio").setNombreVisual("ACTIVO");
         tab_anio.dibujar();
         
          PanelTabla pat_anio = new PanelTabla();
          pat_anio.setId("pat_anio");
          pat_anio.setPanelTabla(tab_anio);
          Division div_anio = new Division();
          div_anio.setId("div_anio");
          div_anio.dividir1(pat_anio);
          agregarComponente(div_anio);
    }
    @Override
    public void insertar() {
        tab_anio.insertar();
    }

    @Override
    public void guardar() {
        tab_anio.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_anio.eliminar();
    }

    public Tabla getTab_anio() {
        return tab_anio;
    }

    public void setTab_anio(Tabla tab_anio) {
        this.tab_anio = tab_anio;
    }
    
}
