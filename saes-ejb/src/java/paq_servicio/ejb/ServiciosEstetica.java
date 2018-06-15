
package paq_servicio.ejb;

/**
 *
 * @author Andres
 */
import framework.aplicacion.TablaGenerica;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
public class ServiciosEstetica {
    /**
     * Retorna el periodo academico vigente
     *
     * @param activo.- permite el ingreso del paramtero activo para filtrar ya sea true, false, o ambos.
     * @return sql del periodo academico
     */
    public String getTipoServicio(String activo) {
        String sql="";
        sql="SELECT IDE_SATIS, NOMBRE_SATIS FROM SAES_TIPO_SERVICIO  WHERE ACTIVO_SATIS in ("+activo+") order by NOMBRE_SATIS";
        return sql;
    }
    public String getServicio(String activo) {
        String sql="";
        sql="SELECT IDE_SASER, NOMBRE_SASER FROM SAES_SERVICIO WHERE ACTIVO_SASER in ("+activo+") ORDER BY NOMBRE_SASER";
        return sql;
    }
    public String getPrecioServicio() {
        String sql="";
        sql="SELECT IDE_SAPRE, VALOR_SAPRE FROM SAES_PRECIO";
        return sql;
    }
}
