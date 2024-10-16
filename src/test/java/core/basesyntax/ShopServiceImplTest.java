package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.StorageService;
import core.basesyntax.db.StorageServiceImpl;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private StorageService storageService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageService));
        handlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageService));
        handlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageService));
        handlers.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageService));

        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    public void process_BalanceAndSupply_Ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("apple");
        balanceTransaction.setAmount(50);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(balanceTransaction);

        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit("apple");
        supplyTransaction.setAmount(30);
        transactions.add(supplyTransaction);

        shopService.process(transactions);

        Map<String, Integer> storage = storageService.getStorage();
        assertEquals(80, storage.get("apple").intValue());
    }

    @Test
    public void process_Purchase_Ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("orange");
        balanceTransaction.setAmount(100);

        FruitTransaction purchaseTransaction = new FruitTransaction();
        purchaseTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseTransaction.setFruit("orange");
        purchaseTransaction.setAmount(40);

        List<FruitTransaction> transactions = List.of(balanceTransaction, purchaseTransaction);
        shopService.process(transactions);

        Map<String, Integer> storage = storageService.getStorage();
        assertEquals(60, storage.get("orange").intValue());
    }

    @Test
    public void process_Return_Ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("banana");
        balanceTransaction.setAmount(30);

        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("banana");
        returnTransaction.setAmount(10);

        List<FruitTransaction> transactions = List.of(balanceTransaction, returnTransaction);
        shopService.process(transactions);

        Map<String, Integer> storage = storageService.getStorage();
        assertEquals(40, storage.get("banana").intValue());
    }
}
