package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static FruitTransactionService transactionService;
    private static final List<FruitTransaction> TEST_TRANSACTIONS = new ArrayList<>();
    private static final List<FruitTransaction> EMPTY_TRANSACTIONS = new ArrayList<>();
    private static final List<FruitTransaction> NULL_DATA_TRANSACTIONS = new ArrayList<>();
    private static final List<FruitTransaction> NULL_TRANSACTIONS = new ArrayList<>();
    private static final Map<Operation, OperationHandler> strategyMap = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        transactionService = new FruitTransactionServiceImpl();
        TEST_TRANSACTIONS.add(new FruitTransaction("test1", 5, Operation.BALANCE));
        TEST_TRANSACTIONS.add(new FruitTransaction("test1", 2, Operation.PURCHASE));
        TEST_TRANSACTIONS.add(new FruitTransaction("test2", 4, Operation.SUPPLY));
        TEST_TRANSACTIONS.add(new FruitTransaction("test2", 10, Operation.RETURN));
        NULL_DATA_TRANSACTIONS.add(new FruitTransaction(null, null, null));
        NULL_TRANSACTIONS.add(null);
        strategyMap.put(Operation.BALANCE, new BalanceOperationHandler());
        strategyMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        strategyMap.put(Operation.RETURN, new ReturnOperationHandler());
        strategyMap.put(Operation.SUPPLY, new SupplyOperationHandler());
    }

    @Test
    public void execute_writeEntriesToDB_Ok() {
        transactionService.execute(TEST_TRANSACTIONS, strategyMap);
        Integer actualQuantity1 = Storage.fruitStorage.get("test1");
        Integer actualQuantity2 = Storage.fruitStorage.get("test2");

        assertEquals(Integer.valueOf(3), actualQuantity1);
        assertEquals(Integer.valueOf(14), actualQuantity2);
    }

    @Test
    public void execute_writeEmpty_Ok() {
        transactionService.execute(EMPTY_TRANSACTIONS, strategyMap);
        boolean actual = Storage.fruitStorage.isEmpty();
        assertTrue(actual);
    }

    @Test
    public void execute_writeNullEntries_Ok() {
        transactionService.execute(NULL_DATA_TRANSACTIONS, strategyMap);
        Integer actualQuantity = Storage.fruitStorage.get(null);
        assertEquals(Integer.valueOf(0), actualQuantity);
    }

    @Test
    public void execute_null_Ok() {
        transactionService.execute(NULL_TRANSACTIONS, strategyMap);
        Integer actualQuantity = Storage.fruitStorage.get(null);
        assertEquals(Integer.valueOf(0), actualQuantity);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
