package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {

    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
    }

    @Test
    void handle_purchaseOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 20);

        storage.put("banana", 150);
        OperationHandler handler = new PurchaseOperation();

        handler.handle(transaction, storage);

        assertEquals(130, storage.get("banana"));
    }

    @Test
    void handle_supplyOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 20);
        OperationHandler handler = new SupplyOperation();

        handler.handle(transaction, storage);

        assertEquals(20, storage.get("banana"));
    }

    @Test
    void handle_returnOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 20);
        FruitTransaction transaction1 = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "mango", 40);
        OperationHandler handler = new ReturnOperation();

        handler.handle(transaction, storage);
        handler.handle(transaction1, storage);

        assertEquals(20, storage.get("banana"));
        assertEquals(40, storage.get("mango"));
    }

    @Test
    void handle_balanceOperation_ok() {
        final FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "mango", 40);
        final FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 20);
        storage.put("mango", 100);
        storage.put("banana", 200);

        OperationHandler handler = new BalanceOperation();

        handler.handle(transaction, storage);
        handler.handle(transaction1, storage);

        assertEquals(20, storage.get("banana"));
        assertEquals(40, storage.get("mango"));
    }

    @Test
    void handle_balanceOperation_negative_throwException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "mango", -30);

        OperationHandler handler = new BalanceOperation();

        assertThrows(IllegalArgumentException.class, () -> handler.handle(transaction, storage));
    }
}
