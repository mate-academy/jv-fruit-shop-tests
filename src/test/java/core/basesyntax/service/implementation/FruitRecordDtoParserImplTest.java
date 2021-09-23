package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
import core.basesyntax.service.validator.RecordValidator;
import core.basesyntax.service.validator.RecordValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static RecordValidator recordValidator;
    private static FruitRecordDtoParser fruitRecordDtoParser;
    private static FruitRecordDto fruitRecordDto1;
    private static FruitRecordDto fruitRecordDto2;
    private static FruitRecordDto expected;
    private static FruitRecordDto actual;

    @BeforeClass
    public static void setUp() {
        recordValidator = new RecordValidatorImpl();
        fruitRecordDtoParser = new FruitRecordDtoParserImpl(recordValidator);
        fruitRecordDto1 = new FruitRecordDto(FruitRecordDto.Type.SUPPLY,
                new Fruit("orange"), 13);
        fruitRecordDto2 = new FruitRecordDto(FruitRecordDto.Type.RETURN,
                new Fruit("apricot"), 67);
    }

    @Test
    public void parseRecord_inputValidData_Ok() {
        expected = fruitRecordDto1;
        actual = fruitRecordDtoParser.parseRecord("s,orange,13");
        assertEquals(expected, actual);
        expected = fruitRecordDto2;
        actual = fruitRecordDtoParser.parseRecord("r,apricot,67");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseRecord_inputNull_NotOk() {
        fruitRecordDtoParser.parseRecord(null);
    }

    @Test (expected = RuntimeException.class)
    public void parseRecord_inputEmptyString_NotOk() {
        fruitRecordDtoParser.parseRecord("");
    }

    @Test (expected = RuntimeException.class)
    public void parseRecord_inputInvalidTypeInString_NotOk() {
        fruitRecordDtoParser.parseRecord("c,apple,10");
    }

    @Test (expected = RuntimeException.class)
    public void parseRecord_inputEmptyTypeInString_NotOk() {
        fruitRecordDtoParser.parseRecord(",apple,10");
    }

    @Test (expected = RuntimeException.class)
    public void parseRecord_inputEmptyFruitNameInString_NotOk() {
        fruitRecordDtoParser.parseRecord("s,,10");
    }

    @Test (expected = RuntimeException.class)
    public void parseRecord_inputNegativeAmountInString_NotOk() {
        fruitRecordDtoParser.parseRecord("s,apple,-10");
    }
}
