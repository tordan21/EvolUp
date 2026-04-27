package com.evolup;

import com.evolup.dao.*;
import com.evolup.database.DBConnection;
import com.evolup.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("EvolUp");

        try {
            DBConnection.getConnection();
            System.out.println("Conexión establecida con la base de datos.");
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la base de datos: " + e.getMessage());
            return;
        }

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1 -> listarUsuarios();
                    case 2 -> listarObjetivosDeUsuario();
                    case 3 -> listarHabitosDeObjetivo();
                    case 4 -> listarRegistrosDeHabito();
                    case 5 -> listarLogrosDeUsuario();
                    case 0 -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            }
        }

        DBConnection.closeConnection();
        System.out.println("Hasta luego.");
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú:");
        System.out.println("1. Ver usuarios");
        System.out.println("2. Ver objetivos de un usuario");
        System.out.println("3. Ver hábitos de un objetivo");
        System.out.println("4. Ver registros de un hábito");
        System.out.println("5. Ver logros de un usuario");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new UsuarioDAO().findAll();
        System.out.println("\nUsuarios registrados:");
        for (Usuario u : usuarios) {
            System.out.println("  [" + u.getIdUsuario() + "] " + u.getNombre() + " - " + u.getEmail());
        }
    }

    private static void listarObjetivosDeUsuario() throws SQLException {
        System.out.print("ID del usuario: ");
        int id = leerOpcion();
        List<Objetivo> objetivos = new ObjetivoDAO().findByUsuario(id);
        if (objetivos.isEmpty()) {
            System.out.println("Este usuario no tiene objetivos.");
            return;
        }
        System.out.println("\nObjetivos:");
        for (Objetivo o : objetivos) {
            System.out.println("  [" + o.getIdObjetivo() + "] " + o.getNombre() + " (" + o.getEstado() + ") " +
                               o.getFechaInicio() + " → " + o.getFechaObjetivo());
        }
    }

    private static void listarHabitosDeObjetivo() throws SQLException {
        System.out.print("ID del objetivo: ");
        int id = leerOpcion();
        List<Habito> habitos = new HabitoDAO().findByObjetivo(id);
        if (habitos.isEmpty()) {
            System.out.println("Este objetivo no tiene hábitos.");
            return;
        }
        System.out.println("\nHábitos:");
        for (Habito h : habitos) {
            System.out.println("  [" + h.getIdHabito() + "] " + h.getNombre() + " - " + h.getFrecuencia());
        }
    }

    private static void listarRegistrosDeHabito() throws SQLException {
        System.out.print("ID del hábito: ");
        int id = leerOpcion();
        List<Registro> registros = new RegistroDAO().findByHabito(id);
        if (registros.isEmpty()) {
            System.out.println("Este hábito no tiene registros.");
            return;
        }
        System.out.println("\nRegistros:");
        for (Registro r : registros) {
            String estado = r.isCompletado() ? "✓" : "✗";
            System.out.println("  " + estado + " " + r.getFecha());
        }
    }

    private static void listarLogrosDeUsuario() throws SQLException {
        System.out.print("ID del usuario: ");
        int id = leerOpcion();
        List<Logro> logros = new LogroDAO().findByUsuario(id);
        if (logros.isEmpty()) {
            System.out.println("Este usuario no tiene logros aún.");
            return;
        }
        System.out.println("\nLogros:");
        for (Logro l : logros) {
            System.out.println("  ★ " + l.getNombre() + " - " + l.getDescripcion());
        }
    }
}
