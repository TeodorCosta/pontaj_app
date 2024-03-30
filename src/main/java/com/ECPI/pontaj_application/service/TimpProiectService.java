package com.ECPI.pontaj_application.service;

import com.ECPI.pontaj_application.entity.Proiect;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.ECPI.pontaj_application.entity.Angajat;
import com.ECPI.pontaj_application.entity.TimpProiect;
import com.ECPI.pontaj_application.repository.TimpProiectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TimpProiectService {
    @Autowired
    TimpProiectRepository timpProiectRepository;

    public void saveTimpProiect(TimpProiect timpProiect){

          timpProiectRepository.save(timpProiect);

    }

    public float calculateSumOfOreForAngajat(UUID angajatId) {
        List<TimpProiect> timpProiectList = timpProiectRepository.findByAngajatId(angajatId);
        float sum = 0;

        if (timpProiectList != null) {
            for (TimpProiect timpProiect : timpProiectList) {
                sum += timpProiect.getOre();
            }
        }

        return sum;
    }
    public List<TimpProiect> getTimpProiecte(){
        return timpProiectRepository.findAll();
    }
    public Angajat getAngajatByTimpProiectId(Integer timpProiectId) {
        TimpProiect timpProiect = timpProiectRepository.findById(timpProiectId).orElse(null);
        if (timpProiect != null) {
            return timpProiect.getAngajat();
        }
        return null;
    }
    public Proiect getProiectByTimpProiectId(Integer timpProiectId) {
        TimpProiect timpProiect = timpProiectRepository.findById(timpProiectId).orElse(null);
        if (timpProiect != null) {
            return timpProiect.getProiect();
        }
        return null;
    }


    public TimpProiect getTimpProiectById(Integer id){
       return  timpProiectRepository.getReferenceById(id);
    }
    public List<TimpProiect> findAllByMonth(int month) {
        return timpProiectRepository.findAllByMonth(month);
    }


    public List<TimpProiect> findAllByDate(LocalDate localDate){return timpProiectRepository.findAllByDate(localDate);}

    public List<TimpProiect> findAllByMonthAndAngajatId(int month, UUID angajatId) {
        return timpProiectRepository.findAllByMonthAndAngajatId(month, angajatId);
    }

    public void deleteTimpProiect(TimpProiect timpProiect){
        timpProiectRepository.delete(timpProiect);
    }


    public byte[] exportTimpProiectEntitiesToExcel() throws IOException {
        // Filter timpProiectList to include only timpProiects of active employees
        List<TimpProiect> timpProiectList = timpProiectRepository.findAll();
        timpProiectList = timpProiectList.stream()
                .filter(timpProiect -> !timpProiect.getAngajat().isActiv())
                .collect(Collectors.toList());

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Map<YearMonth, Map<LocalDate, List<TimpProiect>>> groupedByMonthAndDate = timpProiectList.stream()
                    .collect(Collectors.groupingBy(t -> YearMonth.from(t.getData()),
                            Collectors.groupingBy(TimpProiect::getData)));

            Map<YearMonth, Map<LocalDate, List<TimpProiect>>> sortedByMonthAndDate = new TreeMap<>(groupedByMonthAndDate);

            for (Map.Entry<YearMonth, Map<LocalDate, List<TimpProiect>>> monthEntry : sortedByMonthAndDate.entrySet()) {
                YearMonth yearMonth = monthEntry.getKey();
                String monthSheetName = yearMonth.getMonth().toString() + " " + yearMonth.getYear();
                Sheet monthSheet = workbook.createSheet(monthSheetName);

                // Map to store total hours worked by each employee
                Map<String, Float> totalHoursPerEmployee = new HashMap<>();

                int rowIdx = 0;

                // Create header row for the month sheet
                Row monthHeaderRow = monthSheet.createRow(rowIdx++);
                Set<String> projectSet = new HashSet<>();
                for (List<TimpProiect> timpProiects : monthEntry.getValue().values()) {
                    for (TimpProiect timpProiect : timpProiects) {
                        if (!timpProiect.getProiect().isLivrat()) {
                            projectSet.add(String.valueOf(timpProiect.getProiect().getNrComandaInt()));
                        }
                    }
                }

                int colIdx = 1; // Skip first column for employee names
                for (String project : projectSet) {
                    Cell cell = monthHeaderRow.createCell(colIdx++);
                    cell.setCellValue("" + project);
                }
                Cell totalCell = monthHeaderRow.createCell(colIdx);
                totalCell.setCellValue("Total");

                // Map to store the hours for each employee and project
                Map<String, Map<String, Float>> employeeProjectHours = new HashMap<>();

                // Populate data rows
                for (Map.Entry<LocalDate, List<TimpProiect>> dateEntry : monthEntry.getValue().entrySet()) {
                    for (TimpProiect timpProiect : dateEntry.getValue()) {
                        if (!timpProiect.getProiect().isLivrat()) {
                            String employeeName = timpProiect.getAngajat().fullName();
                            String project = String.valueOf(timpProiect.getProiect().getNrComandaInt());
                            float hours = timpProiect.getOre();

                            // Update employeeProjectHours
                            employeeProjectHours.putIfAbsent(employeeName, new HashMap<>());
                            Map<String, Float> projectHours = employeeProjectHours.get(employeeName);
                            projectHours.put(project, projectHours.getOrDefault(project, 0f) + hours);

                            // Update total hours for the employee
                            totalHoursPerEmployee.put(employeeName, totalHoursPerEmployee.getOrDefault(employeeName, 0f) + hours);
                        }
                    }
                }

                // Fill in the table data for the month sheet
                for (Map.Entry<String, Map<String, Float>> entry : employeeProjectHours.entrySet()) {
                    Row row = monthSheet.createRow(rowIdx++);
                    colIdx = 1;
                    // Set employee name in the first column
                    row.createCell(0).setCellValue(entry.getKey());
                    for (String project : projectSet) {
                        Float hours = entry.getValue().getOrDefault(project, 0f);
                        row.createCell(colIdx++).setCellValue(hours);
                    }
                    // Set total hours for the employee
                    row.createCell(colIdx).setCellValue(totalHoursPerEmployee.get(entry.getKey()));
                }

                // Auto-size columns
                for (int i = 0; i <= projectSet.size(); i++) {
                    monthSheet.autoSizeColumn(i);
                }
                // Add sum formula for each column in the last row
                Row totalRowMonth = monthSheet.createRow(rowIdx);
                totalRowMonth.createCell(0).setCellValue("Total");

                // Add SUM formula for each column including the Total column
                for (int columnIndex = 1; columnIndex <= projectSet.size() + 1; columnIndex++) {
                    String columnLetter = CellReference.convertNumToColString(columnIndex);
                    String sumFormula;
                    if (columnIndex == colIdx) {
                        sumFormula = "SUM(" + columnLetter + "2:" + columnLetter + rowIdx + ")";
                    } else {
                        sumFormula = "SUM(" + columnLetter + "2:" + columnLetter + (rowIdx - 1) + ")";
                    }
                    totalRowMonth.createCell(columnIndex).setCellFormula(sumFormula);
                }

                Map<LocalDate, List<TimpProiect>> sortedDateEntries = new TreeMap<>(monthEntry.getValue());

                for (Map.Entry<LocalDate, List<TimpProiect>> dateEntry : sortedDateEntries.entrySet()) {
                    LocalDate date = dateEntry.getKey();
                    List<TimpProiect> timpProiects = dateEntry.getValue();

                    // Create new tabs for dates with Livrat field false
                    boolean livratFalseExists = timpProiects.stream()
                            .anyMatch(timpProiect -> !timpProiect.getProiect().isLivrat());
                    if (!livratFalseExists) {
                        continue; // Skip creating tab if Livrat field is always true
                    }
                    String dateSheetName = date.toString();
                    Sheet dateSheet = workbook.createSheet(dateSheetName);

                    // Map to store total hours worked by each employee for the specific date
                    totalHoursPerEmployee = new HashMap<>();

                    // Create header row for the date sheet
                    Row dateHeaderRow = dateSheet.createRow(0);
                    projectSet = new HashSet<>();
                    for (TimpProiect timpProiect : timpProiects) {
                        if (!timpProiect.getProiect().isLivrat()) {
                            projectSet.add(String.valueOf(timpProiect.getProiect().getNrComandaInt()));
                        }
                    }

                    colIdx = 1; // Skip first column for employee names
                    for (String project : projectSet) {
                        Cell cell = dateHeaderRow.createCell(colIdx++);
                        cell.setCellValue("" + project);
                    }
                    Cell totalCellDate = dateHeaderRow.createCell(colIdx);
                    totalCellDate.setCellValue("Total");

                    // Map to store the hours for each employee and project for the specific date
                    employeeProjectHours = new HashMap<>();

                    // Populate data rows for the specific date
                    for (TimpProiect timpProiect : timpProiects) {
                        if (!timpProiect.getProiect().isLivrat()) {
                            String employeeName = timpProiect.getAngajat().fullName();
                            String project = String.valueOf(timpProiect.getProiect().getNrComandaInt());
                            float hours = timpProiect.getOre();

                            // Update employeeProjectHours
                            employeeProjectHours.putIfAbsent(employeeName, new HashMap<>());
                            Map<String, Float> projectHours = employeeProjectHours.get(employeeName);
                            projectHours.put(project, projectHours.getOrDefault(project, 0f) + hours);

                            // Update total hours for the employee
                            totalHoursPerEmployee.put(employeeName, totalHoursPerEmployee.getOrDefault(employeeName, 0f) + hours);
                        }
                    }

                    // Fill in the table data for the date sheet
                    int rowIndex = 1; // Start from the second row
                    for (Map.Entry<String, Map<String, Float>> entry : employeeProjectHours.entrySet()) {
                        Row row = dateSheet.createRow(rowIndex++);
                        colIdx = 1;
                        // Set employee name in the first column
                        row.createCell(0).setCellValue(entry.getKey());
                        for (String project : projectSet) {
                            Float hours = entry.getValue().getOrDefault(project, 0f);
                            row.createCell(colIdx++).setCellValue(hours);
                        }
                        // Set total hours for the employee
                        Cell currentTotalCell = row.createCell(colIdx);
                        float totalHours = totalHoursPerEmployee.get(entry.getKey());
                        currentTotalCell.setCellValue(totalHours);

                        // Apply formatting if total hours are below 7.5
                        if (totalHours < 7.5) {
                            currentTotalCell.setCellValue(totalHours + "!");
                        }
                    }

                    // Auto-size columns
                    for (int i = 0; i <= projectSet.size(); i++) {
                        dateSheet.autoSizeColumn(i);
                    }

                    // Add sum formula for each column in the last row
                    Row totalRowDate = dateSheet.createRow(rowIndex);
                    totalRowDate.createCell(0).setCellValue("Total");

                    // Add SUM formula for each column including the Total column
                    for (int columnIndex = 1; columnIndex <= projectSet.size() + 1; columnIndex++) {
                        String columnLetter = CellReference.convertNumToColString(columnIndex);
                        String sumFormula;
                        if (columnIndex == colIdx) {
                            sumFormula = "SUM(" + columnLetter + "2:" + columnLetter + rowIndex + ")";
                        } else {
                            sumFormula = "SUM(" + columnLetter + "2:" + columnLetter + (rowIndex - 1) + ")";
                        }
                        totalRowDate.createCell(columnIndex).setCellFormula(sumFormula);
                    }
                }
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }





























}
