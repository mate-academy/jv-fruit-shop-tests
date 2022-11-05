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
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionServiceImplTest {
    private static TransactionService transactionService;
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map
            .of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private static final List<FruitTransaction> VALID_FRUIT_TRANSACTION_LIST = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    private static final Map<String, Integer> EXPECTED_STORAGE_DATA = Map.of("apple", 90,
            "banana", 152);
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionService = new TransactionServiceImpl(operationStrategy, fruitStorageDao);

    }

    @Test
    public void transaction_ActualDataEqualsToExpected_ok() {
        transactionService.transaction(VALID_FRUIT_TRANSACTION_LIST);
        Set<Map.Entry<String, Integer>> actualStorageData = FruitStorage.fruitStorage.entrySet();
        assertEquals(EXPECTED_STORAGE_DATA.entrySet(), actualStorageData);
    }

    @Test
    public void transaction_nullDataThrowsException_notOk() {
        expectedException.expect(NoSuchElementException.class);
        transactionService.transaction(null);
    }

    @Test
    public void transaction_nullDataExceptionMessage_ok() {
        expectedException.expectMessage("Transaction list can`t be null");
        transactionService.transaction(null);
    }
}
