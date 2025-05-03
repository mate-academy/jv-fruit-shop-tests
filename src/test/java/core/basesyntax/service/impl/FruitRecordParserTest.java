package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.Test;

public class FruitRecordParserTest {
    private FruitRecord expected = new FruitRecord("b", new Fruit("banana"), 10);
    private FruitRecordParser fruitRecordParser = new FruitRecordParser();

    @Test(expected = NullPointerException.class)
    public void parse_nullPointerException_notOk() {
        fruitRecordParser.parse(null);
    }

    @Test
    public void parse_validData_Ok() {
        FruitRecord actual = fruitRecordParser.parse("b,banana,10");
        assertEquals(expected, actual);
    }

    @Test
    public void parse_notEqualObject_Ok() {
        FruitRecord actual = fruitRecordParser.parse("b,banaNA,10");
        assertNotEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void parse_invalidStringFormat_exception_Ok() {
        fruitRecordParser.parse("b,banana,plus10");
    }
}
