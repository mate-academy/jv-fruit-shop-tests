package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;
import strategy.OperationStrategy;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private Map<FruitTransaction.Operation, OperationHandler>
            operationHandlers = new HashMap<>();

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    public void processValidTransactions_ok() {
        Storage.fruits.put("apple", 80);
        Storage.fruits.put("banana", 102);

        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        shopService.process(transactions);

        assertEquals(70, Storage.getAmount("apple"));
        assertEquals(152, Storage.getAmount("banana"));
    }
}
