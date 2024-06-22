package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.config.AppConfig;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers
                = appConfig.getOperationHandlers();
        shopService = new ShopServiceImpl(operationHandlers);
        inventory = new HashMap<>();
    }

    @Test
    void processTransactions_validTransactions_success() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 3),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1)
        );
        shopService.processTransactions(transactions, inventory);
        assertEquals(13, inventory.get("apple"));
    }

    @Test
    void processTransactions_invalidOperation_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(null, "apple", 10)
        );
        assertThrows(RuntimeException.class, ()
                -> shopService.processTransactions(transactions, inventory));
    }
}
