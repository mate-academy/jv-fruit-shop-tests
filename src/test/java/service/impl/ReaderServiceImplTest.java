package service.impl;

import exception.ValidationException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReaderService;

class ReaderServiceImplTest {
    private static ReaderService readerService;
    private List<String> expectedData;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @BeforeEach
    void setUp() {
        expectedData = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
    }

    @Test
    void read_invalidPath_notOk() {
        String wrongPath = "src/test/resources/ToRead.csv";
        Assertions.assertThrows(ValidationException.class, () -> readerService.read(wrongPath));
    }

    @Test
    void read_nullPath_notOk() {
        Assertions.assertThrows(ValidationException.class, () -> readerService.read(null));
    }

    @Test
    void read_validPath_isOk() {
        String rightPath = "src/test/resources/validReportToRead.csv";
        Assertions.assertDoesNotThrow(() -> readerService.read(rightPath));
    }

    @Test
    void read_validData_isOk() {
        String rightPath = "src/test/resources/validReportToRead.csv";
        Assertions.assertLinesMatch(expectedData, readerService.read(rightPath));
    }

    @AfterEach
    void tearDown() {
        expectedData = List.of();
    }
}
