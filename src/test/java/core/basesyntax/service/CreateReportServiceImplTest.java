package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.StorageOfFruits;
import core.basesyntax.service.impl.CreateReportServiceImpl;
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
    void creating_Of_Report_ok() {
        StorageOfFruits.fruitStorage.put(KEY, VALUE);
        String parse = createReportService.parse(StorageOfFruits.fruitStorage);
        String assertLine = HEADER + System.lineSeparator() + LINE_OK;
        assertEquals(assertLine, parse);
    }

    @Test
    void creating_Header_Of_Report_ok() {
        String parse = createReportService.parse(StorageOfFruits.fruitStorage);
        assertEquals(HEADER, parse);
    }
}
