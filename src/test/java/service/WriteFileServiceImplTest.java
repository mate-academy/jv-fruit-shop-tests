package service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileServiceImplTest {
    private static WriteFileService writeFileService;

    @BeforeClass
    public static void beforeClass() {
        writeFileService = new WriteFileServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_wrongFilePath_notOk() {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,14"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator()
                + "orange,67"
                + System.lineSeparator();
        writeFileService.write(report, "src/test/java/resources/EmptyInput.csv");
    }

    @Test
    public void write_correctReport_Ok() {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,14"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator()
                + "orange,67"
                + System.lineSeparator();
        writeFileService.write(report, "src/test/resources/ToFile.csv");
        List<String> actual = reader("src/test/resources/ToFile.csv");
        List<String> expected = reader("src/test/resources/ExpectedFile.csv");
        assertEquals(actual, expected);
    }

    private List<String> reader(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
