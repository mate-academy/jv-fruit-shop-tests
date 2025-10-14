package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import model.FruitTransaction.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;

public class ShopServiceImplTest {

    @BeforeEach
    void setUp() {
        Storage.clear();
    }

    private ShopService createShopService() {
        Map<Operation, OperationHandler> handlers = Map.of(
                Operation.BALANCE, transaction ->
                        Storage.setQuantity(transaction.getFruit(), transaction.getQuantity()),
                Operation.SUPPLY, transaction ->
                        Storage.addQuantity(transaction.getFruit(), transaction.getQuantity()),
                Operation.PURCHASE, transaction ->
                        Storage.addQuantity(transaction.getFruit(), -transaction.getQuantity()),
                Operation.RETURN, transaction ->
                        Storage.addQuantity(transaction.getFruit(), transaction.getQuantity())
        );

        OperationStrategy strategy = new OperationStrategyImpl(handlers);

        return new ShopServiceImpl(strategy);
    }

    @Test
    void process_transactions_updatesStorageCorrectly() {
        ShopService shopService = createShopService();

        FruitTransaction t1 = new FruitTransaction(Operation.BALANCE, "apple", 10);
        FruitTransaction t2 = new FruitTransaction(Operation.SUPPLY, "banana", 5);
        FruitTransaction t3 = new FruitTransaction(Operation.PURCHASE, "apple", 3);
        FruitTransaction t4 = new FruitTransaction(Operation.RETURN, "banana", 2);

        shopService.process(List.of(t1, t2, t3, t4));

        assertEquals(7, Storage.getQuantity("apple"));
        assertEquals(7, Storage.getQuantity("banana"));
    }
}


