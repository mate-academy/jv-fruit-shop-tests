package core.basesyntax.store.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.store.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String RESOURCE_FOLDER = "src/test/resources/";
    private static final String TEST_FILE = "test_report.csv";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validData_writesToFile() throws IOException {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90";
        fileWriter.write(report,RESOURCE_FOLDER + TEST_FILE);

        String writtenData = Files.readString(Path.of(RESOURCE_FOLDER, TEST_FILE));
        assert writtenData.equals(report) : "File content should match the written report.";

        Files.deleteIfExists(Path.of(RESOURCE_FOLDER, TEST_FILE));
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        String invalidFileName = "/invalid/path/test_report.csv";
        String report = "test report data";

        assertThrows(RuntimeException.class,
                () -> fileWriter.write(report, invalidFileName));
    }
}
