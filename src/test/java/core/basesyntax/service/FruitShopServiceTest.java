package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceTest {

    private FruitShopService fruitShopService;

    @Before
    public void setUp() {
        Map<OperationType, OperationHandler> operationStrategy = new HashMap<>();
        operationStrategy.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationStrategy.put(OperationType.RETURN, new ReturnOperationHandler());
        operationStrategy.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationStrategy.put(OperationType.BALANCE, new BalanceOperationHandler());
        TransactionProcessor transactionProcessor = new TransactionProcessor(operationStrategy);
        fruitShopService = new FruitShopService(transactionProcessor);
    }

    @Test
    public void processTransactions_purchaseOperation_updatesInventoryCorrectly() {
        FruitTransaction transactionBalance = new FruitTransaction(OperationType.BALANCE,
                "Apple", 100);
        List<FruitTransaction> transactionsBalance
                = Collections.singletonList(transactionBalance);
        fruitShopService.processTransactions(transactionsBalance);
        FruitTransaction transactionPurchase = new FruitTransaction(OperationType.PURCHASE,
                "Apple", 10);
        List<FruitTransaction> transactionsPurchase
                = Collections.singletonList(transactionPurchase);
        fruitShopService.processTransactions(transactionsPurchase);
        Map<String, Integer> inventory = fruitShopService.getInventory();
        assertEquals("Apple quantity should be 90",
                Integer.valueOf(90), inventory.get("Apple"));
    }

    @Test
    public void processTransactions_returnOperation_updatesInventoryCorrectly() {
        FruitTransaction transaction = new FruitTransaction(OperationType.RETURN, "Apple", 5);
        List<FruitTransaction> transactions = Collections.singletonList(transaction);
        fruitShopService.processTransactions(transactions);
        Map<String, Integer> inventory = fruitShopService.getInventory();
        assertEquals("Apple quantity should be 5",
                Integer.valueOf(5), inventory.get("Apple"));
    }

    @Test
    public void processTransactions_supplyOperation_updatesInventoryCorrectly() {
        FruitTransaction transaction = new FruitTransaction(OperationType.SUPPLY,
                "Banana", 15);
        List<FruitTransaction> transactions = Collections.singletonList(transaction);
        fruitShopService.processTransactions(transactions);
        Map<String, Integer> inventory = fruitShopService.getInventory();
        assertEquals("Banana quantity should be 15",
                Integer.valueOf(15), inventory.get("Banana"));
    }

    @Test
    public void processTransactions_balanceOperation_setsExactInventoryQuantity() {
        FruitTransaction transaction = new FruitTransaction(OperationType.BALANCE,
                "Orange", 50);
        List<FruitTransaction> transactions = Collections.singletonList(transaction);
        fruitShopService.processTransactions(transactions);
        Map<String, Integer> inventory = fruitShopService.getInventory();
        assertEquals("Orange quantity should be 50",
                Integer.valueOf(50), inventory.get("Orange"));
    }

    @Test
    public void processTransactions_multipleOperations_processesCorrectly() {
        FruitTransaction transaction1 = new FruitTransaction(OperationType.BALANCE,
                "Apple", 0);
        FruitTransaction transaction2 = new FruitTransaction(OperationType.SUPPLY,
                "Apple", 10);
        FruitTransaction transaction3 = new FruitTransaction(OperationType.PURCHASE,
                "Apple", 10);
        FruitTransaction transaction4 = new FruitTransaction(OperationType.RETURN,
                "Apple", 5);
        List<FruitTransaction> transactions = Arrays.asList(transaction1, transaction2,
                transaction3, transaction4);
        fruitShopService.processTransactions(transactions);
        Map<String, Integer> inventory = fruitShopService.getInventory();
        assertEquals("Apple quantity should be 5",
                Integer.valueOf(5), inventory.get("Apple"));
    }

    @Test
    public void processTransactions_emptyList_doesNotChangeInventory() {
        Map<String, Integer> initialInventory = fruitShopService.getInventory();
        Map<String, Integer> expectedInventory = new HashMap<>(initialInventory);
        List<FruitTransaction> transactions = Collections.emptyList();
        fruitShopService.processTransactions(transactions);
        Map<String, Integer> inventoryAfterProcessing = fruitShopService.getInventory();
        assertEquals("Inventory should remain unchanged",
                expectedInventory, inventoryAfterProcessing);
    }
}
