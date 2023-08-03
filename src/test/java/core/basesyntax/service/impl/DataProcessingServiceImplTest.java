package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.interfaces.DataProcessingService;
import core.basesyntax.service.interfaces.TransactionStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessingServiceImplTest {
    private TransactionStrategy transactionStrategy;
    private DataProcessingService dataProcessingService;

    @BeforeEach
    void setUp() {
        transactionStrategy = new TransactionStrategyImpl();
        dataProcessingService = new DataProcessingServiceImpl(transactionStrategy);
    }

    @Test
    void processData_OK() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, Fruit.APPLE, 40),
                new FruitTransaction(Operation.BALANCE, Fruit.BANANA, 60),
                new FruitTransaction(Operation.SUPPLY, Fruit.APPLE, 20),
                new FruitTransaction(Operation.RETURN, Fruit.BANANA, 20),
                new FruitTransaction(Operation.PURCHASE, Fruit.APPLE, 30)
        );
        Map<Fruit, Integer> expected = new HashMap<>(Map.of(Fruit.APPLE, 30, Fruit.BANANA, 80));
        dataProcessingService.processData(transactions);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Storage doesn't have correct data after processing transactions!");
        Storage.clear();
    }
}
