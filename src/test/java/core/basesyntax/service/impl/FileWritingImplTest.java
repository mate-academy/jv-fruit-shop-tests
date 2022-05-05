package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriting;
import core.basesyntax.service.Report;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWritingImplTest {
    private static final String PATH_OF_OUTPUT_FILE = "src/test/resources/report.csv";
    private static final String NOT_VALID_PATH = "notValid/test/resources/input.csv";
    private static FileWriting writer;
    private static Report report;

    @BeforeClass
    public static void setUp() {
        writer = new FileWritingImpl();
        report = new ReportImpl();
    }

    @Test
    public void writeToFile_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,52" + System.lineSeparator()
                + "apple,9" + System.lineSeparator();
        List<String> expected = List.of("fruit,quantity",
                "banana,52",
                "apple,9");
        writer.writeToFile(PATH_OF_OUTPUT_FILE, report);
        List<String> actual = read(PATH_OF_OUTPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileFilePathNotOk() {
        writer.writeToFile(NOT_VALID_PATH, report.getReport());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileNull() {
        writer.writeToFile(null, report.getReport());
    }

    private static List<String> read(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Cant`t read data from file"
                    + Path.of(filePath).getFileName().toString(), e);
        }
    }
}
