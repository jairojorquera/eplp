/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.jairo.jorquera.eplp.pagosbo;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.Properties;

/**
 *
 * @author Jairo
 */
public class LectorProperties {

    final String path;

    String nombreArchivoSalida;
    String nombreArchivoEntrada;
    LocalDate fechaMulta1;
    LocalDate fechaMulta2;

    public LectorProperties(String path) {
        this.path = path;
    }

    public String getNombreArchivoSalida() {
        return nombreArchivoSalida;
    }

    public void setNombreArchivoSalida(String nombreArchivoSalida) {
        this.nombreArchivoSalida = nombreArchivoSalida;
    }

    public String getNombreArchivoEntrada() {
        return nombreArchivoEntrada;
    }

    public void setNombreArchivoEntrada(String nombreArchivoEntrada) {
        this.nombreArchivoEntrada = nombreArchivoEntrada;
    }

    public LocalDate getFechaMulta1() {
        return fechaMulta1;
    }

    public void setFechaMulta1(LocalDate fechaMulta1) {
        this.fechaMulta1 = fechaMulta1;
    }

    public LocalDate getFechaMulta2() {
        return fechaMulta2;
    }

    public void setFechaMulta2(LocalDate fechaMulta2) {
        this.fechaMulta2 = fechaMulta2;
    }

    @Override
    public String toString() {
        return "LectorProperties{" + "nombreArchivoSalida=" + nombreArchivoSalida + ", nombreArchivoEntrada=" + nombreArchivoEntrada + ", fechaMulta1=" + fechaMulta1 + ", fechaMulta2=" + fechaMulta2 + '}';
    }

    public static LectorProperties leerArchivo(String path) {
        try {
            Properties p = new Properties();
            p.load(new FileReader(path));

            LectorProperties l = new LectorProperties(path);
            l.setNombreArchivoEntrada(p.getProperty("archivoEstadoPago"));
            l.setNombreArchivoSalida(p.getProperty("archivoSalida"));

            String[] sf1 = p.getProperty("fechaCorte1").split("-");

            l.setFechaMulta1(LocalDate.of(
                    Integer.parseInt(sf1[2]),
                    Integer.parseInt(sf1[1]),
                    Integer.parseInt(sf1[0])));

            String[] sf2 = p.getProperty("fechaCorte2").split("-");

            l.setFechaMulta2(LocalDate.of(
                    Integer.parseInt(sf2[2]),
                    Integer.parseInt(sf2[1]),
                    Integer.parseInt(sf2[0])));

            System.out.println("Configuración cargado: " + l.toString());
            return l;
        } catch (Exception e) {
            System.out.println("Error al leer el archivo de configuración: " + e.getMessage());
            e.printStackTrace();
            return null;

        }

    }
}
