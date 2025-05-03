package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.impl.BalanceOperationService;
import core.basesyntax.strategy.impl.IncomingOperationService;
import core.basesyntax.strategy.impl.OutgoingOperationService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationServiceTest {
    private static final String TEST_FRUIT = "banana";
    private static DaoStorage daoStorageForTest;
    private static Map<String, Integer> storageForTest;
    private Map<OperationService, FruitTransaction> testMapTransactions;

    @BeforeAll
    public static void setUp() {
        storageForTest = new HashMap<>();

        daoStorageForTest = new DaoStorage() {
            @Override
            public void setNewValue(String fruit, Integer quantity) {
                storageForTest.put(fruit, quantity);
            }

            @Override
            public void concatenateValue(String fruit, Integer quantity) {
                storageForTest.merge(fruit, quantity, Integer::sum);
            }

            @Override
            public int getValue(String fruit) {
                return storageForTest.get(fruit);
            }

            @Override
            public Set<Map.Entry<String, Integer>> getStatistic() {
                return storageForTest.entrySet();
            }

            @Override
            public void clear() {
            }
        };
    }

    @BeforeEach
    public void beforeTest() {
        testMapTransactions = Map.of(
                new BalanceOperationService(daoStorageForTest),
                new FruitTransaction("b", TEST_FRUIT, 90),
                new IncomingOperationService(daoStorageForTest),
                new FruitTransaction("s", TEST_FRUIT, 50),
                new IncomingOperationService(daoStorageForTest),
                new FruitTransaction("r", TEST_FRUIT, 10),
                new OutgoingOperationService(daoStorageForTest),
                new FruitTransaction("p", TEST_FRUIT, 30));
    }

    @AfterEach
    public void clear() {
        storageForTest.clear();
    }

    @Test
    public void operationService_DaoStorageNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new BalanceOperationService(null));
        assertThrows(IllegalArgumentException.class,
                () -> new IncomingOperationService(null));
        assertThrows(IllegalArgumentException.class,
                () -> new OutgoingOperationService(null));
    }

    @Test
    public void operationService_FruitTransactionNull_notOk() {
        testMapTransactions.keySet().forEach(service ->
                assertThrows(IllegalArgumentException.class, () -> service.calculation(null)));
    }

    @Test
    public void operationService_calculation_Ok() {
        testMapTransactions.forEach((service, transaction) -> {
            int expected = transaction.getQuantity();

            storageForTest.clear();
            if (OutgoingOperationService.class.equals(service.getClass())) {
                storageForTest.put(TEST_FRUIT, expected + expected);
            }
            service.calculation(transaction);

            assertEquals(expected, storageForTest.get(TEST_FRUIT));
        });
    }
}
