package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitMapperImplTest {
    private FruitMapper fruitMapper;
    private final List<String> dataFromFile = new ArrayList<>();

    @BeforeEach
    void setUp() {
        fruitMapper = new FruitMapperImpl();
        dataFromFile.add("operation,fruit,quantity");
        dataFromFile.add("b,banana,100");
        dataFromFile.add("b,apple,100");
    }

    @Test
    void mapDataWithValidInput_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana",100));
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        List<FruitTransaction> actual = fruitMapper.mapData(dataFromFile);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void mapDataWithNotValidOperation_NotOk() {
        dataFromFile.add("a,banana,200");
        assertThrows(FruitTransactionException.class, () -> fruitMapper.mapData(dataFromFile));
    }

    @Test
    void mapDataEmptyList_NotOk() {
        List<String> list = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> fruitMapper.mapData(list));
    }
}
