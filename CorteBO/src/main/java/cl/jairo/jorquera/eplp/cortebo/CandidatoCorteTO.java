
package cl.jairo.jorquera.eplp.cortebo;

/**
 *
 * @author jairo
 */
public class CandidatoCorteTO {
    int dpto;
    String copropietario;
    long totalCobro;
    long montoCancelado;
    long saldoPendiente;
    int mesesDeuda;

    public int getDpto() {
        return dpto;
    }

    public void setDpto(int dpto) {
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

    @Override
    public String toString() {
        return "CandidatoCorteTO{" + "dpto=" + dpto + ", copropietario=" + copropietario + ", totalCobro=" + totalCobro + ", montoCancelado=" + montoCancelado + ", saldoPendiente=" + saldoPendiente + ", mesesDeuda=" + mesesDeuda + '}';
    }
    
    
}
