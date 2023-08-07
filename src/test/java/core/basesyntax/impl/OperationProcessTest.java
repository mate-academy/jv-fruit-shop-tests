package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcesser;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationProcessTest {
    private static DataProcesser dataProcesser;
    private static List<FruitTransaction> fruitTransactionList;
    private static final Map<Operation, OperationHandler> OPERATION_HANDLER_MAP = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(),
            Operation.PURCHASE, new PurchaseOperationHandler(),
            Operation.RETURN, new ReturnOperationHandler(),
            Operation.SUPPLY, new SupplyOperationHandler());

    @BeforeAll
    static void setUp() {
        dataProcesser = new OperationProcess();
    }

    @Test
    void process_ValidTransactions_Ok() {
        fruitTransactionList = List.of(new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 5),
                new FruitTransaction(Operation.SUPPLY, "apple", 3),
                new FruitTransaction(Operation.RETURN, "apple", 2));
        dataProcesser.processData(fruitTransactionList, OPERATION_HANDLER_MAP);
        assertEquals(10, Storage.getStorage().get("apple"));
    }

    @Test
    void process_NullTransactions_NotOk() {
        fruitTransactionList = null;
        assertThrows(RuntimeException.class,
                () -> dataProcesser.processData(fruitTransactionList, OPERATION_HANDLER_MAP),
                "Transactions must not be null");
    }
}
