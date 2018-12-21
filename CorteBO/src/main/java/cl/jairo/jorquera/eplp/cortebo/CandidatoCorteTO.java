
package cl.jairo.jorquera.eplp.cortebo;

/**
 *
 * @author jairo
 */
public class CandidatoCorteTO {
    String dpto;
    String copropietario;
    long totalCobro;
    long montoCancelado;
    long saldoPendiente;
    int mesesDeuda;

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

    public void calcularMeses(){
        if(this.getTotalCobro() < 0){
            this.mesesDeuda = 0;
            return;
        } 
        
        if(this.getDpto().endsWith("6")){
            this.mesesDeuda = Long.valueOf(this.getSaldoPendiente() / 35000).intValue();
            return;
        }else{
            this.mesesDeuda = Long.valueOf(this.getSaldoPendiente() / 65000).intValue();
            return;        
        }
        
        
    }
    
    @Override
    public String toString() {
        return "CandidatoCorteTO{" + "dpto=" + dpto + ", copropietario=" + copropietario + ", totalCobro=" + totalCobro + ", montoCancelado=" + montoCancelado + ", saldoPendiente=" + saldoPendiente + ", mesesDeuda=" + mesesDeuda + '}';
    }
    
    
}
