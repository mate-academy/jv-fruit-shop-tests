package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import database.Storage;
import database.StorageDealer;
import database.StorageDealerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import strategy.BalanceOperation;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;
import strategy.PurchaseOperation;
import strategy.ReturnOperation;
import strategy.SupplyOperation;

class ShopServiceTest {
    private static final String PROPER_HEADER = "type,fruit,quantity";
    private static final String FIRST_PROPER_LINE = "b,apple,32";
    private static final String SECOND_PROPER_LINE = "b,banana,12";
    private static final String THIRD_PROPER_LINE = "s,orange,56";
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;
    private static Map<String, Integer> expectedStorage;
    private static List<FruitTransaction> transactionList;
    private static StorageDealer storageDealer;
    private FruitTransaction firstBalanceTransaction;
    private FruitTransaction secondBalanceTransaction;
    private FruitTransaction firstPurchaseTransaction;
    private FruitTransaction secondPurchaseTransaction;
    private FruitTransaction firstSupplyTransaction;
    private FruitTransaction secondSupplyTransaction;
    private FruitTransaction firstReturnTransaction;
    private FruitTransaction secondReturnTransaction;
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        handlers = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation());
        storageDealer = new StorageDealerImpl();
        expectedStorage = new HashMap<>();
        transactionList = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(
                operationStrategy, storageDealer);
        firstBalanceTransaction = new FruitTransaction("b", "apple", 32);
        secondBalanceTransaction = new FruitTransaction("b", "banana", 12);
        firstPurchaseTransaction = new FruitTransaction("p", "apple", 6);
        secondPurchaseTransaction = new FruitTransaction("p", "banana", 9);
        firstSupplyTransaction = new FruitTransaction("s", "apple", 13);
        secondSupplyTransaction = new FruitTransaction("s", "banana", 21);
        firstReturnTransaction = new FruitTransaction("r", "apple", 4);
        secondReturnTransaction = new FruitTransaction("r", "banana", 8);

    }

    @AfterEach
    void tearDown() {
        Storage.clearStorage();
        expectedStorage.clear();
        transactionList.clear();
    }

    @Test
    void performBalanceTransaction() {
        setBalance();
        Map<String, Integer> actualStorage = Storage.getAssortment();
        expectedStorage.put(firstBalanceTransaction.getFruit(),
                firstBalanceTransaction.getQuantity());
        expectedStorage.put(secondBalanceTransaction.getFruit(),
                secondBalanceTransaction.getQuantity());
        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void performPurchaseTransaction() {
        setBalance();
        transactionList.clear();

        transactionList.add(firstPurchaseTransaction);
        transactionList.add(secondPurchaseTransaction);
        shopService.process(transactionList);
        Map<String, Integer> actualStorage = Storage.getAssortment();

        int firstQuantityToAdd = firstBalanceTransaction.getQuantity()
                - firstPurchaseTransaction.getQuantity();
        int secondQuantityToAdd = secondBalanceTransaction.getQuantity()
                - secondPurchaseTransaction.getQuantity();

        expectedStorage.put(firstPurchaseTransaction.getFruit(), firstQuantityToAdd);
        expectedStorage.put(secondPurchaseTransaction.getFruit(), secondQuantityToAdd);
        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void performSupplyOperation() {
        setBalance();
        transactionList.clear();

        transactionList.add(firstSupplyTransaction);
        transactionList.add(secondSupplyTransaction);
        shopService.process(transactionList);
        Map<String, Integer> actualStorage = Storage.getAssortment();

        int firstQuantityToAdd = firstBalanceTransaction.getQuantity()
                + firstSupplyTransaction.getQuantity();
        int secondQuantityToAdd = secondBalanceTransaction.getQuantity()
                + secondSupplyTransaction.getQuantity();

        expectedStorage.put(firstSupplyTransaction.getFruit(), firstQuantityToAdd);
        expectedStorage.put(secondSupplyTransaction.getFruit(), secondQuantityToAdd);
        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void performReturnOperation() {
        setBalance();
        transactionList.clear();

        transactionList.add(firstReturnTransaction);
        transactionList.add(secondReturnTransaction);
        shopService.process(transactionList);
        Map<String, Integer> actualStorage = Storage.getAssortment();

        int firstQuantityToAdd = firstBalanceTransaction.getQuantity()
                + firstReturnTransaction.getQuantity();
        int secondQuantityToAdd = secondBalanceTransaction.getQuantity()
                + secondReturnTransaction.getQuantity();

        expectedStorage.put(firstReturnTransaction.getFruit(), firstQuantityToAdd);
        expectedStorage.put(secondReturnTransaction.getFruit(), secondQuantityToAdd);
        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void tryProvokeNegativeBalance() {
        setBalance();
        assertThrows(RuntimeException.class,
                () -> storageDealer.updateDatabase("apple", -33));
    }

    private void setBalance() {
        transactionList.add(firstBalanceTransaction);
        transactionList.add(secondBalanceTransaction);
        shopService.process(transactionList);
    }
}
