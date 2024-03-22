package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitMapperImplTest {
    private static List<String> validData;
    private FruitTransactionMapper fruitMapper;

    @BeforeAll
    static void beforeAll() {
        validData = List.of("b,apple,100",
                "s,banana,100");
    }

    @BeforeEach
    void setUp() {
        fruitMapper = new FruitMapperImpl();
    }

    @Test
    void map_validData_ok() {
        List<FruitTransaction> result = fruitMapper.map(validData);
        assertEquals(2, result.size());

        FruitTransaction firstTransaction = result.get(0);
        assertEquals(Operation.BALANCE, firstTransaction.getOperation());
        assertEquals("apple", firstTransaction.getFruit());
        assertEquals(100, firstTransaction.getQuantity());

        FruitTransaction secondTransaction = result.get(1);
        assertEquals(Operation.SUPPLY, secondTransaction.getOperation());
        assertEquals("banana", secondTransaction.getFruit());
        assertEquals(100, secondTransaction.getQuantity());
    }

    @Test
    void map_RuntimeExceptionForEmptyData_notOk() {
        RuntimeException exceptionMessage = assertThrows(RuntimeException.class,
                () -> fruitMapper.map(Arrays.asList()));
        assertEquals("Can't parse data from empty file", exceptionMessage.getMessage());
    }
}
