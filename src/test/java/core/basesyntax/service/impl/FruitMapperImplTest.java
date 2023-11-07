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
    public static final String DEFAULT_FRUIT = "banana";
    public static final String SECOND_DEFAULT_FRUIT = "apple";
    public static final int DEFAULT_QUANTITY = 100;
    public static final Operation DEFAULT_OPERATION = Operation.BALANCE;
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
        expected.add(new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT,DEFAULT_QUANTITY));
        expected.add(new FruitTransaction(DEFAULT_OPERATION,
                SECOND_DEFAULT_FRUIT, DEFAULT_QUANTITY));
        List<FruitTransaction> actual = fruitMapper.mapData(dataFromFile);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void mapDataWithNotValidOperation_NotOk() {
        String lineFromCsv = "a,banana,200";
        dataFromFile.add(lineFromCsv);
        assertThrows(FruitTransactionException.class, () -> fruitMapper.mapData(dataFromFile));
    }

    @Test
    void mapDataEmptyList_NotOk() {
        List<String> list = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> fruitMapper.mapData(list));
    }
}
