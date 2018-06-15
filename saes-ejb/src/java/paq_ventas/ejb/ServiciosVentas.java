/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_ventas.ejb;

/**
 *
 * @author Andres
 */
import framework.aplicacion.TablaGenerica;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless

public class ServiciosVentas {
     /**
     * Retorna el periodo academico vigente
     *
     * @param activo.- permite el ingreso del paramtero activo para filtrar ya sea true, false, o ambos.
     * @return sql del periodo academico
     */
    public String getTipoDocumento() {
        String sql="";
        sql="SELECT ide_satido, descripcion_satido FROM saes_tipo_documento ";
        return sql;
    }
    public String getFormaPago() {
        String sql="";
        sql="SELECT ide_safopa, descripcion_safopa from saes_forma_pago order by descripcion_safopa ";
        return sql;
    }
}
