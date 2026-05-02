package com.evolup;

import com.evolup.dao.*;
import com.evolup.database.DBConnection;
import com.evolup.model.*;
import com.evolup.util.Validador;

import java.sql.SQLException;
import java.time.LocalDate;
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
                    case 6 -> añadirObjetivo();
                    case 7 -> marcarObjetivoCompletado();
                    case 8 -> eliminarHabito();
                    case 9 -> registrarCumplimiento();
                    case 0 -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException e) {
                System.out.println("Error al ejecutar la operación: " + e.getMessage());
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
        System.out.println("6. Añadir objetivo");
        System.out.println("7. Marcar objetivo como completado");
        System.out.println("8. Eliminar hábito");
        System.out.println("9. Registrar cumplimiento de hábito");
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

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
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

    private static void añadirObjetivo() throws SQLException {
        System.out.print("ID del usuario: ");
        int idUsuario = leerOpcion();
        if (!Validador.idValido(idUsuario)) {
            System.out.println("ID no válido.");
            return;
        }

        String nombre = leerTexto("Nombre del objetivo: ");
        if (!Validador.nombreValido(nombre)) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        LocalDate fechaInicio = null;
        while (fechaInicio == null) {
            fechaInicio = Validador.parsearFecha(leerTexto("Fecha de inicio (AAAA-MM-DD): "));
            if (fechaInicio == null) System.out.println("Formato incorrecto. Usa AAAA-MM-DD.");
        }

        LocalDate fechaObjetivo = null;
        while (fechaObjetivo == null) {
            fechaObjetivo = Validador.parsearFecha(leerTexto("Fecha objetivo (AAAA-MM-DD): "));
            if (fechaObjetivo == null) System.out.println("Formato incorrecto. Usa AAAA-MM-DD.");
        }

        if (fechaObjetivo.isAfter(fechaInicio.plusDays(21))) {
            System.out.println("La fecha objetivo no puede ser más de 21 días después del inicio.");
            return;
        }

        Objetivo objetivo = new Objetivo(0, nombre, null, fechaInicio, fechaObjetivo, "activo", idUsuario);
        new ObjetivoDAO().insert(objetivo);
        System.out.println("Objetivo añadido correctamente.");
    }

    private static void marcarObjetivoCompletado() throws SQLException {
        System.out.print("ID del objetivo: ");
        int id = leerOpcion();

        ObjetivoDAO objetivoDAO = new ObjetivoDAO();
        Objetivo objetivo = objetivoDAO.findById(id);
        if (objetivo == null) {
            System.out.println("No existe ningún objetivo con ese ID.");
            return;
        }

        objetivoDAO.updateEstado(id, "completado");
        System.out.println("Objetivo \"" + objetivo.getNombre() + "\" marcado como completado.");
    }

    private static void eliminarHabito() throws SQLException {
        System.out.print("ID del hábito: ");
        int id = leerOpcion();

        HabitoDAO habitoDAO = new HabitoDAO();
        Habito habito = habitoDAO.findById(id);
        if (habito == null) {
            System.out.println("No existe ningún hábito con ese ID.");
            return;
        }

        habitoDAO.delete(id);
        System.out.println("Hábito \"" + habito.getNombre() + "\" eliminado correctamente.");
    }

    private static void registrarCumplimiento() throws SQLException {
        System.out.print("ID del hábito: ");
        int idHabito = leerOpcion();

        Habito habito = new HabitoDAO().findById(idHabito);
        if (habito == null) {
            System.out.println("No existe ningún hábito con ese ID.");
            return;
        }

        LocalDate fecha = null;
        while (fecha == null) {
            fecha = Validador.parsearFecha(leerTexto("Fecha (AAAA-MM-DD): "));
            if (fecha == null) System.out.println("Formato incorrecto. Usa AAAA-MM-DD.");
        }

        String respuesta = leerTexto("¿Completado? (s/n): ").toLowerCase();
        boolean completado = respuesta.equals("s");

        Registro registro = new Registro(0, fecha, completado, idHabito);
        new RegistroDAO().insert(registro);
        System.out.println("Registro guardado para \"" + habito.getNombre() + "\".");
    }
}
