package com.roca.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.roca.api.model.Despesa;
import com.roca.api.model.Lucro;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    public static <T> ByteArrayInputStream customersToExcel(List<T> entities, String type) throws IOException {
        String[] columns = {"DATA", "DESCRIÇÃO", "QTD", "VALOR", "TOTAL"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            Sheet sheet = workbook.createSheet("despesas");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK1.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (T entity : entities) {
                Row row = sheet.createRow(rowIdx++);

                switch (type) {
                    case "despesas":
                        Despesa despesa = (Despesa) entity;
                        getDespesa(row, despesa);
                        break;
                    case "lucros":
                        Lucro lucro = (Lucro) entity;
                        getLucro(row, lucro);
                        break;
                }

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    static Row getDespesa(Row row, Despesa despesa) {
        String date = new SimpleDateFormat("dd/MM/yyyy").format(despesa.getData());

        row.createCell(0).setCellValue(date);
        row.createCell(1).setCellValue(despesa.getDescricao());
        row.createCell(2).setCellValue(despesa.getQuantidade().doubleValue());
        row.createCell(3).setCellValue(despesa.getValorUnitario().doubleValue());
        row.createCell(4).setCellValue(despesa.getTotal().doubleValue());

        return row;
    }

    static Row getLucro(Row row, Lucro lucro) {
        String date = new SimpleDateFormat("dd/MM/yyyy").format(lucro.getData());

        row.createCell(0).setCellValue(date);
        row.createCell(1).setCellValue(lucro.getDescricao());
        row.createCell(2).setCellValue(lucro.getQuantidade().doubleValue());
        row.createCell(3).setCellValue(lucro.getValorUnitario().doubleValue());
        row.createCell(4).setCellValue(lucro.getTotal().doubleValue());

        return row;
    }
}
