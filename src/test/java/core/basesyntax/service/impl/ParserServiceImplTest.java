package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private static final List<String> VALID_DATA = List.of("type,fruit,quantity", "b,banana,20",
            "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
            "p,apple,20", "p,banana,5", "s,banana,50");
    private static final List<String> EMPTY_DATA = List.of();
    private static final List<String> INVALID_DATA = List.of("putin huylo, russia sucks",
            "glory to Ukraine", "russia, is, a, terrorist state");
    private ParserService parserService;
    private List<FruitTransaction> validDataTransactions;
    private List<FruitTransaction> emptyDataTransactions;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
        validDataTransactions = parserService.parseData(VALID_DATA);
        emptyDataTransactions = parserService.parseData(EMPTY_DATA);
    }

    @Test
    public void parseData_inputDataIsValid_ok() {
        List<FruitTransaction> expected = validDataTransactions;
        List<FruitTransaction> actual = parserService.parseData(VALID_DATA);
        assertEquals(expected, actual);
    }

    @Test
    public void parseData_emptyData_ok() {
        List<FruitTransaction> expected = emptyDataTransactions;
        List<FruitTransaction> actual = parserService.parseData(EMPTY_DATA);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_invalidData_notOk() {
        parserService.parseData(INVALID_DATA);
        fail("You must throw Runtime Exception, if the data from the file is invalid");
    }

    @Test(expected = RuntimeException.class)
    public void parseData_dataIsNull_notOk() {
        parserService.parseData(null);
        fail("You must throw Runtime Exception, if the data from the file is null");
    }
}
