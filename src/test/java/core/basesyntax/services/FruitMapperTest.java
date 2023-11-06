package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitMapperTest {
    private static FruitMapper fruitMapper;
    private static List<String> emptyList;
    private static Map<List<FruitTransaction>, List<String>> correctValues;
    private static List<String> wrongLength;
    private static List<String> wrongOperatorFruitOperation;

    @BeforeAll
    static void beforeAll() {
        fruitMapper = new FruitMapperImpl();
        emptyList = new ArrayList<>();
        correctValues = new HashMap<>();
        correctValues.put(List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20)), List.of("b,banana,20"));
        correctValues.put(List.of(
                new FruitTransaction(Operation.PURCHASE, "apple", 20)), List.of("p,apple,20"));
        correctValues.put(List.of(
                new FruitTransaction(Operation.SUPPLY, "banana", 100)), List.of("s,banana,100"));
        correctValues.put(List.of(
                new FruitTransaction(Operation.RETURN, "apple", 10)), List.of("r,apple,10"));
        wrongOperatorFruitOperation = List.of("a,kiwi,20");
        wrongLength = List.of("a, apple, 100, 100");
    }

    @Test
    void mapDate_correct_OK() {
        for (Map.Entry<List<FruitTransaction>, List<String>> map : correctValues.entrySet()) {
            List<FruitTransaction> excepted = map.getKey();
            List<String> value = map.getValue();
            List<FruitTransaction> actual = fruitMapper.mapData(value);
            assertEquals(excepted, actual);
        }
    }

    @Test
    void mapDate_incorrectValue_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            fruitMapper.mapData(wrongOperatorFruitOperation);
        });
    }

    @Test
    void mapDate_incorrectLengthLine_NotOK() {
        assertThrows(IllegalArgumentException.class, () -> {
            fruitMapper.mapData(wrongLength);
        });

    }

    @Test
    void mapData_checkNull_NotOK() {
        assertThrows(NullPointerException.class, () -> {
            fruitMapper.mapData(null);
        });
    }

    @Test
    void mapData_checkIsEmpty_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitMapper.mapData(emptyList);
        });
    }
}
