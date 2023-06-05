package core.basesyntax.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitServiceImplTest {
    private static FruitServiceImpl fruitService;

    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl(new OperationStrategy(null));
    }

    @Test
    void fruitService_emptyData_Ok() {
        Assertions.assertDoesNotThrow(() -> fruitService.process(new ArrayList<>()));
    }

    @Test
    void fruitService_normalData_Ok() {
        List<FruitTransaction> fruitTransaction = List.of(
                new FruitTransaction(BALANCE, "apple", 5),
                new FruitTransaction(SUPPLY, "apple", 30),
                new FruitTransaction(RETURN, "apple", 10),
                new FruitTransaction(PURCHASE, "apple", 40)
        );
        Assertions.assertDoesNotThrow(() -> fruitService.process(fruitTransaction));
    }
}
