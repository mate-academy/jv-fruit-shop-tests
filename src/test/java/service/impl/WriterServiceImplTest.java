package service.impl;

import exception.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.WriterService;
import util.TestConstants;

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
        report.append(TestConstants.HEADER).append(System.lineSeparator())
                .append(TestConstants.BANANA_BALANCE).append(System.lineSeparator())
                .append(TestConstants.APPLE_BALANCE);
        dataToWrite = report.toString();
    }

    @Test
    void write_nullPath_notOk() {
        Assertions.assertThrows(ValidationException.class,
                () -> writerService.write(dataToWrite, null));
    }

    @Test
    void write_nullDataToWrite_notOk() {
        Assertions.assertThrows(ValidationException.class,
                () -> writerService.write(null, TestConstants.RIGHT_PATH_TO_WRITE));
    }

    @Test
    void write_validParameters_isOk() {
        Assertions.assertDoesNotThrow(() -> writerService.write(dataToWrite,
                TestConstants.RIGHT_PATH_TO_WRITE));
    }

    @AfterEach
    void tearDown() {
        dataToWrite = null;
    }
}
