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
    private static final String FOLDER = "src/test/resources";
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
        File actual = new File(FOLDER, "writerTestActualOutput.csv");
        File expected = new File(FOLDER, "writerTestExpectedOutput.csv");
        csvFileWriterService.writeReport(actual, report);
        filesCompareByLine(actual.toPath(), expected.toPath());
        Assert.assertTrue(filesCompareByLine(actual.toPath(), expected.toPath()));
    }

    private static boolean filesCompareByLine(Path firstFile, Path secondFile) {
        try (BufferedReader firstBufferedReader = Files.newBufferedReader(firstFile);
                BufferedReader secondBufferedReader = Files.newBufferedReader(secondFile)) {
            String lineFirstFile = "";
            String lineSecondFile = "";
            while ((lineFirstFile = firstBufferedReader.readLine()) != null
                    && (lineSecondFile = secondBufferedReader.readLine()) != null) {
                if (!lineFirstFile.equals(lineSecondFile)) {
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can not read file " + firstFile + " " + secondFile, e);
        }
        return true;
    }
}
