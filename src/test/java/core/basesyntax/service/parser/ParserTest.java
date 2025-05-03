package core.basesyntax.service.parser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitRecordDto;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser parser;
    private static FruitRecordDto correctFruitForDecrease;
    private static final String CORRECT_DATA = "p,fruit,21";
    private static final String INCORRECT_DATA = "fruit,21,p";

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserCsvImpl();
        correctFruitForDecrease = new FruitRecordDto(OperationType.PURCHASE,
                new Fruit("fruit"), 21);
    }

    @Before
    public void setUp() {
        FruitStorage.storage.clear();
        ParserCsvImpl.fruitStore.clear();
    }

    @Test
    public void parserParse_Ok() {
        parser.parse(new String[] {CORRECT_DATA});
        FruitRecordDto actual = ParserCsvImpl.fruitStore.get(0);
        FruitRecordDto expected = correctFruitForDecrease;
        assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parserWrongParse_NotOk() {
        parser.parse(new String[] {INCORRECT_DATA});
        ParserCsvImpl.fruitStore.get(0);
    }
}
