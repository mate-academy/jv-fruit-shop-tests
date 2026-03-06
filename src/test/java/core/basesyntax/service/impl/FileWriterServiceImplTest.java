package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String TEST_OUTPUT_FILE = "src/test/resources/test_report.csv";
    private static FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void write_validData_Ok() throws IOException {
        String expectedReport = "fruit,quantity" + System.lineSeparator() + "banana,152";
        fileWriterService.write(expectedReport, TEST_OUTPUT_FILE);
        String actualReport = Files.readString(Path.of(TEST_OUTPUT_FILE));
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void invalidFilePath_notOk() {
        String report = "test data";
        String invalidPath = "src/test/resource/non_exist_folder/test.csv";
        assertThrows(RuntimeException.class,
                () -> fileWriterService.write(report, invalidPath));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_OUTPUT_FILE));
    }
}
