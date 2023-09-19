package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.storage.Storage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void input_ValidData_Ok() {
        List<String> data = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(ActivityType.BALANCE, "banana", 20),
                new FruitTransaction(ActivityType.BALANCE, "apple", 100),
                new FruitTransaction(ActivityType.SUPPLY, "banana", 100)
        );
        List<FruitTransaction> actual = fruitService.processFruitLines(data);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void input_InvalidData_NotOk() {
        List<String> data = List.of("d,banana,20", "a,apple,1", "s,ban,100");
        assertThrows(RuntimeException.class, () -> fruitService.processFruitLines(data));
    }

    @Test
    void input_QuantityIsNegative_NotOk() {
        List<String> data = List.of("b,banana,-1", "b,apple,-1", "s,banana,-1");
        assertThrows(RuntimeException.class, () -> fruitService.processFruitLines(data));
    }

    @Test
    void input_InvalidOperation_NotOk() {
        List<String> data = List.of("d,banana,20", "a,apple,100", "s,banana,100");
        assertThrows(RuntimeException.class, () -> fruitService.processFruitLines(data));
    }
}
