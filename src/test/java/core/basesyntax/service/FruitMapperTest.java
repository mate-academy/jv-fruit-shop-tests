package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitMapperTest {
    private static FruitMapper fruitMapper;

    @BeforeAll
    static void beforeAll() {
        fruitMapper = new FruitMapper();
    }

    @Test
    void mapData_inputDataIsNull_NotOK() {
        List<String> inputData = null;
        assertThrows(RuntimeException.class, () -> fruitMapper.mapData(inputData), "Invalid data!");
    }

    @Test
    void mapData_inputDataIsEmpty_NotOK() {
        List<String> inputData = Collections.EMPTY_LIST;
        assertThrows(RuntimeException.class, () -> fruitMapper.mapData(inputData),"Invalid data!");
    }

    @Test
    void mapData_inputDataIsValid_OK() {
        List<String> validInputData = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,apple,13");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 13)
        );
        assertEquals(expected, fruitMapper.mapData(validInputData));
    }

    @Test
    void mapData_invalidInputData_NotOk() {
        List<String> invalidInputData = List.of("first line", "Invalid operation,fruit,15");
        assertThrows(IllegalArgumentException.class, () -> fruitMapper.mapData(invalidInputData),
                "Not valid code!");
    }
}
