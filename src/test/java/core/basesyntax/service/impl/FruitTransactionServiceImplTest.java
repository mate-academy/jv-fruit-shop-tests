package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static FruitTransactionService fruitTransactionService;
    private static List<String> activities;
    private static List<FruitTransaction> transactions;
    private static List<String> activitiesWithWrongValues;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionService = new FruitTransactionServiceImpl();
        activities = new ArrayList<>();
        activities.add("\"type\",\"fruit\",\"quantity\"");
        activities.add("\"b\",\"banana\",\"20\"");
        activities.add("\"b\",\"apple\",\"100\"");
        activities.add("\"s\",\"banana\",\"100\"");
        activities.add("\"p\",\"banana\",\"13\"");
        activities.add("\"r\",\"apple\",\"10\"");
        activities.add("\"p\",apple,\"20\"");
        activities.add("\"p\",\"banana\",\"5\"");
        activities.add("\"s\",\"banana\",\"50\"");
    }

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.BALANCE, new Fruit("banana"), 20));
        transactions.add(new FruitTransaction(Operation.BALANCE, new Fruit("apple"), 100));
        transactions.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana"), 100));
        transactions.add(new FruitTransaction(Operation.PURCHASE, new Fruit("banana"), 13));
        transactions.add(new FruitTransaction(Operation.RETURN, new Fruit("apple"), 10));
        transactions.add(new FruitTransaction(Operation.PURCHASE, new Fruit("apple"), 20));
        transactions.add(new FruitTransaction(Operation.PURCHASE, new Fruit("banana"), 5));
        transactions.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana"), 50));
    }

    @Test
    void getFruitTransactionsFromData_ok() {
        List<FruitTransaction> actual = fruitTransactionService
                .getFruitTransactionsFromData(activities);
        List<FruitTransaction> expected = transactions;
        assertEquals(expected, actual);
    }

    @Test
    void getFruitTransactionsFromData_notCorrectExpectedList_notOk() {
        transactions.clear();
        List<FruitTransaction> actual = fruitTransactionService
                .getFruitTransactionsFromData(activities);
        List<FruitTransaction> expected = transactions;
        assertNotEquals(expected, actual);

        transactions.add(new FruitTransaction(Operation.PURCHASE,
                new Fruit("banana"), 5));
        assertNotEquals(expected, actual);
    }

    @Test
    void getFruitTransactionsFromData_incorrectActivity_notOk() {
        activitiesWithWrongValues = new ArrayList<>();
        activitiesWithWrongValues.add("\"type\",\"fruit\",\"quantity\"");
        activitiesWithWrongValues.add("\"type\",\"banana\",\"50\"");
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService
                        .getFruitTransactionsFromData(activitiesWithWrongValues));

        activitiesWithWrongValues.clear();
        activitiesWithWrongValues.add("\"type\",\"fruit\",\"quantity\"");
        activitiesWithWrongValues.add("\"b\",\"apple\",\"quantity\"");
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService
                        .getFruitTransactionsFromData(activitiesWithWrongValues));
        activitiesWithWrongValues.clear();
        activitiesWithWrongValues.add("\"type\",\"fruit\",\"quantity\"");
        activitiesWithWrongValues.add("\"s\",\"apple\",\"-5\"");
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService
                        .getFruitTransactionsFromData(activitiesWithWrongValues));
    }

    @AfterEach
    void tearDown() {
        transactions.clear();
    }
}
