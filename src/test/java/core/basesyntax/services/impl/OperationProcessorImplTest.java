package core.basesyntax.services.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceOperation;
import core.basesyntax.handler.impl.PurchaseOperation;
import core.basesyntax.handler.impl.ReturnOperation;
import core.basesyntax.handler.impl.SupplyOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationProcessorImplTest {
    private static OperationProcessorImpl operationProcessor;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerOperationMap = new HashMap<>();

        BalanceOperation balanceOperation = new BalanceOperation(fruitDao);
        PurchaseOperation purchaseOperation = new PurchaseOperation(fruitDao);
        ReturnOperation returnOperation = new ReturnOperation(fruitDao);
        SupplyOperation supplyOperation = new SupplyOperation(fruitDao);

        handlerOperationMap.put(
                FruitTransaction.Operation.BALANCE,balanceOperation);
        handlerOperationMap.put(
                FruitTransaction.Operation.SUPPLY, supplyOperation);
        handlerOperationMap.put(
                FruitTransaction.Operation.PURCHASE, purchaseOperation);
        handlerOperationMap.put(
                FruitTransaction.Operation.RETURN, returnOperation);

        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(handlerOperationMap);
        operationProcessor = new OperationProcessorImpl(operationStrategy);
    }

    @Test
    public void operationProcessor_processData_Ok() {
        operationProcessor.process(createTransactionData());
        int expectedBananaQuantity = 160;
        int actualBananaQuantity = Storage.getFruitsStorage().get("banana");
        Assert.assertEquals(expectedBananaQuantity, actualBananaQuantity);
    }

    @After
    public void tearDown() {
        Storage.getFruitsStorage().clear();
    }

    private List<FruitTransaction> createTransactionData() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("banana");
        balanceTransaction.setQuantity(150);

        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit("banana");
        supplyTransaction.setQuantity(30);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("banana");
        purchaseTransaction.setQuantity(40);

        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("banana");
        returnTransaction.setQuantity(20);
        return List.of(balanceTransaction, supplyTransaction,
                purchaseTransaction, returnTransaction);
    }
}

