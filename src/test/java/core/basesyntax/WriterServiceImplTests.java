package core.basesyntax;

import core.basesyntax.impl.WriterServiceImpl;
import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTests {
    private static final String PATH_OUTPUT_FILE = "src/test/resources/reportTest.csv";
    private static final String PATH_WRONG = "src/java/resources/reportTest.csv";
    private static final String PATH_FILE_NOT_EXIST = "src/java/resources/reportTest.txt";
    private String valueOutputFile;
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        valueOutputFile = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100";
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeFile_fileNotExist_notOk() {
        String value = "Some value";
        Assertions.assertThrows(RuntimeException.class, () -> {
            writerService.writeValueToFile(PATH_FILE_NOT_EXIST, value);
        });
    }

    @Test
    void writeFile_writeValue_ok() throws IOException {
        String expectedValue = valueOutputFile;
        writerService.writeValueToFile(PATH_OUTPUT_FILE, expectedValue);
        List<String> lines = Files.readAllLines(Path.of(PATH_OUTPUT_FILE));
        String actualValue = String.join(System.lineSeparator(), lines);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void writeFile_wrongPath_notOk() throws IOException {
        String expectedValue = valueOutputFile;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            writerService.writeValueToFile(PATH_WRONG, expectedValue);
        });
    }
}
