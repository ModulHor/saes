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
import framework.componentes.Division;
import framework.componentes.PanelTabla;
import framework.componentes.Tabla;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sistema.aplicacion.Pantalla;

public class DocumentoIdentidad extends Pantalla {
    
     private Tabla tab_doc_identidad =  new Tabla();
     
     public DocumentoIdentidad (){
          tab_doc_identidad.setId("tab_doc_identidad");   //identificador
         tab_doc_identidad.setTabla("saes_tipo_doc_iden", "ide_satiden", 1);
         tab_doc_identidad.dibujar();
         
          PanelTabla pat_doc_identidad = new PanelTabla();
          pat_doc_identidad.setId("pat_doc_identidad");
          pat_doc_identidad.setPanelTabla(tab_doc_identidad);
          Division div_doc_identidad = new Division();
          div_doc_identidad.setId("div_doc_identidad");
          div_doc_identidad.dividir1(pat_doc_identidad);
          agregarComponente(div_doc_identidad);
     }
    @Override
    public void insertar() {
        tab_doc_identidad.insertar();
    }

    @Override
    public void guardar() {
        tab_doc_identidad.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_doc_identidad.eliminar();
    }

    public Tabla getTab_doc_identidad() {
        return tab_doc_identidad;
    }

    public void setTab_doc_identidad(Tabla tab_doc_identidad) {
        this.tab_doc_identidad = tab_doc_identidad;
    }
        
}
