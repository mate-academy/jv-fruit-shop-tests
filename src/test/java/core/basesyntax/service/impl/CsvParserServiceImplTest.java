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
        csvParserService.parse("d,banana, 20");
        csvParserService.parse("b,carrot, 20");
        csvParserService.parse("b,carrot, - 20");
        csvParserService.parse("Invalid input format");
        csvParserService.parse(null);
    }
}
