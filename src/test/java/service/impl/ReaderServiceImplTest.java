package service.impl;

import exception.ValidationException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReaderService;
import util.TestConstants;

class ReaderServiceImplTest {
    private static ReaderService readerService;
    private List<String> expectedData;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @BeforeEach
    void setUp() {
        expectedData = List.of(TestConstants.HEADER,
                TestConstants.REPORT_LINE_1,
                TestConstants.REPORT_LINE_2,
                TestConstants.REPORT_LINE_3,
                TestConstants.REPORT_LINE_4);
    }

    @Test
    void read_invalidPath_notOk() {
        Assertions.assertThrows(ValidationException.class,
                () -> readerService.read(TestConstants.WRONG_PATH_TO_READ));
    }

    @Test
    void read_nullPath_notOk() {
        Assertions.assertThrows(ValidationException.class, () -> readerService.read(null));
    }

    @Test
    void read_validPath_isOk() {
        Assertions.assertDoesNotThrow(() -> readerService.read(TestConstants.RIGHT_PATH_TO_READ));
    }

    @Test
    void read_validData_isOk() {
        Assertions.assertLinesMatch(expectedData,
                readerService.read(TestConstants.RIGHT_PATH_TO_READ));
    }

    @AfterEach
    void tearDown() {
        expectedData = List.of();
    }
}
