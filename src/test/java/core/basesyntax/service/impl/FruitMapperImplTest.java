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
    private FruitMapper fruitMapper;

    @BeforeEach
    void setUp() {
        fruitMapper = new FruitMapperImpl();
    }

    @Test
    void validData_convert_oK() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,20");
        inputData.add("s,apple,100");
        List<Fruit> expected = List.of(
                new Fruit("banana", Operation.BALANCE, 20),
                new Fruit("apple", Operation.SUPPLY, 100)
        );
        List<Fruit> actual = fruitMapper.convert(inputData);
        assertEquals(expected, actual, "Fruits should be correctly mapped from input data.");
    }

    @Test
    void incorrectDataFormat_convert_throwsNumberFormatException() {
        List<String> incorrectData = new ArrayList<>();
        incorrectData.add("type,fruit,quantity");
        incorrectData.add("b,banana,notanumber");
        assertThrows(NumberFormatException.class,
                () -> fruitMapper.convert(incorrectData),
                "Conversion of incorrect data format should throw NumberFormatException.");
    }

    @Test
    void emptyList_convert_ok() {
        List<String> emptyDataAfterRemovalTitle = new ArrayList<>();
        emptyDataAfterRemovalTitle.add("type,fruit,quantity");
        List<Fruit> actual = fruitMapper.convert(emptyDataAfterRemovalTitle);
        assertTrue(actual.isEmpty(),
                "Converting an empty list (except header) should return an empty list.");
    }
}
