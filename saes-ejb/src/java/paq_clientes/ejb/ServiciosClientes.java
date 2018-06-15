
package paq_clientes.ejb;

/**
 *
 * @author Andres
 */
import framework.aplicacion.TablaGenerica;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
public class ServiciosClientes {
     /**
     * Retorna el periodo academico vigente
     *
     * @param activo.- permite el ingreso del paramtero activo para filtrar ya sea true, false, o ambos.
     * @return sql del periodo academico
     */
    public String getCliente() {
        String sql="";
        sql="SELECT ide_sacli, ci_dni_sacli, apellidos_sacli, nombres_sacli FROM saes_cliente order by apellidos_sacli";
        return sql;
    }
}
