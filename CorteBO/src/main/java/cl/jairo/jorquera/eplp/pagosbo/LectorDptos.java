package cl.jairo.jorquera.eplp.pagosbo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author jairo
 */
public class LectorDptos {

    String fileName = "C:\\Users\\jairo\\Documents\\GitHub\\eplp\\CorteBO\\src\\main\\resources\\ID_DPTOS.txt";
    Properties prop = new Properties();
    InputStream input;
    boolean procesado = false;

    public LectorDptos() {
        try {
            input = new FileInputStream(fileName);
            prop.load(input);
            procesado = true;
        } catch (Exception e) {
            procesado = false;
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public String getCodigo(String depto) {
        return prop.getProperty(depto);
    }
}
