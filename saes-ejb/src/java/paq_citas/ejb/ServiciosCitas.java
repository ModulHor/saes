/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paq_citas.ejb;

/**
 *
 * @author Andres
 */
import framework.aplicacion.TablaGenerica;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
public class ServiciosCitas {
    /**
     * Retorna el periodo academico vigente
     *
     * @param activo.- permite el ingreso del paramtero activo para filtrar ya sea true, false, o ambos.
     * @return sql del periodo academico
     */
    public String getAño(String activo) {
        String sql="";
        sql="select ide_saanio, descripcion_saanio from saes_anio where activo_saanio  in ("+activo+") order by descripcion_saanio ";
        return sql;
    }
    public String getMes(String activo) {
        String sql="";
        sql="select ide_sames, descripcion_sames from saes_mes where activo_sames in ("+activo+") order by orden_sames ";
        return sql;
    }
    public String getPeriodo(String activo) {
        String sql="";
        sql="select a.ide_saperi, b.descripcion_sames, c.descripcion_saanio\n" +
            "from saes_periodo a\n" +
            "inner join saes_mes b on a.ide_sames = b.ide_sames\n" +
            "inner join saes_anio c on a.ide_saanio = c.ide_saanio\n" +
            "where a.activo_saperi in ("+activo+") order by orden_sames ";
        return sql;
    }
     public String getDias(String activo) {
        String sql="";
        sql="select ide_sadias, descripcion_sadias from saes_dias where laboral_sadias in ("+activo+") order by orden_sadias ";
        return sql;
    }
     public String getConsultaCitasAprobadas(String activo, String periodo) {
        String sql="";
        sql="select a.ide_sacita, d.descripcion_sadias, a.fecha_cita_sacita, a.hora_sacita, b.cliente, c.nombre_saser, a.observacion_sacita\n" +
            "from saes_cita a\n" +
            "left join (select ide_sacli, ci_dni_sacli,apellidos_sacli||' '||nombres_sacli as cliente from saes_cliente) b on a.ide_sacli = b.ide_sacli\n" +
            "left join saes_servicio c on a.ide_saser = c.ide_saser\n" +
            "left join saes_dias d on a.ide_sadias = d.ide_sadias\n" +
            "left join saes_periodo e on a.ide_saperi = e.ide_saperi\n" +    
            "where a.aprobado_sacita in ("+activo+") and a.ide_saperi in ("+periodo+") order by a.fecha_cita_sacita ";
        return sql;
    }
     public String getCitasAprobadasTabla(String activo, String ide_sacita) {
        String sql="";
        sql="select a.ide_sacita, d.descripcion_sadias, a.fecha_cita_sacita, a.hora_sacita, b.cliente, c.nombre_saser, a.observacion_sacita\n" +
            "from saes_cita a\n" +
            "left join (select ide_sacli, ci_dni_sacli,apellidos_sacli||' '||nombres_sacli as cliente from saes_cliente) b on a.ide_sacli = b.ide_sacli\n" +
            "left join saes_servicio c on a.ide_saser = c.ide_saser\n" +
            "left join saes_dias d on a.ide_sadias = d.ide_sadias\n" +
            "where a.ide_sacita in ("+ide_sacita+") \n" +
            "and a.aprobado_sacita in ("+activo+") \n" +
            "order by a.fecha_cita_sacita ";
        return sql;
    }
}
