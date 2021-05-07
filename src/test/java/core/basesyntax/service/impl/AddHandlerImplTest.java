package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.storage.DataBase;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddHandlerImplTest {
    private static final AddHandlerImpl addHandler = new AddHandlerImpl();
    private static Map<String, Integer> testDataBase;
    private static FruitRecordDto fruitRecordDto;
    private static OperationType operationType;

    @BeforeEach
    void setUp() {
        testDataBase = new HashMap<>();
        operationType = OperationType.SUPPLY;
        int amount = 25;
        fruitRecordDto
                = new FruitRecordDto(operationType, "banana", amount);
    }

    @AfterEach
    void tearDown() {
        DataBase.getDataBase().clear();
    }

    @Test
    void getAmountOfFruitFromStorage_Ok() {
        testDataBase.put(fruitRecordDto.getName(), fruitRecordDto.getAmount());
        int expected = testDataBase.get(fruitRecordDto.getName());
        int actual = addHandler.applyFruitToStorage(fruitRecordDto);
        assertEquals(expected, actual);
    }

    @Test
    void addFruitToStorage_Ok() {
        testDataBase.put(fruitRecordDto.getName(), fruitRecordDto.getAmount());
        int expected = testDataBase.get(fruitRecordDto.getName());
        int actual = addHandler.applyFruitToStorage(fruitRecordDto);
        assertEquals(expected, actual);
    }
}
