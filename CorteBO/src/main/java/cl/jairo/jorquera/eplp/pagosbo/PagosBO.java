package cl.jairo.jorquera.eplp.pagosbo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    int DEPTO_NRO_COL = 0;
    int COPROPIETARIO_COL = 1;
    int TOTAL_COL = 3;
    int CANCELADO_COL = 4;
    int PENDIENTE_COL = 5;
    int FECHA_COL = 9;
    int FILA_INICIO = 6;

    private LocalDate of(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        String fechaString = formatter.formatCellValue(cell);
        return LocalDate.parse(fechaString, DateTimeFormatter.ofPattern("d-MM-yyyy"));

    }

    public void procesarMultas(LectorProperties lp)
            throws FileNotFoundException, IOException {
        procesarMultas(
                lp.getNombreArchivoEntrada(),
                lp.getNombreArchivoSalida(),
                lp.getFechaMulta1(),
                lp.getFechaMulta2(),
                lp.getDeudaMinimaMulta(),
                lp.getMulta1(),
                lp.getMulta2()
        );

    }

    long valorCorte = 25000;
    long multaCompleta = 5000;
    long multaBasica = 2000;

    private void procesarMultas(String pathExcel, String pathExcelSalida,
            LocalDate fechaCorte1, LocalDate fechaCorte2, Long valorMinimoMulta, Long multaBasica, Long multaCompleta
    )
            throws FileNotFoundException, IOException {

        List<RegistroCuentaPago> resumen = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(pathExcel));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet hoja = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();

        int i = 0;

        for (Row row : hoja) {
            i++;
            if (i >= FILA_INICIO) {
                if (!formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).isEmpty()
                        && !formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).equals("BOD-054")
                        && !formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).equals("TOTALES")) {

                    RegistroCuentaPago to = new RegistroCuentaPago();
                    to.setDpto(formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)));
                    to.setCopropietario((formatter.formatCellValue(row.getCell(COPROPIETARIO_COL))));
                    to.setTotalCobro(Long.valueOf(formatter.formatCellValue(row.getCell(TOTAL_COL)).replaceAll("\\.", "")));
                    to.setMontoCancelado(Long.valueOf(formatter.formatCellValue(row.getCell(CANCELADO_COL)).replaceAll("\\.", "")));
                    to.setSaldoPendiente(Long.valueOf(formatter.formatCellValue(row.getCell(PENDIENTE_COL)).replaceAll("\\.", "")));
                    if (row.getCell(FECHA_COL) != null && !formatter.formatCellValue(row.getCell(FECHA_COL)).isEmpty()) {
                        to.setFecha(of(row.getCell(FECHA_COL)));
                    }
                    resumen.add(to);
                } else {
                    if (formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).isEmpty()) {

                        LocalDate fecha = of(row.getCell(FECHA_COL));
                        RegistroCuentaPago to = resumen.get(resumen.size() - 1);
                        if (fecha.isAfter(to.getFecha())) {
                            to.setFecha(fecha);
                        }
                    }
                }
            }
        }

        /*List<PagoTO> candidatos = resumen.stream().filter(c -> c.getMesesDeuda() > 1)
                .sorted((PagoTO a, PagoTO b)
                        -> (a.getMesesDeuda() < b.getMesesDeuda()) ? 1 : -1
                )
                .collect(Collectors.toList());
        crearExcelCandidatoCorteFinal(candidatos, pathExcelSalida);*/
        LectorDptos prop = new LectorDptos();

        for (RegistroCuentaPago to : resumen) {
            to.setCodigoEdifito(prop.getCodigo(to.getDpto()));
            to.calcularMulta(valorMinimoMulta, multaCompleta, multaBasica, fechaCorte1, fechaCorte2);
            System.out.println(to);
        }

        List<RegistroCuentaPago> multas = resumen.stream().filter(t -> t.getMulta() > 0).collect(Collectors.toList());
        System.out.println("Multas: " + multas.size());

        if (!multas.isEmpty()) {
            generarCsvMultas(multas, pathExcelSalida);
        }

    }

    private void generarCsvMultas(List<RegistroCuentaPago> multas, String pathSalida) {
        Path path = Paths.get(pathSalida);
        String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));

        try (BufferedWriter br = Files.newBufferedWriter(path,
                Charset.defaultCharset(), StandardOpenOption.CREATE)) {
            br.write("IDENTIFICADOR;NOMBRE DE UCO;DESCRIPCION;MONTO;FECHA (" + fechaActual + ");FONDO RESERVA (1=si, 0=no)");
            br.newLine();

            StringBuilder sb = null;
            for (RegistroCuentaPago line : multas) {
                sb = new StringBuilder();
                sb.append(line.getCodigoEdifito()).append(";");
                sb.append(line.getDpto()).append(";");
                sb.append("Pago fuera de plazo").append(";");
                sb.append(line.getMulta()).append(";");
                sb.append(fechaActual).append(";");
                sb.append("0");
                br.write(sb.toString());
                br.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void procesarCandidatoCorte(String pathExcel, String pathExcelSalida)
            throws FileNotFoundException, IOException {

        List<RegistroCuentaPago> resumen = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(pathExcel));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet hoja = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();

        int i = 0;

        for (Row row : hoja) {
            i++;
            if (i >= FILA_INICIO) {
                if (!formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).isEmpty()
                        && !formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).equals("BOD-054")
                        && !formatter.formatCellValue(row.getCell(DEPTO_NRO_COL)).equals("TOTALES")) {

                    RegistroCuentaPago to = new RegistroCuentaPago();
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

        List<RegistroCuentaPago> candidatos = resumen.stream().filter(c -> c.getMesesDeuda() > 1)
                .sorted((RegistroCuentaPago a, RegistroCuentaPago b)
                        -> (a.getMesesDeuda() < b.getMesesDeuda()) ? 1 : -1
                )
                .collect(Collectors.toList());
        crearExcelCandidatoCorteFinal(candidatos, pathExcelSalida);

    }

    private static void crearExcelCandidatoCorteFinal(List<RegistroCuentaPago> candidatos, String pathExcelSalida) throws FileNotFoundException, IOException {

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
        for (RegistroCuentaPago c : candidatos) {
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
