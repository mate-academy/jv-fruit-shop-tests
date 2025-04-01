package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String REPORT_FILE_NAME = "src/test/resources/reportToWrite.csv";
    private FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void write_validOutputDataAndFilePath_ok() throws IOException {
        String outputData = String.join(System.lineSeparator(), "banana,152",
                "apple,90");
        fileWriterService.write(outputData, REPORT_FILE_NAME);
        String expected = String.join(System.lineSeparator(), "fruit,quantity",
                "banana,152", "apple,90");
        String actual;
        actual = Files.readString(Path.of(REPORT_FILE_NAME));
        assertEquals(expected, actual);
    }

    @Test
    void write_nullOutputData_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.write(null, REPORT_FILE_NAME));
    }

    @Test
    void write_nullFileName_notOk() {
        String outputData = String.join(System.lineSeparator(), "banana,152",
                "apple,90");
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileWriterService.write(outputData, null));
        assertEquals("File name cannot be null", exception.getMessage());
    }

    @Test
    void write_emptyOutputData_ok() throws IOException {
        String outputData = "";
        fileWriterService.write(outputData, REPORT_FILE_NAME);
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual;
        actual = Files.readString(Path.of(REPORT_FILE_NAME));
        assertEquals(expected, actual);
    }

    @Test
    void write_invalidPathFile_notOk() {
        String invalidFileName = "src/test/notExistingFolder/invalidFileName";
        String outputData = String.join(System.lineSeparator(), "banana,152",
                "apple,90");
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileWriterService.write(outputData, invalidFileName));
        assertEquals("Can't write data to file: " + invalidFileName,
                exception.getMessage());
    }
}
