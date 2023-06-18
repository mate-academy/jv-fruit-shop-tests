package core.basesyntax;

import core.basesyntax.impl.WriterServiceImpl;
import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTests {
    private static final String PATH_OUTPUT_FILE = "src/test/resources/reportTest.csv";
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
    void writeFile_writeValue_ok() throws IOException {
        WriterServiceImpl writerService = new WriterServiceImpl();
        String expectedValue = valueOutputFile;
        writerService.writeValueToFile(PATH_OUTPUT_FILE, expectedValue);
        List<String> lines = Files.readAllLines(Path.of(PATH_OUTPUT_FILE));
        String actualValue = String.join(System.lineSeparator(), lines);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void writeFile_writeValue_notOk() {
        writerService.writeValueToFile(PATH_OUTPUT_FILE, valueOutputFile);
        try {
            String fileContent = Files.readString(Paths.get(PATH_OUTPUT_FILE));
            Assertions.assertEquals(valueOutputFile, fileContent);
        } catch (IOException e) {
            Assertions.fail("Failed to read file: " + PATH_OUTPUT_FILE);
        } finally {
            try {
                Files.deleteIfExists(Paths.get(PATH_OUTPUT_FILE));
            } catch (IOException e) {
                Assertions.fail("Failed to delete file: " + PATH_OUTPUT_FILE);
            }
        }
    }
}
