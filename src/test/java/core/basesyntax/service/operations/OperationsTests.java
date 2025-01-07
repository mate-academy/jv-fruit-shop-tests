package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationsTests {
    private static final String TEST_STORAGE_KEY = "TEST";
    private static final int DEFAULT_VALUE_QUANTITY = 50;
    private static final int BALANCE_OPERATION_QUANTITY = 50;
    private static final int PURCHASE_OPERATION_QUANTITY = 15;
    private static final int RETURN_OPERATION_QUANTITY = 25;
    private static final int SUPPLY_OPERATION_QUANTITY = 5;
    private BalanceOperation balanceOperation = new BalanceOperation();
    private PurchaseOperation purchaseOperation = new PurchaseOperation();
    private ReturnOperation returnOperation = new ReturnOperation();
    private SupplyOperation supplyOperation = new SupplyOperation();

    @BeforeEach
    void before() {
        Storage.getCalculatedTransactions().put(TEST_STORAGE_KEY,DEFAULT_VALUE_QUANTITY);
    }

    @AfterAll
    static void afterAll() {
        Storage.getCalculatedTransactions().remove(TEST_STORAGE_KEY);
    }

    @Test
    void balanceOperation_Ok() {
        balanceOperation.doOperation(TEST_STORAGE_KEY, BALANCE_OPERATION_QUANTITY);
        assertEquals(BALANCE_OPERATION_QUANTITY,
                Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }

    @Test
    void purchaseOperation_Ok() {
        purchaseOperation.doOperation(TEST_STORAGE_KEY, PURCHASE_OPERATION_QUANTITY);
        assertEquals(DEFAULT_VALUE_QUANTITY - PURCHASE_OPERATION_QUANTITY,
                Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }

    @Test
    void returnOperation_Ok() {
        returnOperation.doOperation(TEST_STORAGE_KEY, RETURN_OPERATION_QUANTITY);
        assertEquals(DEFAULT_VALUE_QUANTITY + RETURN_OPERATION_QUANTITY,
                Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }

    @Test
    void supplyOperation_Ok() {
        supplyOperation.doOperation(TEST_STORAGE_KEY, SUPPLY_OPERATION_QUANTITY);
        assertEquals(DEFAULT_VALUE_QUANTITY + SUPPLY_OPERATION_QUANTITY,
                Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }
}
