package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitDb;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseOperationHandler;
import core.basesyntax.service.strategy.ReturnOperationHandler;
import core.basesyntax.service.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static FruitShopServiceImpl fruitShopService;
    private static final String FIRST_FRUIT_NAME = "firstFruitName";
    private static final String SECOND_FRUIT_NAME = "secondFruitName";
    private static final int[] testNumbers =
            {10, 100, 15, 5, 208, 21, 93, 18, 100, 154, 57};
    private static final Map<Operation, OperationHandler>
            operationHandlerMap = new HashMap<>();
    private static final Map<String, Integer> expectedBalanceMap = new HashMap<>();
    private static final FruitTransaction fruitBalance = new FruitTransaction();
    private List<FruitTransaction> fruitTransactionList = new ArrayList<>();

    @BeforeAll
    static void initVariables() {
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);

        fruitBalance.setOperation(Operation.BALANCE);
        fruitBalance.setFruitName(FIRST_FRUIT_NAME);
        fruitBalance.setQuantity(testNumbers[10]);

        FruitDb.getBalanceMap().clear();
    }

    @BeforeEach
    void setCollections() {
        fruitTransactionList.clear();
        expectedBalanceMap.clear();
    }

    @AfterEach
    void clearBalanceMap() {
        FruitDb.getBalanceMap().clear();
    }

    @Test
    void executeTransactions_nullFruitTransactionList_notOk() {
        fruitTransactionList = null;
        assertThrows(NullPointerException.class,
                () -> fruitShopService.executeTransactions(fruitTransactionList));
    }

    @Test
    void executeTransactions_balanceOperation_ok() {
        fruitTransactionList.add(fruitBalance);

        fruitShopService.executeTransactions(fruitTransactionList);

        expectedBalanceMap.put(FIRST_FRUIT_NAME, testNumbers[10]);

        Map<String, Integer> actualBalanceMap = FruitDb.getBalanceMap();

        assertEquals(expectedBalanceMap, actualBalanceMap);
    }

    @Test
    void executeTransactions_correctOperations_ok() {
        FruitTransaction firstFruitBalance = new FruitTransaction();
        firstFruitBalance.setOperation(Operation.BALANCE);
        firstFruitBalance.setFruitName(FIRST_FRUIT_NAME);
        firstFruitBalance.setQuantity(testNumbers[0]);

        FruitTransaction firstFruitSupply = new FruitTransaction();
        firstFruitSupply.setOperation(Operation.SUPPLY);
        firstFruitSupply.setFruitName(FIRST_FRUIT_NAME);
        firstFruitSupply.setQuantity(testNumbers[1]);

        FruitTransaction firstFruitPurchase = new FruitTransaction();
        firstFruitPurchase.setOperation(Operation.PURCHASE);
        firstFruitPurchase.setFruitName(FIRST_FRUIT_NAME);
        firstFruitPurchase.setQuantity(testNumbers[2]);

        FruitTransaction firstFruitReturn = new FruitTransaction();
        firstFruitReturn.setOperation(Operation.RETURN);
        firstFruitReturn.setFruitName(FIRST_FRUIT_NAME);
        firstFruitReturn.setQuantity(testNumbers[3]);

        FruitTransaction secondFruitBalance = new FruitTransaction();
        secondFruitBalance.setOperation(Operation.BALANCE);
        secondFruitBalance.setFruitName(SECOND_FRUIT_NAME);
        secondFruitBalance.setQuantity(testNumbers[4]);

        FruitTransaction secondFruitSupply = new FruitTransaction();
        secondFruitSupply.setOperation(Operation.SUPPLY);
        secondFruitSupply.setFruitName(SECOND_FRUIT_NAME);
        secondFruitSupply.setQuantity(testNumbers[5]);

        FruitTransaction secondFruitPurchase = new FruitTransaction();
        secondFruitPurchase.setOperation(Operation.PURCHASE);
        secondFruitPurchase.setFruitName(SECOND_FRUIT_NAME);
        secondFruitPurchase.setQuantity(testNumbers[6]);

        FruitTransaction secondFruitReturn = new FruitTransaction();
        secondFruitReturn.setOperation(Operation.RETURN);
        secondFruitReturn.setFruitName(SECOND_FRUIT_NAME);
        secondFruitReturn.setQuantity(testNumbers[7]);

        fruitTransactionList.add(firstFruitBalance);
        fruitTransactionList.add(firstFruitSupply);
        fruitTransactionList.add(firstFruitPurchase);
        fruitTransactionList.add(firstFruitReturn);
        fruitTransactionList.add(secondFruitBalance);
        fruitTransactionList.add(secondFruitSupply);
        fruitTransactionList.add(secondFruitPurchase);
        fruitTransactionList.add(secondFruitReturn);

        expectedBalanceMap.put(FIRST_FRUIT_NAME, testNumbers[8]);
        expectedBalanceMap.put(SECOND_FRUIT_NAME, testNumbers[9]);

        fruitShopService.executeTransactions(fruitTransactionList);

        Map<String, Integer> actualBalanceMap = FruitDb.getBalanceMap();

        assertEquals(expectedBalanceMap, actualBalanceMap);
    }
}
