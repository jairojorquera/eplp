package cl.jairo.jorquera.eplp.pagosbo;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author jairo
 */
public class CandidatosCorteAPP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        PagosBO bo = new PagosBO();
        String pathExcel = "C:\\Users\\jairo\\Downloads\\20190620.Resumen_Pagos.xlsx";
        String pathExcelSalida = "C:\\Users\\jairo\\Downloads\\Resumen_Pagos-20190620.csv";
        bo.procesarCandidatoCorte(pathExcel, pathExcelSalida);
    }

}
