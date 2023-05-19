package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FruitServiceImplTest {
    private final OperationStrategy operationStrategy = new OperationStrategyImpl();
    private final FruitService fruitService = new FruitServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.listFruits.clear();
    }

    @Test
    public void calc_validInput_Ok() {
        final Map<String, Integer> initialFruitQuantities = new HashMap<>();
        initialFruitQuantities.put("banana", 20);
        initialFruitQuantities.put("apple", 100);

        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 15));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 5));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 100));
        fruitService.calculateShoppingCart(fruitTransactions, operationStrategy);
        assertEquals(initialFruitQuantities, Storage.listFruits);
    }
}
