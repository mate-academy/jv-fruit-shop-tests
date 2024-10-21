package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import database.Storage;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static Map<String, Integer> expectedStorage;
    private static OperationHandler balanceHandler;
    private static OperationHandler purchaseHandler;
    private static OperationHandler supplyHandler;
    private static OperationHandler returnHandler;
    private FruitTransaction firstBalanceTransaction;
    private FruitTransaction secondBalanceTransaction;
    private FruitTransaction firstPurchaseTransaction;
    private FruitTransaction secondPurchaseTransaction;
    private FruitTransaction firstSupplyTransaction;
    private FruitTransaction secondSupplyTransaction;
    private FruitTransaction firstReturnTransaction;
    private FruitTransaction secondReturnTransaction;
    private FruitTransaction transactionToMakeBalanceNegative;

    @BeforeAll
    static void beforeAll() {
        expectedStorage = new HashMap<>();
        balanceHandler = new BalanceOperation();
        purchaseHandler = new PurchaseOperation();
        supplyHandler = new SupplyOperation();
        returnHandler = new ReturnOperation();
    }

    @BeforeEach
    void setUp() {
        firstBalanceTransaction = new FruitTransaction("b", "apple", 32);
        secondBalanceTransaction = new FruitTransaction("b", "banana", 12);
        firstPurchaseTransaction = new FruitTransaction("p", "apple", 6);
        secondPurchaseTransaction = new FruitTransaction("p", "banana", 9);
        firstSupplyTransaction = new FruitTransaction("s", "apple", 13);
        secondSupplyTransaction = new FruitTransaction("s", "banana", 21);
        firstReturnTransaction = new FruitTransaction("r", "apple", 4);
        secondReturnTransaction = new FruitTransaction("r", "banana", 8);
        transactionToMakeBalanceNegative = new FruitTransaction(
                "p", "apple", 100);
        setBalance();
    }

    @AfterEach
    void tearDown() {
        Storage.clearStorage();
        expectedStorage.clear();
    }

    @Test
    void updateDatabase_balance_ok() {
        Map<String, Integer> actualStorage = Storage.getAssortment();
        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void updateDatabase_purchase_ok() {
        int firstQuantityToAdd = firstBalanceTransaction.getQuantity()
                - firstPurchaseTransaction.getQuantity();
        int secondQuantityToAdd = secondBalanceTransaction.getQuantity()
                - secondPurchaseTransaction.getQuantity();

        expectedStorage.put(firstPurchaseTransaction.getFruit(), firstQuantityToAdd);
        expectedStorage.put(secondPurchaseTransaction.getFruit(), secondQuantityToAdd);

        purchaseHandler.updateDatabase(firstPurchaseTransaction);
        purchaseHandler.updateDatabase(secondPurchaseTransaction);
        Map<String, Integer> actualStorage = Storage.getAssortment();

        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void updateDatabase_supply_ok() {
        int firstQuantityToAdd = firstBalanceTransaction.getQuantity()
                + firstSupplyTransaction.getQuantity();
        int secondQuantityToAdd = secondBalanceTransaction.getQuantity()
                + secondSupplyTransaction.getQuantity();

        expectedStorage.put(firstSupplyTransaction.getFruit(), firstQuantityToAdd);
        expectedStorage.put(secondSupplyTransaction.getFruit(), secondQuantityToAdd);

        supplyHandler.updateDatabase(firstSupplyTransaction);
        supplyHandler.updateDatabase(secondSupplyTransaction);
        Map<String, Integer> actualStorage = Storage.getAssortment();

        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void updateDatabase_return_ok() {
        int firstQuantityToAdd = firstBalanceTransaction.getQuantity()
                + firstReturnTransaction.getQuantity();
        int secondQuantityToAdd = secondBalanceTransaction.getQuantity()
                + secondReturnTransaction.getQuantity();

        expectedStorage.put(firstReturnTransaction.getFruit(), firstQuantityToAdd);
        expectedStorage.put(secondReturnTransaction.getFruit(), secondQuantityToAdd);

        returnHandler.updateDatabase(firstReturnTransaction);
        returnHandler.updateDatabase(secondReturnTransaction);
        Map<String, Integer> actualStorage = Storage.getAssortment();

        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void updateDatabase_makeBalanceNegative_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.updateDatabase(
                        transactionToMakeBalanceNegative));
    }

    private void setBalance() {
        expectedStorage.put(firstBalanceTransaction.getFruit(),
                firstBalanceTransaction.getQuantity());
        expectedStorage.put(secondBalanceTransaction.getFruit(),
                secondBalanceTransaction.getQuantity());

        balanceHandler.updateDatabase(firstBalanceTransaction);
        balanceHandler.updateDatabase(secondBalanceTransaction);
    }
}
