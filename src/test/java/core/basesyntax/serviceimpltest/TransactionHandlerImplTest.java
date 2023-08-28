package core.basesyntax.serviceimpltest;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopService;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitsShopServiceImpl;
import core.basesyntax.service.impl.BalanceOperationHandler;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.PurchaseOperationHandler;
import core.basesyntax.service.impl.ReturnOperationHandler;
import core.basesyntax.service.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionHandlerImplTest {
    private FruitShopService fruitShopService;
    private FruitTransaction fruitTransactionBalance;
    private FruitTransaction fruitTransactionSupply;
    private FruitTransaction fruitTransactionPurchase;
    private FruitTransaction fruitTransactionReturn;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitsShopServiceImpl(operationStrategy);
        fruitTransactionBalance = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 20);
        fruitTransactionPurchase = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        fruitTransactionSupply = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);
        fruitTransactionReturn = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20);
    }

    @AfterEach
    void tearDown() {
        Storage.fruit.clear();
    }

    @Test
    void transaction_correctData_Ok() {
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransactionBalance,
                fruitTransactionPurchase, fruitTransactionSupply, fruitTransactionReturn);
        fruitShopService.process(fruitTransactionList);
        int expected = 60;
        int actual = Storage.fruit.get("apple");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void transaction_emptyList_ok() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        fruitShopService.process(emptyList);
        int expected = 0;
        int actual = Storage.fruit.size();
        Assertions.assertEquals(expected, actual);
    }
}
