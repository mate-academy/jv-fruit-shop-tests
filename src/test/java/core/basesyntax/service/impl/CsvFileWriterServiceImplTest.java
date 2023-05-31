package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String OUTPUT_FILE = "src/test/resources/outputFile.csv";
    private static final String WRONG_PATH = "src\\\\main/,resources/.,output.csv\n";
    private static final String DATA_TO_WRITE
            = "line 1" + System.lineSeparator()
            + "line 2" + System.lineSeparator()
            + "line 3";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullValue_NotOk() {
        fileWriterService.writeToFile(OUTPUT_FILE, null);
    }

    @Test
    public void writeToFile_validData_Ok() {
        fileWriterService.writeToFile(OUTPUT_FILE, DATA_TO_WRITE);
        List<String> expected = new ArrayList<>();
        expected.add("line 1");
        expected.add("line 2");
        expected.add("line 3");
        List<String> actual = readFromFile(OUTPUT_FILE);

        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notValidPath_NotOk() {
        fileWriterService.writeToFile(WRONG_PATH, DATA_TO_WRITE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        fileWriterService.writeToFile(null, DATA_TO_WRITE);
    }

    private List<String> readFromFile(String path) {
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can not read such file: " + path, e);
        }
        return data;
    }
}
