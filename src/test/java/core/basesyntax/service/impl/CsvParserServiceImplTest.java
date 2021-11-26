package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.CsvParserService;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvParserServiceImplTest {
    private static final String VALID_LINE = "b,banana,20";
    private static final String VALID_OPERATION = "b";
    private static final String VALID_FRUIT_NAME = "banana";
    private static final int VALID_QUANTITY = 20;
    private static CsvParserService<TransactionDto> csvParserService;

    @BeforeClass
    public static void beforeClass() {
        csvParserService = new CsvParserServiceImpl(new ValidatorServiceImpl());
    }

    @Test
    public void validLine_Ok() {
        TransactionDto expected = new TransactionDto(VALID_OPERATION,
                VALID_FRUIT_NAME, VALID_QUANTITY);
        assertEquals(expected, csvParserService.parseLine(VALID_LINE));
    }
}
