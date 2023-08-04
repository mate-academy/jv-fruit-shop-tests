package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
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
                new FruitTransaction(FruitShopOperation.BALANCE, "apple", 40),
                new FruitTransaction(FruitShopOperation.BALANCE, "banana", 60),
                new FruitTransaction(FruitShopOperation.SUPPLY, "apple", 20),
                new FruitTransaction(FruitShopOperation.RETURN, "banana", 20),
                new FruitTransaction(FruitShopOperation.PURCHASE, "apple", 30)
        );
        Map<String, Integer> expected = new HashMap<>(Map.of("apple", 30, "banana", 80));
        dataProcessingService.processData(transactions);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Storage doesn't have correct data after processing transactions!");
        Storage.clear();
    }
}
