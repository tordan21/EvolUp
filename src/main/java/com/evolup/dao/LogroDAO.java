package com.evolup.dao;

import com.evolup.database.DBConnection;
import com.evolup.model.Logro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogroDAO {

    public List<Logro> findByUsuario(int idUsuario) throws SQLException {
        List<Logro> logros = new ArrayList<>();
        String sql = "SELECT l.id_logro, l.nombre, l.descripcion " +
                     "FROM LOGRO l " +
                     "JOIN USUARIO_LOGRO ul ON l.id_logro = ul.id_logro_fk " +
                     "WHERE ul.id_usuario_fk = ? " +
                     "ORDER BY ul.fecha_obtenido";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logros.add(new Logro(
                            rs.getInt("id_logro"),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    ));
                }
            }
        }
        return logros;
    }

    public List<Logro> findAll() throws SQLException {
        List<Logro> logros = new ArrayList<>();
        String sql = "SELECT id_logro, nombre, descripcion FROM LOGRO";

        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                logros.add(new Logro(
                        rs.getInt("id_logro"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }
        }
        return logros;
    }
}
