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

    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String GRAPE = "grape";
    private static final String MELON = "melon";
    private static final String PEAR = "pear";
    private static final int APPLE_QUANTITY = 50;
    private static final int INITIAL_BANANA_QUANTITY = 20;
    private static final int SUPPLY_BANANA_QUANTITY = 30;
    private static final int EXPECTED_BANANA_QUANTITY = 50;
    private static final int INITIAL_ORANGE_QUANTITY = 40;
    private static final int PURCHASE_ORANGE_QUANTITY = 15;
    private static final int EXPECTED_ORANGE_QUANTITY = 25;
    private static final int INITIAL_GRAPE_QUANTITY = 10;
    private static final int RETURN_GRAPE_QUANTITY = 5;
    private static final int EXPECTED_GRAPE_QUANTITY = 15;
    private static final int INITIAL_MELON_QUANTITY = 10;
    private static final int PURCHASE_MELON_QUANTITY = 15;
    private static final int INITIAL_PEAR_QUANTITY = 20;
    private static final int BALANCE_PEAR_QUANTITY = 50;
    private static final int SUPPLY_PEAR_QUANTITY = 20;
    private static final int PURCHASE_PEAR_QUANTITY = 30;
    private static final int RETURN_PEAR_QUANTITY = 5;
    private static final int EXPECTED_PEAR_QUANTITY = 45;

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
        transaction.setFruit(APPLE);
        transaction.setQuantity(APPLE_QUANTITY);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(APPLE_QUANTITY, storage.getFruitQuantity(APPLE));
    }

    @Test
    void process_ShouldUpdateStorage_WhenSupplyOperation() {
        storage.updateFruitQuantity(BANANA, INITIAL_BANANA_QUANTITY);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.SUPPLY);
        transaction.setFruit(BANANA);
        transaction.setQuantity(SUPPLY_BANANA_QUANTITY);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(EXPECTED_BANANA_QUANTITY, storage.getFruitQuantity(BANANA));
    }

    @Test
    void process_ShouldUpdateStorage_WhenPurchaseOperation() {
        storage.updateFruitQuantity(ORANGE, INITIAL_ORANGE_QUANTITY);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit(ORANGE);
        transaction.setQuantity(PURCHASE_ORANGE_QUANTITY);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(EXPECTED_ORANGE_QUANTITY, storage.getFruitQuantity(ORANGE));
    }

    @Test
    void process_ShouldUpdateStorage_WhenReturnOperation() {
        storage.updateFruitQuantity(GRAPE, INITIAL_GRAPE_QUANTITY);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.RETURN);
        transaction.setFruit(GRAPE);
        transaction.setQuantity(RETURN_GRAPE_QUANTITY);
        transactions.add(transaction);
        shopService.process(transactions);
        assertEquals(EXPECTED_GRAPE_QUANTITY, storage.getFruitQuantity(GRAPE));
    }

    @Test
    void process_ShouldThrowException_WhenInsufficientQuantityForPurchase() {
        storage.updateFruitQuantity(MELON, INITIAL_MELON_QUANTITY);
        final List<FruitTransaction> transactions = new ArrayList<>();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit(MELON);
        transaction.setQuantity(PURCHASE_MELON_QUANTITY);
        transactions.add(transaction);
        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions),
                "Insufficient inventory to purchase melon");
    }

    @Test
    void process_ShouldHandleMultipleTransactionsCorrectly() {
        storage.updateFruitQuantity(PEAR, INITIAL_PEAR_QUANTITY);
        final List<FruitTransaction> transactions = new ArrayList<>();

        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(Operation.BALANCE);
        balanceTransaction.setFruit(PEAR);
        balanceTransaction.setQuantity(BALANCE_PEAR_QUANTITY);
        transactions.add(balanceTransaction);

        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(Operation.SUPPLY);
        supplyTransaction.setFruit(PEAR);
        supplyTransaction.setQuantity(SUPPLY_PEAR_QUANTITY);
        transactions.add(supplyTransaction);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(Operation.PURCHASE);
        purchaseTransaction.setFruit(PEAR);
        purchaseTransaction.setQuantity(PURCHASE_PEAR_QUANTITY);
        transactions.add(purchaseTransaction);

        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(Operation.RETURN);
        returnTransaction.setFruit(PEAR);
        returnTransaction.setQuantity(RETURN_PEAR_QUANTITY);
        transactions.add(returnTransaction);

        shopService.process(transactions);
        assertEquals(EXPECTED_PEAR_QUANTITY, storage.getFruitQuantity(PEAR));
    }

}
