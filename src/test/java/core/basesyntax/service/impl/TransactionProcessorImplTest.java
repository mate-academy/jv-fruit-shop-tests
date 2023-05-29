package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static TransactionProcessor transactionProcessor;
    private static OperationStrategy strategy;
    private static FruitDao fruitDao;
    private static final int FRUIT_BALANCE = 20;
    private static final String FRUIT = "banana";
    private OperationHandler operationHandler;
    private final FruitTransaction transactionBalance =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT, FRUIT_BALANCE);
    private final FruitTransaction transactionPurchase =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, FRUIT, 10);
    private final FruitTransaction transactionReturn =
            new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT, 5);
    private final FruitTransaction transactionSupply =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, FRUIT, FRUIT_BALANCE);

    @BeforeAll
    static void beforeAll() {
        strategy = new OperationStrategyImpl();
        fruitDao = new FruitDaoImpl();
        transactionProcessor = new TransactionProcessorImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.getFruitsStorage().put(FRUIT, FRUIT_BALANCE);
    }

    @AfterEach
    void afterEach() {
        Storage.getFruitsStorage().clear();
        strategy = new OperationStrategyImpl();
    }

    @Test
    void transactionBalance_Ok() {
        operationHandler = new BalanceOperationHandler(fruitDao);
        strategy.setStrategy(FruitTransaction.Operation.BALANCE, operationHandler);
        transactionProcessor.transaction(List.of(transactionBalance), strategy);
        int expectedQuantity = transactionBalance.getQuantity();
        int actualQuantity = Storage.getFruitsStorage().get(transactionBalance.getFruit());
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void transactionPurchase_Ok() {
        operationHandler = new PurchaseOperationHandler(fruitDao);
        strategy.setStrategy(FruitTransaction.Operation.PURCHASE, operationHandler);
        transactionProcessor.transaction(List.of(transactionPurchase), strategy);
        int expectedQuantity = FRUIT_BALANCE - transactionPurchase.getQuantity();
        int actualQuantity = Storage.getFruitsStorage().get(transactionBalance.getFruit());
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void transactionReturn_Ok() {
        operationHandler = new ReturnOperationHandler(fruitDao);
        strategy.setStrategy(FruitTransaction.Operation.RETURN, operationHandler);
        transactionProcessor.transaction(List.of(transactionReturn), strategy);
        int expectedQuantity = FRUIT_BALANCE + transactionReturn.getQuantity();
        int actualQuantity = Storage.getFruitsStorage().get(transactionReturn.getFruit());
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void transactionSupply_Ok() {
        operationHandler = new SupplyOperationHandler(fruitDao);
        strategy.setStrategy(FruitTransaction.Operation.SUPPLY, operationHandler);
        transactionProcessor.transaction(List.of(transactionSupply), strategy);
        int expectedQuantity = FRUIT_BALANCE + transactionSupply.getQuantity();
        int actualQuantity = Storage.getFruitsStorage().get(transactionSupply.getFruit());
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }
}
