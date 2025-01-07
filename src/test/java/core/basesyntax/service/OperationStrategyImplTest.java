package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final String TEST_STORAGE_KEY = "TEST";
    private static final int DEFAULT_VALUE_QUANTITY = 50;
    private static final int BALANCE_OPERATION_QUANTITY = 50;
    private static final int PURCHASE_OPERATION_QUANTITY = 15;
    private static final int RETURN_OPERATION_QUANTITY = 25;
    private static final int SUPPLY_OPERATION_QUANTITY = 5;
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
    private static final OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

    @BeforeAll
    static void beforeAll() {
        operationHandlers.clear();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
    }

    @BeforeEach
    void before() {
        Storage.getCalculatedTransactions().put(TEST_STORAGE_KEY,DEFAULT_VALUE_QUANTITY);
    }

    @AfterAll
    static void afterAll() {
        Storage.getCalculatedTransactions().remove(TEST_STORAGE_KEY);
    }

    @Test
    void makeOperationBalance_Ok() {
        operationStrategy.makeOperation(
                FruitTransaction.Operation.BALANCE,
                TEST_STORAGE_KEY, BALANCE_OPERATION_QUANTITY);
        assertEquals(BALANCE_OPERATION_QUANTITY, Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }

    @Test
    void makeOperationSupply_Ok() {
        operationStrategy.makeOperation(
                FruitTransaction.Operation.SUPPLY,
                TEST_STORAGE_KEY, SUPPLY_OPERATION_QUANTITY);
        assertEquals(DEFAULT_VALUE_QUANTITY + SUPPLY_OPERATION_QUANTITY,
                Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }

    @Test
    void makeOperationPurchase_Ok() {
        operationStrategy.makeOperation(
                FruitTransaction.Operation.PURCHASE,
                TEST_STORAGE_KEY, PURCHASE_OPERATION_QUANTITY);
        assertEquals(DEFAULT_VALUE_QUANTITY - PURCHASE_OPERATION_QUANTITY,
                Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }

    @Test
    void makeOperationReturn_Ok() {
        operationStrategy.makeOperation(
                FruitTransaction.Operation.RETURN,
                TEST_STORAGE_KEY, RETURN_OPERATION_QUANTITY);
        assertEquals(DEFAULT_VALUE_QUANTITY + RETURN_OPERATION_QUANTITY,
                Storage.getCalculatedTransactions().get(TEST_STORAGE_KEY));
    }
}
