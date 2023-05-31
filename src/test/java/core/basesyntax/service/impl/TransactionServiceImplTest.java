package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.impl.BalanceOperationHandler;
import core.basesyntax.service.operations.impl.PurchaseOperationHandler;
import core.basesyntax.service.operations.impl.ReturnOperationHandler;
import core.basesyntax.service.operations.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionServiceImplTest {
    private static TransactionService<List<FruitTransaction>> transactionService;
    private static final Map<String, Integer> EXPECTED_STORAGE_DATA = Map.of("apple", 90,
            "banana", 152);
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Transaction list can`t be null";
    private static final Map<String, Integer> EMPTY_STORAGE_DATA = new HashMap<>();
    private static final List<FruitTransaction> EMPTY_TRANSITION_LIST = new ArrayList<>();
    private static final Map<String, Integer> ONE_TRANSITION_STORAGE_DATA = Map.of("banana", 20);
    private static final List<FruitTransaction> ONE_TRANSITION_LIST =
            List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(getOperationHandlerMap());
        transactionService = new TransactionServiceImpl(operationStrategy, fruitStorageDao);

    }

    @Test
    public void transaction_ActualDataEqualsToExpected_ok() {
        transactionService.transaction(getValidFruitTransactionList());
        assertEquals(EXPECTED_STORAGE_DATA, FruitStorage.fruitStorage);
    }

    @Test
    public void transaction_withOneTransition_ok() {
        transactionService.transaction(ONE_TRANSITION_LIST);
        assertEquals(ONE_TRANSITION_STORAGE_DATA, FruitStorage.fruitStorage);
    }

    @Test
    public void transaction_withZeroTransitions_ok() {
        transactionService.transaction(EMPTY_TRANSITION_LIST);
        assertEquals(EMPTY_STORAGE_DATA, FruitStorage.fruitStorage);
    }

    @Test
    public void transaction_nullDataThrowsException_notOk() {
        expectedException.expect(NoSuchElementException.class);
        expectedException.expectMessage(EXPECTED_EXCEPTION_MESSAGE);
        transactionService.transaction(null);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    private static Map<FruitTransaction.Operation, OperationHandler> getOperationHandlerMap() {
        return Map
                .of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    }

    private static List<FruitTransaction> getValidFruitTransactionList() {
        return List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }
}
