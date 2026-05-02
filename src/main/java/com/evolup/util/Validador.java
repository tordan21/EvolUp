package com.evolup.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Validador {

    private Validador() {}

    // Comprueba que el texto no sea nulo ni esté vacío
    public static boolean nombreValido(String texto) {
        return texto != null && !texto.isBlank();
    }

    // Comprueba que el ID sea un número positivo
    public static boolean idValido(int id) {
        return id > 0;
    }

    // Intenta parsear una fecha en formato AAAA-MM-DD
    // Devuelve null si el formato no es correcto
    public static LocalDate parsearFecha(String texto) {
        try {
            return LocalDate.parse(texto);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
