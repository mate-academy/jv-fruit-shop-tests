package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvParserServiceImplTest {
    private static CsvParserServiceImpl csvParserService;

    @BeforeClass
    public static void beforeClass() {
        csvParserService = new CsvParserServiceImpl();
    }

    @Test
    public void validLine_Ok() {
        FruitTransaction actual = csvParserService.parse("b,banana,20");
        FruitTransaction expected = new FruitTransaction("b","banana",20);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void invalidLine_notOk() {
        csvParserService.parse("Invalid input format");
    }

    @Test (expected = RuntimeException.class)
    public void nullLine_notOk() {
        csvParserService.parse(null);
    }

    @Test (expected = RuntimeException.class)
    public void invalidOperation_notOk() {
        csvParserService.parse("d,banana, 20");
    }

    @Test (expected = RuntimeException.class)
    public void invalidFruit_notOk() {
        csvParserService.parse("b,carrot, 20");
    }

    @Test (expected = RuntimeException.class)
    public void invalidQuantity_notOk() {
        csvParserService.parse("b,banana, - 20");
    }
}
