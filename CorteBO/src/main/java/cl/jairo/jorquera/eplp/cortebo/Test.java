package cl.jairo.jorquera.eplp.cortebo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author jairo
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        PagosBO bo = new PagosBO();
        String pathExcel = "C:\\Users\\jairo\\Downloads\\Resumen_Pagos.xlsx";
        String pathExcelSalida = "C:\\Users\\jairo\\Downloads\\" 
                        + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) 
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HM")) 
                        + "-candidatos.xlsx";
        bo.procesarCandidatoCorte(pathExcel, pathExcelSalida);
    }

}
