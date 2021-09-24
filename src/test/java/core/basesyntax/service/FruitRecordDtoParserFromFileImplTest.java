package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitRecordDto;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserFromFileImplTest {
    private static FruitRecordDtoParserFromFileImpl fruitRecordDtoParserFromFile;
    private static final String INCORRECT_STRING = "Mate academy";
    private static final String CORRECT_STRING = "b,apple,100";
    private static FruitRecordDto modelFruitRecordDto;
    private static FruitRecordDto expected;
    private static FruitRecordDto actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitRecordDtoParserFromFile = new FruitRecordDtoParserFromFileImpl();
        modelFruitRecordDto = new FruitRecordDto();
        modelFruitRecordDto.setFruit("apple");
        modelFruitRecordDto.setType(FruitRecordDto.Activities.BALANCE);
        modelFruitRecordDto.setAmount(100);
    }

    @Test
    public void parse_incorrectString_NotOk() {
        try {
            fruitRecordDtoParserFromFile.parse(INCORRECT_STRING);
        } catch (NullPointerException e) {
            return;
        }
        fail("Should throw NullPointerException");
    }

    @Test
    public void parse_validData_Ok() {
        expected = fruitRecordDtoParserFromFile.parse(CORRECT_STRING);
        actual = modelFruitRecordDto;
        assertEquals(actual, expected);
    }
}
