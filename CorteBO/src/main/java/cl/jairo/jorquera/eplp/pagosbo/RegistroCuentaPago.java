package cl.jairo.jorquera.eplp.pagosbo;

import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author jairo
 */
public class RegistroCuentaPago {

    String dpto;
    String copropietario;
    LocalDate fecha;
    String codigoEdifito;
    long totalCobro;
    long montoCancelado;
    long saldoPendiente;
    int mesesDeuda;
    long multa;

    public long getMulta() {
        return multa;
    }

    public void setMulta(long multa) {
        this.multa = multa;
    }

    public String getCodigoEdifito() {
        return codigoEdifito;
    }

    public void setCodigoEdifito(String codigoEdifito) {
        this.codigoEdifito = codigoEdifito;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getCopropietario() {
        return copropietario;
    }

    public void setCopropietario(String copropietario) {
        this.copropietario = copropietario;
    }

    public long getTotalCobro() {
        return totalCobro;
    }

    public void setTotalCobro(long totalCobro) {
        this.totalCobro = totalCobro;
    }

    public long getMontoCancelado() {
        return montoCancelado;
    }

    public void setMontoCancelado(long montoCancelado) {
        this.montoCancelado = montoCancelado;
    }

    public long getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(long saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public int getMesesDeuda() {
        return mesesDeuda;
    }

    public void setMesesDeuda(int mesesDeuda) {
        this.mesesDeuda = mesesDeuda;
    }

    public void calcularMeses() {
        if (this.getTotalCobro() < 0) {
            this.mesesDeuda = 0;
            return;
        }

        if (this.getDpto().endsWith("6")) {
            this.mesesDeuda = Long.valueOf(this.getSaldoPendiente() / 35000).intValue();
            return;
        } else {
            this.mesesDeuda = Long.valueOf(this.getSaldoPendiente() / 65000).intValue();
            return;
        }

    }

    public void calcularMulta(
            long valorCorte, long multaCompleta, long multaBasica, LocalDate fechaCorte1, LocalDate fechaCorte2
    ) {

        //Si no debe nada no tiene multa
        if (this.getTotalCobro() <= 0) {
            this.multa = 0;
            //System.out.println("-- No debe nada: getTotalCobro() <= 0");
            return;
        }

        // Si tiene un saldo pendiente mayor a un minimo a pagar, se le cobra multa completa
        if (this.getSaldoPendiente() > valorCorte) {
            this.multa = multaCompleta;
            //System.out.println("-- Saldo Pendiente > valorCorte: " + this.getSaldoPendiente());
            return;
        }

        // Si fecha pago es antes de la fecha de Corte 1
        if (this.getFecha() != null && this.getFecha().isBefore(fechaCorte1)) {
            this.multa = 0;
            //System.out.println("-- Fecha pago es anterior a fecha corte 1, fecha de pago: " + this.getFecha());
            return;
        }

        if (this.getFecha() != null && this.getFecha().isBefore(fechaCorte2)) {
            this.multa = multaBasica;
            //System.out.println("-- Fecha pago es anterior a fecha corte 2, fecha de pago: " + this.getFecha());
        }else{
            this.multa = multaCompleta;
            //System.out.println("-- Fecha pago es posterior a fecha corte 2, fecha de pago: " + this.getFecha());
        }
        

    }

    @Override
    public String toString() {
        return "RegistroCuentaPago{" + "dpto=" + dpto + ", copropietario=" + copropietario + ", fecha=" + fecha + ", codigoEdifito=" + codigoEdifito + ", totalCobro=" + totalCobro + ", montoCancelado=" + montoCancelado + ", saldoPendiente=" + saldoPendiente + ", mesesDeuda=" + mesesDeuda + ", multa=" + multa + '}';
    }



}
