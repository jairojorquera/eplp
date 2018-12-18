package cl.jairo.jorquera.eplp.cortebo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Format;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
        String pathExcel = "C:\\Users\\jairo\\Downloads\\Resumen_Pagos.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(pathExcel));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet hoja = workbook.getSheetAt(0);
        
        DataFormatter formatter = new DataFormatter();
        Row row = hoja.getRow(7);

        int DEPTO_NRO_COL = 0;
        int COPROPIETARIO_COL = 1;
        int TOTAL_COL = 3;
        int CANCELADO_COL = 4;
        int PENDIENTE_COL = 5;
        
        CandidatoCorteTO to = new CandidatoCorteTO();
        to.setDpto(Integer.valueOf(formatter.formatCellValue(row.getCell(DEPTO_NRO_COL))));
        to.setCopropietario((formatter.formatCellValue(row.getCell(COPROPIETARIO_COL))));
        to.setTotalCobro(Long.valueOf(formatter.formatCellValue(row.getCell(TOTAL_COL)).replaceAll("\\.", "")) );
        to.setMontoCancelado(Long.valueOf(formatter.formatCellValue(row.getCell(CANCELADO_COL)).replaceAll("\\.", "")));
        to.setSaldoPendiente(Long.valueOf(formatter.formatCellValue(row.getCell(PENDIENTE_COL)).replaceAll("\\.", "")));
        
        System.out.println(to);
        
        /*
        Iterator iterator = hoja.iterator();
        
        
        DataFormatter formatter = new DataFormatter();
        while (iterator.hasNext()) {
            Row nextRow = (Row) iterator.next();
            Iterator cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = (Cell) cellIterator.next();
                String contenidoCelda = formatter.formatCellValue(cell);
                System.out.println("celda: " + contenidoCelda);
            }

        }*/
    }

}
