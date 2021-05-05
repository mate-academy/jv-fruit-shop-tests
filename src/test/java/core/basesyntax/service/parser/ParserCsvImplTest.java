package core.basesyntax.service.parser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitRecordDto;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.Before;
import org.junit.Test;

public class ParserCsvImplTest {
    private static Parser parser;
    private static FruitRecordDto correctFruitForDecrease;

    @Before
    public void setUp() {
        FruitStorage.storage.clear();
        ParserCsvImpl.fruitStore.clear();
        parser = new ParserCsvImpl();
        correctFruitForDecrease = new FruitRecordDto(OperationType.PURCHASE,
                new Fruit("fruit"), 21);
    }

    @Test
    public void parserParse_Ok() {
        parser.parse(new String[] {"p,fruit,21"});
        FruitRecordDto actual = ParserCsvImpl.fruitStore.get(0);
        FruitRecordDto expected = correctFruitForDecrease;
        assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parserWrongParse_NotOk() {
        parser.parse(new String[] {"fruit,21,p"});
        ParserCsvImpl.fruitStore.get(0);
    }
}
