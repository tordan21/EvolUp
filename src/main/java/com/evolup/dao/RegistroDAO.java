package com.evolup.dao;

import com.evolup.database.DBConnection;
import com.evolup.model.Registro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAO {

    public List<Registro> findByHabito(int idHabito) throws SQLException {
        List<Registro> registros = new ArrayList<>();
        String sql = "SELECT id_registro, fecha, completado, id_habito_fk FROM REGISTRO WHERE id_habito_fk = ? ORDER BY fecha";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idHabito);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    registros.add(new Registro(
                            rs.getInt("id_registro"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getBoolean("completado"),
                            rs.getInt("id_habito_fk")
                    ));
                }
            }
        }
        return registros;
    }

    public void insert(Registro registro) throws SQLException {
        String sql = "INSERT INTO REGISTRO (fecha, completado, id_habito_fk) VALUES (?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(registro.getFecha()));
            ps.setBoolean(2, registro.isCompletado());
            ps.setInt(3, registro.getIdHabitoFk());
            ps.executeUpdate();
        }
    }

    public void marcarCompletado(int idHabito, LocalDate fecha) throws SQLException {
        String sql = "UPDATE REGISTRO SET completado = TRUE WHERE id_habito_fk = ? AND fecha = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idHabito);
            ps.setDate(2, Date.valueOf(fecha));
            ps.executeUpdate();
        }
    }
}
