package service.impl;

import exception.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.WriterService;

class WriterServiceImplTest {
    private static WriterService writerService;
    private String dataToWrite;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @BeforeEach
    void setUp() {
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,20").append(System.lineSeparator())
                .append("apple,100");
        dataToWrite = report.toString();
    }

    @Test
    void write_nullPath_notOk() {
        Assertions.assertThrows(ValidationException.class,
                () -> writerService.write(dataToWrite, null));
    }

    @Test
    void write_nullDataToWrite_notOk() {
        String rightPath = "src/test/resources/validReportToWrite.csv";
        Assertions.assertThrows(ValidationException.class,
                () -> writerService.write(null, rightPath));
    }

    @Test
    void write_validParameters_isOk() {
        String rightPath = "src/test/resources/validReportToWrite.csv";
        Assertions.assertDoesNotThrow(() -> writerService.write(dataToWrite, rightPath));
    }

    @AfterEach
    void tearDown() {
        dataToWrite = null;
    }
}
