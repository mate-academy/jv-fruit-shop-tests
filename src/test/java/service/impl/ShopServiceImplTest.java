package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;
import strategy.OperationStrategy;

public class ShopServiceImplTest {
    private ShopService service;

    @BeforeEach
    void setUp() {
        Map<Transaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Transaction.Operation.BALANCE, new BalanceOperation());
        handlerMap.put(Transaction.Operation.SUPPLY, new SupplyOperation());
        handlerMap.put(Transaction.Operation.PURCHASE, new PurchaseOperation());
        handlerMap.put(Transaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlerMap);
        service = new ShopServiceImpl(strategy, new HashMap<>());
    }

    @Test
    void processValidTransaction_ok() {
        Transaction transaction1 = new Transaction();
        transaction1.setOperation(Transaction.Operation.BALANCE);
        transaction1.setFruit("apple");
        transaction1.setQuantity(100);

        Transaction transaction2 = new Transaction();
        transaction2.setOperation(Transaction.Operation.SUPPLY);
        transaction2.setFruit("apple");
        transaction2.setQuantity(150);

        Transaction transaction3 = new Transaction();
        transaction3.setOperation(Transaction.Operation.PURCHASE);
        transaction3.setFruit("apple");
        transaction3.setQuantity(55);

        Transaction transaction4 = new Transaction();
        transaction4.setOperation(Transaction.Operation.BALANCE);
        transaction4.setFruit("orange");
        transaction4.setQuantity(30);

        service.process(List.of(transaction1, transaction2, transaction3, transaction4));

        assertEquals(195, Storage.getAmount("apple"));
        assertEquals(30, Storage.getAmount("orange"));
    }
}
