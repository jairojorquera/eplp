

import cl.jairo.jorquera.eplp.pagosbo.LectorProperties;
import cl.jairo.jorquera.eplp.pagosbo.PagosBO;
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
        LectorProperties lp = LectorProperties.leerArchivo("C:\\Users\\Jairo\\Downloads\\MultasCorte.properties");
        
        bo.procesarCandidatoCorte(lp);
    }

}
