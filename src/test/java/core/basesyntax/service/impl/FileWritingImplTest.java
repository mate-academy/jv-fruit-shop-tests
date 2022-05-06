package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriting;
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
    private static FileWriting fileWriter;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWritingImpl();
    }

    @Test
    public void writeToFile_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,52" + System.lineSeparator()
                + "apple,9" + System.lineSeparator();
        List<String> expected = List.of("fruit,quantity",
                "banana,52",
                "apple,9");
        fileWriter.writeToFile(PATH_OF_OUTPUT_FILE, report);
        List<String> actual = readFromFile(PATH_OF_OUTPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileFilePathNotOk() {
        fileWriter.writeToFile(NOT_VALID_PATH, "report");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileNull() {
        fileWriter.writeToFile(null, "report");
    }

    private static List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Cant`t read data from file"
                    + Path.of(filePath).getFileName().toString(), e);
        }
    }
}
