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
    private static final int DEFAULT_BALANCE = 100;
    private static Map<String, Integer> storageForTest;
    private static OperationService balanceOperationService;
    private static IncomingOperationService incomingOperationService;
    private static OutgoingOperationService outgoingOperationService;
    private FruitTransaction testTransactionB;
    private FruitTransaction testTransactionS;
    private FruitTransaction testTransactionR;
    private FruitTransaction testTransactionP;

    @BeforeAll
    public static void setUp() {
        storageForTest = new HashMap<>();

        DaoStorage daoStorageForTest = new DaoStorage() {
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
                storageForTest.clear();
            }
        };
        balanceOperationService = new BalanceOperationService(daoStorageForTest);
        incomingOperationService = new IncomingOperationService(daoStorageForTest);
        outgoingOperationService = new OutgoingOperationService(daoStorageForTest);
    }

    @BeforeEach
    public void beforeTest() {
        storageForTest.put(TEST_FRUIT, DEFAULT_BALANCE);
        testTransactionB = new FruitTransaction("b", TEST_FRUIT, 90);
        testTransactionS = new FruitTransaction("s", TEST_FRUIT, 50);
        testTransactionR = new FruitTransaction("r", TEST_FRUIT, 10);
        testTransactionP = new FruitTransaction("p", TEST_FRUIT, 30);
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
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperationService.calculation(null));
        assertThrows(IllegalArgumentException.class,
                () -> incomingOperationService.calculation(null));
        assertThrows(IllegalArgumentException.class,
                () -> outgoingOperationService.calculation(null));
    }

    @Test
    public void operationService_calculation_Ok() {
        balanceOperationService.calculation(testTransactionB);
        int actual = storageForTest.get(TEST_FRUIT);
        assertEquals(testTransactionB.getQuantity(), actual);

        incomingOperationService.calculation(testTransactionS);
        int expected = actual + testTransactionS.getQuantity();
        actual = storageForTest.get(TEST_FRUIT);
        assertEquals(expected, actual);

        incomingOperationService.calculation(testTransactionR);
        expected = actual + testTransactionR.getQuantity();
        actual = storageForTest.get(TEST_FRUIT);
        assertEquals(expected, actual);

        outgoingOperationService.calculation(testTransactionP);
        expected = actual - testTransactionP.getQuantity();
        actual = storageForTest.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }
}
