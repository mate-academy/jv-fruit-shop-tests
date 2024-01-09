package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.operation.BalanceInputOperation;
import core.basesyntax.service.operation.InputOperation;
import core.basesyntax.service.operation.PurchaseInputOperation;
import core.basesyntax.service.operation.ReturnInputOperation;
import core.basesyntax.service.operation.SupplyInputOperation;
import core.basesyntax.service.strategy.InputOperationStrategyImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;
    private static List<FruitTransaction> validParsedData;

    @BeforeAll
    static void beforeAll() {
        OperationStrategy operationStrategy = new InputOperationStrategyImpl(getMapForStragety());
        fruitService = new FruitServiceImpl(operationStrategy);
        validParsedData = new ArrayList<>();
        validParsedData.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20)
        );
        validParsedData.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                100)
        );
        validParsedData.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "watermelon",
                30)
        );
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void processTransactions_validTrastactionsData_Ok() {
        fruitService.processTransactions(validParsedData);
        Map<String, Integer> expected = Map.of("banana", 20,
                "apple", 100,
                "watermelon", 30);
        Map<String, Integer> actualMap = Storage.fruitsStorage;
        for (Map.Entry<String, Integer> expectedEntry : expected.entrySet()) {
            Integer actual = actualMap.get(expectedEntry.getKey());
            assertEquals(expectedEntry.getValue(), actual);
        }
    }

    @Test
    void processTransactions_validSingleData_Ok() {
        fruitService.processTransactions(
                List.of(new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, "banana", 20)));
        Map<String, Integer> expected = Map.of("banana", 20);
        Map<String, Integer> actual = Storage.fruitsStorage;
        assertEquals(expected, actual);
    }

    @Test
    void processTransactions_TransactionsIsNull_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            fruitService.processTransactions(null);
        });
        String expected = "Fruit Transactions must not be null";
        assertEquals(expected, runtimeException.getMessage());
    }

    private static Map<FruitTransaction.Operation, InputOperation> getMapForStragety() {
        FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoImpl();
        return Map.of(
                FruitTransaction.Operation.BALANCE,
                new BalanceInputOperation(fruitTransactionDao),
                FruitTransaction.Operation.SUPPLY,
                new SupplyInputOperation(fruitTransactionDao),
                FruitTransaction.Operation.PURCHASE,
                new PurchaseInputOperation(fruitTransactionDao),
                FruitTransaction.Operation.RETURN,
                new ReturnInputOperation(fruitTransactionDao)
        );
    }
}
