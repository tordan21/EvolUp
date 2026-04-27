package com.evolup.dao;

import com.evolup.database.DBConnection;
import com.evolup.model.Habito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitoDAO {

    public List<Habito> findByObjetivo(int idObjetivo) throws SQLException {
        List<Habito> habitos = new ArrayList<>();
        String sql = "SELECT id_habito, nombre, frecuencia, id_objetivo_fk FROM HABITO WHERE id_objetivo_fk = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, idObjetivo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    habitos.add(new Habito(
                            rs.getInt("id_habito"),
                            rs.getString("nombre"),
                            rs.getString("frecuencia"),
                            rs.getInt("id_objetivo_fk")
                    ));
                }
            }
        }
        return habitos;
    }

    public Habito findById(int id) throws SQLException {
        String sql = "SELECT id_habito, nombre, frecuencia, id_objetivo_fk FROM HABITO WHERE id_habito = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Habito(
                            rs.getInt("id_habito"),
                            rs.getString("nombre"),
                            rs.getString("frecuencia"),
                            rs.getInt("id_objetivo_fk")
                    );
                }
            }
        }
        return null;
    }

    public void insert(Habito habito) throws SQLException {
        String sql = "INSERT INTO HABITO (nombre, frecuencia, id_objetivo_fk) VALUES (?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, habito.getNombre());
            ps.setString(2, habito.getFrecuencia());
            ps.setInt(3, habito.getIdObjetivoFk());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM HABITO WHERE id_habito = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
