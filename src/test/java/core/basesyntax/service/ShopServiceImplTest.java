package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> OPERATION_HANDLERS
            = new HashMap<>();
    private static final Map<String, Integer> EXPECTED_RESULT = new HashMap<>();
    private static final List<FruitTransaction> FRUIT_TRANSACTION_LIST = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 15),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 12),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 4),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 2)
    );
    private static final OperationHandler BALANCE_OPERATION_HANDLER
            = new BalanceOperationHandler();
    private static final OperationHandler PURCHASE_OPERATION_HANDLER
            = new PurchaseOperationHandler();
    private static final OperationHandler RETURN_OPERATION_HANDLER
            = new ReturnOperationHandler();
    private static final OperationHandler SUPPLY_OPERATION_HANDLER
            = new SupplyOperationHandler();
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategy(OPERATION_HANDLERS);
        OPERATION_HANDLERS.put(FruitTransaction.Operation.BALANCE, BALANCE_OPERATION_HANDLER);
        OPERATION_HANDLERS.put(FruitTransaction.Operation.PURCHASE, PURCHASE_OPERATION_HANDLER);
        OPERATION_HANDLERS.put(FruitTransaction.Operation.RETURN, RETURN_OPERATION_HANDLER);
        OPERATION_HANDLERS.put(FruitTransaction.Operation.SUPPLY, SUPPLY_OPERATION_HANDLER);

        EXPECTED_RESULT.put("banana", 24);
        EXPECTED_RESULT.put("apple", 11);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        OPERATION_HANDLERS.clear();
        EXPECTED_RESULT.clear();
    }

    @Test
    void process_isOk() {
        Map<String, Integer> actual = shopService.process(FRUIT_TRANSACTION_LIST);
        assertEquals(EXPECTED_RESULT, actual);
    }

    @Test
    void process_nullValue_notOk() {
        assertThrows(IllegalArgumentException.class, () -> shopService.process(null));
    }
}
