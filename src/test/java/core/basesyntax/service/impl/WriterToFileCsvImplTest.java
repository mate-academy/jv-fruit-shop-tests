package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.WriterToFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterToFileCsvImplTest {
    private static WriterToFile writer;
    private static File fileReport;
    private static File fileReportTest;
    private static String defaultPath = "src/test/resources/";

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterToFileCsvImpl();
        fileReport = new File(defaultPath + "report.csv");
        fileReportTest = new File(defaultPath + "testreport.csv");
    }

    @Test
    public void write_fileNotExist_ok() {
        fileReport = new File(defaultPath + "report.csv");
        fileReportTest = new File(defaultPath + "testreport.csv");
        List<String> fruitsList = List.of("fruit,quantity",
                "banana,152", "apple,90", "pineapple,20");
        writeToFile(fruitsList, fileReportTest);
        writer.write(fruitsList, fileReport);
        List<String> actual = readFromFile(fileReportTest);
        List<String> expected = readFromFile(fileReport);
        assertEquals(expected, actual);
    }

    @Test
    public void write_fileExists_ok() {
        fileReport = new File(defaultPath + "report.csv");
        fileReportTest = new File(defaultPath + "testreport.csv");
        List<String> fruitsList = List.of("fruit,quantity",
                "banana,207", "apple,90");
        writeToFile(fruitsList, fileReportTest);
        writer.write(fruitsList, fileReport);
        List<String> actual = readFromFile(fileReportTest);
        List<String> expected = readFromFile(fileReport);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_filePathNotExists_notOk() {
        List<String> actual = List.of("fruit,quantity",
                "banana,207", "apple,90");
        writer.write(actual, new File("sadasdfsf/@" + "report.csv"));
    }

    private void writeToFile(List<String> list, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String string : list) {
                bufferedWriter.write(string + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file", e);
        }
    }

    private List<String> readFromFile(File file) {
        List<String> dataFromFile = null;
        try {
            dataFromFile = Files.readAllLines(new File(defaultPath + "report.csv").toPath());
        } catch (IOException e) {
            fail("Test couldn't find the file!!!");
        }
        return dataFromFile;
    }
}
