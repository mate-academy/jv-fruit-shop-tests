package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoCreator;
import core.basesyntax.service.FruitRecordDtoParser;
import core.basesyntax.service.validator.RecordValidator;
import core.basesyntax.service.validator.RecordValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoCreatorImplTest {
    private static FruitRecordDtoCreator fruitRecordDtoCreator;
    private static FruitRecordDtoParser fruitRecordDtoParser;
    private static RecordValidator recordValidator;
    private static FruitRecordDto fruitRecordDto1;
    private static FruitRecordDto fruitRecordDto2;
    private static List<FruitRecordDto> expected;
    private static List<FruitRecordDto> actual;

    @BeforeClass
    public static void setUp() {
        recordValidator = new RecordValidatorImpl();
        fruitRecordDtoParser = new FruitRecordDtoParserImpl(recordValidator);
        fruitRecordDtoCreator = new FruitRecordDtoCreatorImpl(fruitRecordDtoParser);
        fruitRecordDto1 = new FruitRecordDto(FruitRecordDto.Type.SUPPLY,
                new Fruit("orange"), 13);
        fruitRecordDto2 = new FruitRecordDto(FruitRecordDto.Type.RETURN,
                new Fruit("apricot"), 67);
    }

    @Test
    public void createRecords_inputEmptyList_Ok() {
        expected = new ArrayList<>();
        actual = fruitRecordDtoCreator.createRecords(new ArrayList<>());
        assertEquals(expected, actual);
    }

    @Test
    public void createRecords_inputValidData_Ok() {
        expected = List.of(fruitRecordDto1, fruitRecordDto2);
        actual = fruitRecordDtoCreator
                .createRecords(List.of("s,orange,13", "r,apricot,67"));
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void createRecords_inputNull_NotOk() {
        fruitRecordDtoCreator.createRecords(null);
    }

    @Test (expected = RuntimeException.class)
    public void createRecords_inputInvalidData_NotOk() {
        fruitRecordDtoCreator.createRecords(List.of("s,13", "r,apricot,67"));
    }
}
