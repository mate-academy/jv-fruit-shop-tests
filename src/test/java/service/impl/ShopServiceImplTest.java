package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ShopService;
import storage.Storage;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.impl.BalanceOperation;
import strategy.impl.OperationStrategyImpl;
import strategy.impl.PurchaseOperation;
import strategy.impl.ReturnOperation;
import strategy.impl.SupplyOperation;

class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap =
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                        FruitTransaction.Operation.RETURN, new ReturnOperation(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void afterEach() {
        Storage.getFruitDbResult().clear();
    }

    @Test
    void process_nullInputList_notOk() {
        assertThrows(RuntimeException.class, () -> shopService.process(null));
    }

    @Test
    void process_fruitTransactionObjectIsNull_notOk() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(null);
        assertThrows(RuntimeException.class, () -> shopService.process(fruitTransactionList));
    }

    @Test
    void process_fruitNameEqualsNull_notOk() {
        List<FruitTransaction> transactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 25));
        assertThrows(RuntimeException.class, () -> shopService.process(transactionList));
    }

    @Test
    void process_ValidInput_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );
        shopService.process(transactions);
        Map<String, Integer> result = Storage.getFruitDbResult();
        assertEquals(1, result.size());
        assertEquals(130, result.get("apple").intValue());
    }
}
