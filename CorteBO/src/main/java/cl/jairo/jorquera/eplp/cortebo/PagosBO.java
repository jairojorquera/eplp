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
public class PagosBO {

    public void procesarCandidatoCorte(String pathExcel, String pathExcelSalida)
            throws FileNotFoundException, IOException {

        List<CandidatoCorteTO> resumen = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(pathExcel));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet hoja = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();

        int DEPTO_NRO_COL = 0;
        int COPROPIETARIO_COL = 1;
        int TOTAL_COL = 3;
        int CANCELADO_COL = 4;
        int PENDIENTE_COL = 5;

        int i = 0;

        for (Row row : hoja) {
            i++;
            if (i >= 7) {
                if (!formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).isEmpty()
                        && !formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).equals("BOD-054")
                        && !formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).equals("TOTALES")) {

                    CandidatoCorteTO to = new CandidatoCorteTO();
                    to.setDpto(formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)));
                    to.setCopropietario((formatter.formatCellValue(row.getCell(COPROPIETARIO_COL))));
                    to.setTotalCobro(Long.valueOf(formatter.formatCellValue(row.getCell(TOTAL_COL)).replaceAll("\\.", "")));
                    to.setMontoCancelado(Long.valueOf(formatter.formatCellValue(row.getCell(CANCELADO_COL)).replaceAll("\\.", "")));
                    to.setSaldoPendiente(Long.valueOf(formatter.formatCellValue(row.getCell(PENDIENTE_COL)).replaceAll("\\.", "")));
                    to.calcularMeses();
                    resumen.add(to);
                }
            }
        }

        List<CandidatoCorteTO> candidatos = resumen.stream().filter(c -> c.getMesesDeuda() > 1)
                .sorted((CandidatoCorteTO a, CandidatoCorteTO b)
                        -> (a.getMesesDeuda() < b.getMesesDeuda()) ? 1 : -1
                )
                .collect(Collectors.toList());
        crearExcelFinal(candidatos, pathExcelSalida);

    }

    private static void crearExcelFinal(List<CandidatoCorteTO> candidatos, String pathExcelSalida) throws FileNotFoundException, IOException {

        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Candidatos");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);

        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Unidad Copropiedad");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Nombre Copropietario");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(2);
        cell.setCellValue("Total Cobro");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(3);
        cell.setCellValue("Monto Cancelado");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(4);
        cell.setCellValue("Saldo Pendiente");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(5);
        cell.setCellValue("Meses Deuda");
        cell.setCellStyle(headerCellStyle);
        int rowNum = 1;
        for (CandidatoCorteTO c : candidatos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0)
                    .setCellValue(c.getDpto());
            row.createCell(1)
                    .setCellValue(c.getCopropietario());
            row.createCell(2)
                    .setCellValue(c.getTotalCobro());
            row.createCell(3)
                    .setCellValue(c.getMontoCancelado());
            row.createCell(4)
                    .setCellValue(c.getSaldoPendiente());
            row.createCell(5)
                    .setCellValue(c.getMesesDeuda());

        }

        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut
                = new FileOutputStream(pathExcelSalida);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

    }
}
