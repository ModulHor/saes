
package paq_empleados.ejb;

/**
 *
 * @author Andres
 */
import framework.aplicacion.TablaGenerica;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
public class ServiciosEmpleados {
    /**
     * Retorna el periodo academico vigente
     *
     * @param activo.- permite el ingreso del paramtero activo para filtrar ya sea true, false, o ambos.
     * @return sql del periodo academico
     */
    public String getTipoDescuento(String activo) {
        String sql="";
        sql="select ide_satid, nombre_satid from saes_tipo_descuento where activo_satid  in ("+activo+") ";
        return sql;
    }
        public String getEmpleado(String activo) {
        String sql="";
        sql="select ide_saemp, nombres_saemp, apellidos_saemp from saes_empleado where activo_saemp in ("+activo+") ";
        return sql;
    }
        public String getEmpleadosGeneral() {
        String sql="";
        sql="select ide_saemp, ci_dni_saemp, apellidos_saemp, nombres_saemp from saes_empleado order by apellidos_saemp";
        return sql;
    }
        public String getDocumentoIdentidad() {
        String sql="";
        sql="select ide_satiden, descripcion_satiden from saes_tipo_doc_iden order by descripcion_satiden";
        return sql;
    }
        public String getCargo() {
        String sql="";
        sql="select IDE_SACARG, descripcion_sacarg from SAES_CARGO order by descripcion_sacarg";
        return sql;
    }
        
    public String getEmpleadoTabla(String activo) {
        String sql="";
        sql="select ide_saemp, ci_dni_saemp,apellidos_saemp||' '||nombres_saemp as empleado from saes_empleado a\n" +
            "left join saes_cargo b on a.ide_sacarg = b.ide_sacarg where activo_saemp in ("+activo+")\n" +
            "order by empleado";
        return sql;
    }
    public String getEstadoCivil(String activo) {
        String sql="";
        sql="select ide_saesci, descripcion_saesci from saes_estado_civil where activo_saesci in ("+activo+")";
        return sql;
    }
    public String getTipoDiscapacidad(String activo) {
        String sql="";
        sql="select ide_satidi, descripcion_satidi from saes_tipo_discapacidad where activo_satidi in ("+activo+")";
        return sql;
    }
    
    public String getTipoArchivo(String activo) {
        String sql="";
        sql="select ide_satiar, descripcion_satiar from saes_tipo_archivo where activo_satiar in ("+activo+")";
        return sql;
    }
    
    public String getTipoDocumentosEmpleado(String activo) {
        String sql="";
        sql="select ide_satidem, descripcion_satidem from saes_tipo_doc_empleado where activo_satidem in ("+activo+")";
        return sql;
    }
    public String getNivelEducacion() {
        String sql="";
        sql="select ide_sanied, descripcion_sanied from saes_nivel_educacion order by orden_sanied";
        return sql;
    }   
    public String getEspecialidad() {
        String sql="";
        sql="select ide_saespe, descripcion_saespe from saes_especialidad order by descripcion_saespe";
        return sql;
    }
    public String getTituloObtenido() {
        String sql="";
        sql="select ide_satiob, descripcion_satiob from saes_titulo_obtenido order by descripcion_satiob";
        return sql;
    }
    public String getTipoCuenta() {
        String sql="";
        sql="select * from saes_tipo_cuenta";
        return sql;
    }
    public String getTipoEntidadFinanciera() {
        String sql="";
        sql="select * from saes_tipo_entidad_financie";
        return sql;
    }
    public String getInstitucionFinanciera() {
        String sql="";
        sql="select ide_saenfi, descripcion_saenfi from saes_entidad_financiera";
        return sql;
    }
    public String getParentezco() {
        String sql="";
        sql="select * from saes_parentezco_emple order by ide_saparem";
        return sql;
    }
}
