package com.evolup.dao;

import com.evolup.database.DBConnection;
import com.evolup.model.Objetivo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjetivoDAO {

    private Objetivo mapear(ResultSet rs) throws SQLException {
        return new Objetivo(
                rs.getInt("id_objetivo"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getDate("fecha_inicio").toLocalDate(),
                rs.getDate("fecha_objetivo").toLocalDate(),
                rs.getString("estado"),
                rs.getInt("id_usuario_fk")
        );
    }

    public List<Objetivo> findByUsuario(int idUsuario) throws SQLException {
        List<Objetivo> objetivos = new ArrayList<>();
        String sql = "SELECT * FROM OBJETIVO WHERE id_usuario_fk = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    objetivos.add(mapear(rs));
                }
            }
        }
        return objetivos;
    }

    public Objetivo findById(int id) throws SQLException {
        String sql = "SELECT * FROM OBJETIVO WHERE id_objetivo = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public void insert(Objetivo objetivo) throws SQLException {
        String sql = "INSERT INTO OBJETIVO (nombre, descripcion, fecha_inicio, fecha_objetivo, estado, id_usuario_fk) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, objetivo.getNombre());
            ps.setString(2, objetivo.getDescripcion());
            ps.setDate(3, Date.valueOf(objetivo.getFechaInicio()));
            ps.setDate(4, Date.valueOf(objetivo.getFechaObjetivo()));
            ps.setString(5, objetivo.getEstado());
            ps.setInt(6, objetivo.getIdUsuarioFk());
            ps.executeUpdate();
        }
    }

    public void updateEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE OBJETIVO SET estado = ? WHERE id_objetivo = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM OBJETIVO WHERE id_objetivo = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
