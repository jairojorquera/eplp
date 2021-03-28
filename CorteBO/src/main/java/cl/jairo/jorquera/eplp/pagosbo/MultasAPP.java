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

        if (args.length == 0) {
            System.out.println("Debe indicar el path del archivo de configuración");
            return;
        }

        //Leemos el archivo de configuraciones si viene lo busca 
        String path = args[0];//"C:\\Users\\Jairo\\Downloads\\MultasCorte.properties";

        if (path == null || path.isEmpty()) {
            System.out.println("Debe ingresar el path del archivo de configuracion.");
            return;
        }

        LectorProperties lp = LectorProperties.leerArchivo(path);

        PagosBO bo = new PagosBO();
        /* String pathExcel = "C:\\Users\\Jairo\\Downloads\\Resumen_Pagos.xlsx";
        String salida = "C:\\Users\\jairo\\Downloads\\20190620.Multas.csv";
        LocalDate fechaCorte1 = LocalDate.of(2021, Month.MARCH, 14);
        LocalDate fechaCorte2 = LocalDate.of(2021, Month.MARCH, 22);*/

        bo.procesarMultas(lp);
    }

}
