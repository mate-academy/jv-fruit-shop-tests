package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.service.CreateReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String LINE_OK = "100,banana";
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static CreateReportService createReportService;

    @BeforeAll
    static void beforeAll() {
        createReportService = new CreateReportServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        StorageOfFruits.fruitStorage.clear();
    }

    @Test
    void parse_creatingReport_isOk() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        String parse = createReportService.parse(StorageOfFruits.fruitStorage);
        String expected = HEADER + System.lineSeparator() + LINE_OK;
        assertEquals(expected, parse);
    }

    @Test
    void parse_creatingHeader_isOk() {
        String parse = createReportService.parse(StorageOfFruits.fruitStorage);
        assertEquals(HEADER, parse);
    }
}
