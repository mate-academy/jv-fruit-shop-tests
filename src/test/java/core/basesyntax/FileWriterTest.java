package core.basesyntax;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String TEST_REPORT = "src\\test\\resources\\testReport.csv";
    private static final String UNEXISTING_FILE = "src\\test\\resources\\unexistingFile.csv";
    private FileWriterImpl fileWriter = new FileWriterImpl();

    @BeforeEach
    void setUp() throws IOException {
        Files.write(Paths.get(TEST_REPORT), "".getBytes());
    }

    @Test
    void fileReader_read_ok() throws IOException {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,64" + System.lineSeparator()
                + "banana,45" + System.lineSeparator();
        fileWriter.write(expected, TEST_REPORT);
        String actual = Files.readString(Paths.get(TEST_REPORT)).trim();
        Assertions.assertEquals(actual, expected.trim());
    }

    @Test
    void write_unexist_file_not_ok() {
        String data = "";
        Assertions.assertThrows(RuntimeException.class,() -> {
            fileWriter.write(data, UNEXISTING_FILE);
        });
    }

    @Test
    void write_null_not_ok() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            fileWriter.write(null, null);
        });
    }
}
