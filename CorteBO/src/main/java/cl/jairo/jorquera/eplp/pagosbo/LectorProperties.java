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

    String tipo;

    String nombreArchivoSalida;
    String nombreArchivoEntrada;
    LocalDate fechaMulta1;
    LocalDate fechaMulta2;
    Long multa1;
    Long multa2;
    Long deudaMinimaMulta;
    Long saldoMenor;
    Long saldoMayor;

    public LectorProperties(String path) {
        this.path = path;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getMulta1() {
        return multa1;
    }

    public void setMulta1(Long multa1) {
        this.multa1 = multa1;
    }

    public Long getMulta2() {
        return multa2;
    }

    public void setMulta2(Long multa2) {
        this.multa2 = multa2;
    }

    public Long getDeudaMinimaMulta() {
        return deudaMinimaMulta;
    }

    public void setDeudaMinimaMulta(Long deudaMinimaMulta) {
        this.deudaMinimaMulta = deudaMinimaMulta;
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

    public Long getSaldoMenor() {
        return saldoMenor;
    }

    public void setSaldoMenor(Long saldoMenor) {
        this.saldoMenor = saldoMenor;
    }

    public Long getSaldoMayor() {
        return saldoMayor;
    }

    public void setSaldoMayor(Long saldoMayor) {
        this.saldoMayor = saldoMayor;
    }

    @Override
    public String toString() {
        return "LectorProperties{" + "path=" + path + ", tipo=" + tipo + ", nombreArchivoSalida=" + nombreArchivoSalida + ", nombreArchivoEntrada=" + nombreArchivoEntrada + ", fechaMulta1=" + fechaMulta1 + ", fechaMulta2=" + fechaMulta2 + ", multa1=" + multa1 + ", multa2=" + multa2 + ", deudaMinimaMulta=" + deudaMinimaMulta + ", saldoMenor=" + saldoMenor + ", saldoMayor=" + saldoMayor + '}';
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

            l.setMulta1(Long.valueOf(p.getProperty("multaCorte1")));
            l.setMulta2(Long.valueOf(p.getProperty("multaCorte2")));
            l.setDeudaMinimaMulta(Long.valueOf(p.getProperty("valorMinimoDeuda")));
            l.setSaldoMenor(Long.valueOf(p.getProperty("valorReferenciaDeptoChico")));
            l.setSaldoMayor(Long.valueOf(p.getProperty("valorReferenciaDeptoGrande")));
            l.setTipo(p.getProperty("tipoEjecucion"));
            System.out.println("Configuración cargado: " + l.toString());
            return l;
        } catch (Exception e) {
            System.out.println("Error al leer el archivo de configuración: " + e.getMessage());
            e.printStackTrace();
            return null;

        }

    }
}
