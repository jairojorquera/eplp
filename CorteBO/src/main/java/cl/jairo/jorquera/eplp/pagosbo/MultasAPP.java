package cl.jairo.jorquera.eplp.pagosbo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author jairo
 */
public class MultasAPP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        PagosBO bo = new PagosBO();
        String pathExcel = "C:\\Users\\jairo\\Downloads\\20190620.Resumen_Pagos.xlsx";
        String salida = "C:\\Users\\jairo\\Downloads\\20190620.Multas.csv";
        LocalDate fechaCorte1 = LocalDate.of(2019, Month.JUNE, 14);
        LocalDate fechaCorte2 = LocalDate.of(2019, Month.JUNE, 22);

        bo.procesarMultas(pathExcel, salida, fechaCorte1, fechaCorte2);
    }

}
