package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static ShopServiceImpl shopServiceImpl;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void setUp() {
        fruitTransactions = new ArrayList<>();
        fruitTransactions.clear();
        Storage.quantities.clear();
    }

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopServiceImpl = new ShopServiceImpl(operationStrategy);
        fruitTransactions = new ArrayList<>();
    }

    @Test
    public void process_storageIsNull() {
        ShopServiceImpl shopService
                = new ShopServiceImpl(new OperationStrategyImpl(new HashMap<>()));
        List<FruitTransaction> transactions = new ArrayList<>();
        try {
            shopService.process(transactions, null);
        } catch (NullPointerException e) {
            Assertions.assertEquals("Storage cannot be null", e.getMessage());
        }
    }

    @Test
    public void process_transactionsIsEmpty() {
        ShopServiceImpl shopService
                = new ShopServiceImpl(new OperationStrategyImpl(new HashMap<>()));
        List<FruitTransaction> transactions = new ArrayList<>();
        Storage storage = new Storage();

        try {
            shopService.process(transactions, storage);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Transactions are valid", e.getMessage());
        }
    }

    @Test
    public void process_transactionsAreValid() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "Apple", 10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "Banana", 5));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "Orange", 3));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "Grapes", 2));
        ShopServiceImpl shopService
                = new ShopServiceImpl(new OperationStrategyImpl(operationHandlers));
        Storage storage = new Storage();
        Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(transactions, storage));
    }
}
