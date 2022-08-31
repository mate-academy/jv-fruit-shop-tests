package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static final String FOLDER = "src/main/resources";
    private static final String OUTPUT_FILE = "output.csv";
    private static FileWriterService csvFileWriterService;
    private static List<String> report;

    @Before
    public void setUp() {
        report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add("banana,152");
        report.add("apple,90");

        csvFileWriterService = new CsvFileWriterService();
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_nullFile_notOk() {
        csvFileWriterService.writeReport(null, report);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_nullFilegf_notOk() {
        File file = new File(FOLDER, OUTPUT_FILE);
        csvFileWriterService.writeReport(file, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_fileNameNotValid_notOk() {
        String notValidName = "fh56*/*\\cg8d6fth_";
        File notValidNameFile = new File(FOLDER, notValidName);
        csvFileWriterService.writeReport(notValidNameFile, report);
    }

    @Test
    public void writeReport() {
        File actual = new File(FOLDER, OUTPUT_FILE);
        File expected = new File(FOLDER, "expected.csv");
        csvFileWriterService.writeReport(expected, report);
        Assert.assertTrue(filesCompareByLine(actual.toPath(), expected.toPath()));
    }

    private static boolean filesCompareByLine(Path firstFile, Path secondFile) {
        try (BufferedReader firstBufferedReader = Files.newBufferedReader(firstFile);
                BufferedReader secondBufferedReader = Files.newBufferedReader(secondFile)) {
            String lineFirstFile = "";
            String lineSecondFile = "";
            while ((lineFirstFile = firstBufferedReader.readLine()) != null
                    && (lineSecondFile = secondBufferedReader.readLine()) != null) {
                if (lineFirstFile.equals(lineSecondFile)) {
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }
        return true;
    }
}
