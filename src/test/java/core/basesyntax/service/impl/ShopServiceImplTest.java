package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private ShopServiceImpl shopService;
    private FruitStorage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        Map<Operation, OperationHandler> operationHandlers = new EnumMap<>(Operation.class);
        operationHandlers.put(Operation.BALANCE, new BalanceOperation(storage));
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation(storage));
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation(storage));
        operationHandlers.put(Operation.RETURN, new ReturnOperation(storage));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_ShouldUpdateStorage_WhenBalanceOperation() {
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(50);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(50, storage.getFruitQuantity("apple"));
    }

    @Test
    void process_ShouldUpdateStorage_WhenSupplyOperation() {
        storage.updateFruitQuantity("banana", 20);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.SUPPLY);
        transaction.setFruit("banana");
        transaction.setQuantity(30);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(50, storage.getFruitQuantity("banana"));
    }

    @Test
    void process_ShouldUpdateStorage_WhenPurchaseOperation() {
        storage.updateFruitQuantity("orange", 40);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit("orange");
        transaction.setQuantity(15);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(25, storage.getFruitQuantity("orange"));
    }

    @Test
    void process_ShouldUpdateStorage_WhenReturnOperation() {
        storage.updateFruitQuantity("grape", 10);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.RETURN);
        transaction.setFruit("grape");
        transaction.setQuantity(5);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(15, storage.getFruitQuantity("grape"));
    }

    @Test
    void process_ShouldThrowException_WhenInsufficientQuantityForPurchase() {
        storage.updateFruitQuantity("melon", 10);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit("melon");
        transaction.setQuantity(15);
        transactions.add(transaction);
        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions),
                "Insufficient inventory to purchase melon");
    }

    @Test
    void process_ShouldHandleMultipleTransactionsCorrectly() {
        storage.updateFruitQuantity("pear", 20);
        final List<FruitTransaction> transactions = new ArrayList<>();

        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(Operation.BALANCE);
        balanceTransaction.setFruit("pear");
        balanceTransaction.setQuantity(50);
        transactions.add(balanceTransaction);

        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(Operation.SUPPLY);
        supplyTransaction.setFruit("pear");
        supplyTransaction.setQuantity(20);
        transactions.add(supplyTransaction);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(Operation.PURCHASE);
        purchaseTransaction.setFruit("pear");
        purchaseTransaction.setQuantity(30);
        transactions.add(purchaseTransaction);

        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(Operation.RETURN);
        returnTransaction.setFruit("pear");
        returnTransaction.setQuantity(5);
        transactions.add(returnTransaction);

        shopService.process(transactions);
        assertEquals(45, storage.getFruitQuantity("pear"));
    }

}
