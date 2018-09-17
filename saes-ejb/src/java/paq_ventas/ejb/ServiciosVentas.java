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
    public String getSqlFacturas(String tipo_documento) {
        String sql="";
        sql="select ide_saven, ide_satido, numero_secuencial_venta_saven, b.descripcion_safopa, fecha_saven, apellidos_sacli||' '||nombres_sacli as nombre_cliente, iva_fac_saven, total_saven, observacion_saven\n" +
            "from saes_venta a\n" +
            "left join saes_forma_pago b on a.ide_safopa = b.ide_safopa\n" +
            "left join saes_cliente c on a.ide_sacli = c.ide_sacli\n" +
            "where ide_satido = "+tipo_documento+"\n" +
            "order by numero_secuencial_venta_saven desc, fecha_saven desc";
        return sql;
    }
}
