package main.java.yevgen.dao;

import main.java.yevgen.util.Task;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelSaver extends AbstractSaver {

    private Sheet sheet;

    public ExcelSaver(List<Task> taskList) {
        super(taskList);
    }


    public void save(File file) {
        Workbook workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Sheet1");

        createHeaderRow();
        int rowCount = 0;
        for (Task aTask : taskList) {
            Row row = sheet.createRow(++rowCount);
            writeTask(aTask, row);
        }

        createChart();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("Exception with writing to file: " + e);
        }


    }

    private void writeTask(Task aTask, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(aTask.getName());

        cell = row.createCell(2);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(aTask.getProcessID());

        cell = row.createCell(1);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(aTask.getMemorySize());
    }

    private void createHeaderRow() {

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setColor((short)500);
        font.setItalic(true);
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);

        Cell cellName = row.createCell(0);
        cellName.setCellStyle(cellStyle);
        cellName.setCellValue("Name");

        Cell cellPID = row.createCell(2);
        cellPID.setCellStyle(cellStyle);
        cellPID.setCellValue("PID");

        Cell cellMemory = row.createCell(1);
        cellMemory.setCellStyle(cellStyle);
        cellMemory.setCellValue("Memory");
    }

    private void createChart() {
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = drawing.createAnchor(4, 4, 4, 4, 4, 5, 15, 20);

        Chart chart = drawing.createChart(anchor);
        ChartLegend legend = chart.getOrCreateLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        LineChartData data = chart.getChartDataFactory().createLineChartData();

        ValueAxis bottomAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

        ChartDataSource<String> xs = DataSources.fromStringCellRange(sheet, new CellRangeAddress(1, taskList.size(), 0, 0));
        ChartDataSource<Number> ys = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, taskList.size(), 1, 1));


        data.addSeries(xs, ys);

        chart.plot(data, bottomAxis, leftAxis);
    }



}

