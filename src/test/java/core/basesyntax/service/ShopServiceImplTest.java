package core.basesyntax.service;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.domain.FruitTransaction;
import core.basesyntax.service.operation.*;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    @DisplayName("Process fruit transactions test")
    void processFruitTransactions_ok() {
        ShopService shopService = new ShopServiceImpl(operationStrategy, new FruitDaoImpl());
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                                 FruitTransaction.FruitName.APPLE,
                                                 10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                                 FruitTransaction.FruitName.BANANA,
                                                 20));
        shopService.process(transactions);
        int expectedStorageSizeAfterProcessTransactions = 2;
        int actualStorageSizeAfterProcessTransactions = Storage.getFruits().size();
        assertEquals(expectedStorageSizeAfterProcessTransactions, actualStorageSizeAfterProcessTransactions);
    }

    @Test
    @DisplayName("Process null fruits transactions test")
    void processNullFruitTranstactions_notOk() {
        ShopService shopService = new ShopServiceImpl(operationStrategy, new FruitDaoImpl());
        assertThrows(NullPointerException.class, () -> shopService.process(null));
    }
}