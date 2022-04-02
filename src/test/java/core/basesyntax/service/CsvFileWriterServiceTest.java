package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static final String OUTPUT_PATH = "src/test/resources/outputTest.csv";
    private static final String INPUT_PATH = "src/test/resources/inputTest.csv";
    private static final StringBuilder builder = new StringBuilder();
    private static CsvFileWriterService csvFileWriterService;

    @BeforeClass
    public static void beforeClass() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        builder.append("type,fruit,quantity")
                .append(System.lineSeparator()).append("b,banana,20")
                .append(System.lineSeparator()).append("b,apple,100")
                .append(System.lineSeparator()).append("s,banana,100")
                .append(System.lineSeparator()).append("p,banana,13")
                .append(System.lineSeparator()).append("r,apple,10")
                .append(System.lineSeparator()).append("p,apple,20")
                .append(System.lineSeparator()).append("p,banana,5")
                .append(System.lineSeparator()).append("s,banana,50");
    }

    @Test
    public void writeToFile_validPathFile() {
        csvFileWriterService.writeToFile(OUTPUT_PATH, builder);
        List<String> expected = readFromTestFile(INPUT_PATH);
        List<String> actual = readFromTestFile(OUTPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyData_Ok() {
        csvFileWriterService.writeToFile(OUTPUT_PATH, new StringBuilder(""));
        List<String> expected = List.of();
        List<String> actual = readFromTestFile(OUTPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullBuilder_NotOk() {
        csvFileWriterService.writeToFile(OUTPUT_PATH, null);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullPath_NotOk() {
        csvFileWriterService.writeToFile(null, builder);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_emptyPath_NotOk() {
        csvFileWriterService.writeToFile("", builder);
    }

    private List<String> readFromTestFile(String fromFilePath) {
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(fromFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + fromFilePath, e);
        }
        return data;
    }
}
