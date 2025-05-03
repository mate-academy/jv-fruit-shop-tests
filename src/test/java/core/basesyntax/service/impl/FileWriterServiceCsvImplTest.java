package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.WriteToFileCsvException;
import core.basesyntax.service.FileWriterService;
import org.junit.jupiter.api.Test;

public class FileWriterServiceCsvImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
    private static final String REPORT = "report";
    private static final FileWriterService fileWriterService = new FileWriterServiceCsvImpl();

    @Test
    void writeData_validPath_ok() {
        assertDoesNotThrow(() -> fileWriterService.writeData(OUTPUT_FILE_PATH, REPORT));
    }

    @Test
    void writeData_invalidPath_notOk() {
        assertThrows(WriteToFileCsvException.class, () -> fileWriterService.writeData("", REPORT));
    }
}
