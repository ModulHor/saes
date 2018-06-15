/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_ventas;

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

public class FormaPago extends Pantalla{
     private Tabla tab_forma_pago =  new Tabla();
     
     public FormaPago (){
          tab_forma_pago.setId("tab_forma_pago");   //identificador
          tab_forma_pago.setTabla("saes_forma_pago", "ide_safopa", 1);
          tab_forma_pago.dibujar();
         
          PanelTabla pat_forma_pago = new PanelTabla();
          pat_forma_pago.setId("pat_tipo_servicios");
          pat_forma_pago.setPanelTabla(tab_forma_pago);
          Division div_forma_pago = new Division();
          div_forma_pago.setId("div_tipo_servicios");
          div_forma_pago.dividir1(pat_forma_pago);
          agregarComponente(div_forma_pago);
     }
      @Override
    public void insertar() {
        tab_forma_pago.insertar();
    }

    @Override
    public void guardar() {
        tab_forma_pago.guardar();
        guardarPantalla();
    }

    @Override
    public void eliminar() {
       tab_forma_pago.eliminar();
    }

    public Tabla getTab_forma_pago() {
        return tab_forma_pago;
    }

    public void setTab_forma_pago(Tabla tab_forma_pago) {
        this.tab_forma_pago = tab_forma_pago;
    }
     
}
