package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static final String FOLDER = "src/test/resources";
    private static FileWriterService csvFileWriterService;
    private static List<String> report;

    @BeforeClass
    public static void beforeClass() {
        csvFileWriterService = new CsvFileWriterService();
        report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add("banana,152");
        report.add("apple,90");
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_nullFile_notOk() {
        csvFileWriterService.writeReport(null, report);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_nullReport_notOk() {
        File file = new File(FOLDER, "writerTestNullOutput.csv");
        csvFileWriterService.writeReport(file, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_fileNameNotValid_notOk() {
        String notValidName = "fh56*/*\\cg8d6fth_";
        File notValidNameFile = new File(FOLDER, notValidName);
        csvFileWriterService.writeReport(notValidNameFile, report);
    }

    @Test
    public void writeReport_ok() {
        File outputFile = new File(FOLDER, "writerTestActualOutput.csv");
        csvFileWriterService.writeReport(outputFile, report);
        assertEquals("Files are different", report, readFile(outputFile));
    }

    private static List<String> readFile(File file) {
        List<String> list;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            list = bufferedReader.lines()
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + file.getName() + " does not exist.", e);
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file " + file.getName(), e);
        }
        return list;
    }
}
