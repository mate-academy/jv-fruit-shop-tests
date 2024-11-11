package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static List<FruitTransaction> fruitTransactionList;
    private static Map<String, Integer> expectedFruitStorage;
    private static ShopService shopService;
    private static FruitTransaction balanceBanana;
    private static FruitTransaction balanceApple;
    private static FruitTransaction supplyBanana;
    private static FruitTransaction returnApple;
    private static FruitTransaction purchaseBanana;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionList = new ArrayList<>();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        shopService = new ShopServiceImpl(operationStrategy);
        expectedFruitStorage = new HashMap<>();
    }

    @AfterEach
    public void afterEachTest() {
        expectedFruitStorage.clear();
        FruitStorage.fruitStorage.clear();
        fruitTransactionList.clear();
    }

    @Test
    void process_validDataInput_ok() {
        balanceBanana = new FruitTransaction(BALANCE, "banana", 100);
        balanceApple = new FruitTransaction(BALANCE, "apple", 10);
        supplyBanana = new FruitTransaction(SUPPLY, "banana", 35);
        returnApple = new FruitTransaction(RETURN, "apple", 0);
        purchaseBanana = new FruitTransaction(PURCHASE, "banana", 40);

        fruitTransactionList.add(balanceBanana);
        fruitTransactionList.add(balanceApple);
        fruitTransactionList.add(supplyBanana);
        fruitTransactionList.add(returnApple);
        fruitTransactionList.add(purchaseBanana);

        expectedFruitStorage.put("banana", 95);
        expectedFruitStorage.put("apple", 10);

        Map<String, Integer> expected = expectedFruitStorage;
        shopService.process(fruitTransactionList);
        Map<String, Integer> actual = FruitStorage.fruitStorage;
        assertEquals(expected, actual);
    }

    @Test
    void process_negative_QuantityInput_notOk() {
        balanceBanana = new FruitTransaction(BALANCE, "banana", -100);
        fruitTransactionList.add(balanceBanana);
        assertThrows(RuntimeException.class, () -> shopService.process(fruitTransactionList));

        supplyBanana = new FruitTransaction(SUPPLY, "banana", -35);
        fruitTransactionList.add(supplyBanana);
        assertThrows(RuntimeException.class, () -> shopService.process(fruitTransactionList));

        returnApple = new FruitTransaction(RETURN, "apple", -5);
        fruitTransactionList.add(returnApple);
        assertThrows(RuntimeException.class, () -> shopService.process(fruitTransactionList));

        purchaseBanana = new FruitTransaction(PURCHASE, "banana", -40);
        assertThrows(RuntimeException.class, () -> shopService.process(fruitTransactionList));
        fruitTransactionList.add(purchaseBanana);
    }
}
