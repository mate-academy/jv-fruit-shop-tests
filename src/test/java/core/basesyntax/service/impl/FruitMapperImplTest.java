package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitMapperImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String VALID_FIRST_ROW = "b,banana,20";
    private static final String VALID_SECOND_ROW = "s,apple,100";
    private static final String NOT_VALID_ROW = "b,banana,notanumber";
    private FruitMapper fruitMapper;

    @BeforeEach
    void setUp() {
        fruitMapper = new FruitMapperImpl();
    }

    @Test
    void validData_convert_oK() {
        List<String> inputData = new ArrayList<>();
        inputData.add(TITLE);
        inputData.add(VALID_FIRST_ROW);
        inputData.add(VALID_SECOND_ROW);
        int quantityForBanana = 20;
        int quantityForApple = 100;
        List<Fruit> expected = List.of(
                new Fruit(FRUIT_BANANA, Operation.BALANCE, quantityForBanana),
                new Fruit(FRUIT_APPLE, Operation.SUPPLY, quantityForApple)
        );
        List<Fruit> actual = fruitMapper.convert(inputData);
        assertEquals(expected, actual, "Fruits should be correctly mapped from input data.");
    }

    @Test
    void incorrectDataFormat_convert_throwsNumberFormatException() {
        List<String> incorrectData = new ArrayList<>();
        incorrectData.add(TITLE);
        incorrectData.add(NOT_VALID_ROW);
        assertThrows(NumberFormatException.class,
                () -> fruitMapper.convert(incorrectData),
                "Conversion of incorrect data format should throw NumberFormatException.");
    }

    @Test
    void emptyList_convert_ok() {
        List<String> emptyDataAfterRemovalTitle = new ArrayList<>();
        emptyDataAfterRemovalTitle.add(TITLE);
        List<Fruit> actual = fruitMapper.convert(emptyDataAfterRemovalTitle);
        assertTrue(actual.isEmpty(),
                "Converting an empty list (except header) should return an empty list.");
    }
}
