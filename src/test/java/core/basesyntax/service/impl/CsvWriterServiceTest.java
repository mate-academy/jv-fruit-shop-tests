package core.basesyntax.service.impl;

import core.basesyntax.service.DataWriterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvWriterServiceTest {
    private static final String INVALID_PATH = "src/test/resources/dummy-path/dummy.csv";
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private DataWriterService dataWriterService;
    private static final String REPORT_DATA = "fruit,quantity\n"
                                                + "banana,152\n"
                                                + "apple,90";

    @BeforeEach
    void setUp() {
        dataWriterService = new CsvWriterService();
    }

    @Test
    void writeData_invalidPath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> dataWriterService.writeData(REPORT_DATA, INVALID_PATH));
    }

    @Test
    void writeData_validPath_Ok() {
        dataWriterService.writeData(REPORT_DATA, VALID_PATH);
    }
}