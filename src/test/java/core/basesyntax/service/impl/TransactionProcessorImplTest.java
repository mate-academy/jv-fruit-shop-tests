package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.InventoryDao;
import core.basesyntax.dao.InventoryDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.OperationType;
import core.basesyntax.strategy.HandlerStrategy;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static HandlerStrategy strategy;
    private static InventoryDao inventoryDao;
    private static TransactionProcessorImpl transactionProcessor;
    private static FruitTransaction testTransaction;

    @BeforeAll
    static void setUp() {
        strategy = new HandlerStrategy(new HashMap<>());
        inventoryDao = new InventoryDaoImpl();
        transactionProcessor = new TransactionProcessorImpl(strategy);
        strategy.getStrategyMap().put(OperationType.BALANCE, new BalanceHandler(inventoryDao));
        strategy.getStrategyMap().put(OperationType.PURCHASE, new PurchaseHandler(inventoryDao));
        strategy.getStrategyMap().put(OperationType.RETURN, new ReturnHandler(inventoryDao));
        strategy.getStrategyMap().put(OperationType.SUPPLY, new SupplyHandler(inventoryDao));
        testTransaction = new FruitTransaction(OperationType.BALANCE, "potato", 100);
    }

    @BeforeEach
    void putDefaultDataToInventoryMap() {
        inventoryDao.putToInventory("potato", 100);
    }

    @AfterEach
    void cleanMap() {
        inventoryDao.getCurrentInventoryState().clear();
    }

    @Test
    void processTransaction_withAllValid_Ok() {
        assertAll(
                () -> {
                    transactionProcessor.processTransaction(testTransaction);
                    assertEquals(100, inventoryDao.getAmountByFruit("potato"));
                },
                () -> {
                    testTransaction.setOperationType(OperationType.PURCHASE);
                    testTransaction.setQuantity(15);
                    transactionProcessor.processTransaction(testTransaction);
                    assertEquals(85, inventoryDao.getAmountByFruit("potato"));
                },
                () -> {
                    testTransaction.setOperationType(OperationType.SUPPLY);
                    testTransaction.setQuantity(25);
                    transactionProcessor.processTransaction(testTransaction);
                    assertEquals(110, inventoryDao.getAmountByFruit("potato"));
                },
                () -> {
                    testTransaction.setOperationType(OperationType.RETURN);
                    testTransaction.setQuantity(3);
                    transactionProcessor.processTransaction(testTransaction);
                    assertEquals(113, inventoryDao.getAmountByFruit("potato"));
                }
        );
    }

    @Test
    void processTransaction_withNullTransaction_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> transactionProcessor.processTransaction(null));
    }
}
