package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_OF_REPORT_FILE = "src/test/resources/report.csv";
    private static final String NOT_VALID_PATH_OF_INPUT_FILE = "notValid/test/resources/input.csv";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFileMethodIsOk() {
        String report = "fruit, quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "orange,20" + System.lineSeparator()
                + "apple,20";
        List<String> expected = List.of("fruit, quantity",
                "banana,20",
                "orange,20",
                "apple,20");
        fileWriter.writeToFile(PATH_OF_REPORT_FILE, report);
        List<String> actual = readFromFile(PATH_OF_REPORT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFromFilePathNotOk() {
        fileWriter.writeToFile(NOT_VALID_PATH_OF_INPUT_FILE, "report");
    }

    @Test(expected = RuntimeException.class)
    public void writeFromFilePathNullNotOk() {
        fileWriter.writeToFile(null, "report");
    }

    private List<String> readFromFile(String pathOfReportFile) {
        Path path = Path.of(pathOfReportFile);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file"
                    + path.getFileName().toString(), e);
        }
    }
}
