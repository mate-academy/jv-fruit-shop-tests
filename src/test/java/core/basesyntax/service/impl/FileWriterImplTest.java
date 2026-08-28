package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE_PATH =
            "src/test/resources/testOutput.csv";

    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_nullReport_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(null, "src/test/resources/testOutput.csv")
        );
    }

    @Test
    void write_nullPath_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> fileWriter.write("fruit,quantity",
                        null)
        );
    }

    @Test
    void write_validData_ok() throws IOException {
        String report = """
                 fruit,quantity
                 banana,152
                  apple,90
                 """;

        String filePath = "src/test/resources/testOutput.csv";

        fileWriter.write(report, TEST_FILE_PATH);

        Path path = Path.of(TEST_FILE_PATH);
        assertTrue(Files.exists(path));

        String actual = null;
        try {
            actual = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(report, actual);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }
}
