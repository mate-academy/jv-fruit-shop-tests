package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitDatabase;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.OperationStrategySupplierImpl;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.impl.BalanceOperationStrategy;
import core.basesyntax.service.operation.impl.PurchaseOperationStrategy;
import core.basesyntax.service.operation.impl.ReturnOperationStrategy;
import core.basesyntax.service.operation.impl.SupplyOperationStrategy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
    private static FruitService fruitService;

    @BeforeAll
    public static void initFruitService() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationStrategy> strategyMap =
                Map.of(FruitTransaction.Operation.BALANCE,
                        new BalanceOperationStrategy(fruitDao),
                        FruitTransaction.Operation.SUPPLY,
                        new SupplyOperationStrategy(fruitDao),
                        FruitTransaction.Operation.PURCHASE,
                        new PurchaseOperationStrategy(fruitDao),
                        FruitTransaction.Operation.RETURN,
                        new ReturnOperationStrategy(fruitDao));

        OperationStrategySupplier operationSupplier =
                new OperationStrategySupplierImpl(strategyMap);
        fruitService = new FruitServiceImpl(operationSupplier);
    }

    @BeforeEach
    public void fillDatabase() {
        FruitDatabase.database.put("Orange", 100);
        FruitDatabase.database.put("Banana", 120);
        FruitDatabase.database.put("Apple", 10);
    }

    @AfterEach
    public void cleanUpDatabase() {
        FruitDatabase.database.clear();
    }

    @Test
    public void performFruitsOperations_performNoTransactions_ok() {
        Map<String, Integer> databaseBeforePerforming =
                new HashMap<>(FruitDatabase.database);
        fruitService.performFruitsOperations(List.of());
        assertEquals(databaseBeforePerforming, FruitDatabase.database);
    }

    @Test
    public void performFruitOperations_performTransactions_ok() {
        HashMap<String, Integer> expectedDatabase = new HashMap<>();
        expectedDatabase.put("Orange", 120);
        expectedDatabase.put("Banana", 70);
        expectedDatabase.put("Apple", 18);
        List<FruitTransaction> transactions =
                List.of(
                        new FruitTransaction(
                                FruitTransaction.Operation.PURCHASE,
                                "Banana", 50),
                        new FruitTransaction(
                                FruitTransaction.Operation.RETURN,
                                "Apple", 8),
                        new FruitTransaction(
                                FruitTransaction.Operation.SUPPLY,
                                "Orange", 20));
        fruitService.performFruitsOperations(transactions);
        assertEquals(expectedDatabase, FruitDatabase.database);
    }

    @Test
    public void performFruitsOperations_nullValue_notOk() {
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitService.performFruitsOperations(null));
        assertEquals("Can't perform operations with null list",
                actualException.getMessage());
    }

    @Test
    public void performFruitsOperations_transactionListWithNull_notOk() {
        List<FruitTransaction> transactionsWithNull =
                Arrays.asList(
                        new FruitTransaction(
                                FruitTransaction.Operation.PURCHASE,
                                "Banana", 50),
                        null,
                        new FruitTransaction(
                                FruitTransaction.Operation.SUPPLY,
                                "Orange", 20));
        Exception actualException = assertThrows(IllegalArgumentException.class,
                () -> fruitService.performFruitsOperations(transactionsWithNull));
        assertEquals("Can't operate null value transactions",
                actualException.getMessage());
    }
}
