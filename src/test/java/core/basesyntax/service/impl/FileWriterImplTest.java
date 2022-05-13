package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_wrongPathToFile_NotOk() {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,78"
                + System.lineSeparator()
                + "apple,87"
                + System.lineSeparator()
                + "orange,10"
                + System.lineSeparator();
        fileWriter.write(report, "src/test/java/resources/EmptyInput.csv");
    }

    @Test
    public void write_correctReport_Ok() {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,78"
                + System.lineSeparator()
                + "apple,87"
                + System.lineSeparator()
                + "orange,10"
                + System.lineSeparator();
        fileWriter.write(report, "src/test/resources/ToFile.csv");
        List<String> actual = readFromFile("src/test/resources/ToFile.csv");
        List<String> expected = readFromFile("src/test/resources/ExpectedFile.csv");
        assertEquals(actual, expected);
    }

    private List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
