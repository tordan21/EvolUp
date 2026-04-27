package com.evolup.dao;

import com.evolup.database.DBConnection;
import com.evolup.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, email, contrasena FROM USUARIO";

        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contrasena")
                ));
            }
        }
        return usuarios;
    }

    public Usuario findById(int id) throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, contrasena FROM USUARIO WHERE id_usuario = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contrasena")
                    );
                }
            }
        }
        return null;
    }

    public Usuario findByEmail(String email) throws SQLException {
        String sql = "SELECT id_usuario, nombre, email, contrasena FROM USUARIO WHERE email = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contrasena")
                    );
                }
            }
        }
        return null;
    }

    public void insert(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO USUARIO (nombre, email, contrasena) VALUES (?, ?, ?)";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getContrasena());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM USUARIO WHERE id_usuario = ?";

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
