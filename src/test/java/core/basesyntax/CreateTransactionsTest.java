package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.CreateTaskService;
import core.basesyntax.services.impl.CreateTaskServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CreateTransactionsTest {
    private static CreateTaskService createTaskServiceTest;

    @BeforeAll
    static void createDataToTransactionsTest() {
        createTaskServiceTest = new CreateTaskServiceImpl();

    }

    @Test
    void transaction_isActionTypeNotCorrect_notOk() {
        List<String[]> actual = new ArrayList<>();
        actual.add(new String[] {"n", "banana", "20"});
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest.createTasks(actual));
    }

    @Test
    void transaction_isNameEmpty_notOk() {
        List<String[]> actual = new ArrayList<>();
        actual.add(new String[] {"b", "", "20"});
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest.createTasks(actual));
    }

    @Test
    void transaction_isValueEmpty_notOk() {
        List<String[]> actual = new ArrayList<>();
        actual.add(new String[] {"b", "banana", ""});
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest.createTasks(actual));
    }

    @Test
    void transaction_isValueLessZero_notOk() {
        List<String[]> actual = new ArrayList<>();
        actual.add(new String[] {"b", "banana", "-4"});
        assertThrows(ValidationDataException.class,
                () -> createTaskServiceTest.createTasks(actual));
    }

    @Test
    void transaction_isOneLineCorrect_ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                "banana", 30);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruit);
        List<String[]> parsedData = new ArrayList<>();
        parsedData.add(new String[] {"b", "banana", "30"});
        List<FruitTransaction> actual = new CreateTaskServiceImpl()
                .createTasks(parsedData);
        assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }

    @Test
    void transaction_isThreeLinesCorrect_ok() {
        FruitTransaction fruitOne = new FruitTransaction(FruitTransaction.ActionType.BALANCE,
                "banana", 40);
        FruitTransaction fruitTwo = new FruitTransaction(FruitTransaction.ActionType.PURCHASE,
                "banana", 10);
        FruitTransaction fruitThree = new FruitTransaction(FruitTransaction.ActionType.SUPPLY,
                "banana", 13);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(fruitOne);
        expected.add(fruitTwo);
        expected.add(fruitThree);
        List<String[]> parsedData = new ArrayList<>();
        parsedData.add(new String[] {"b", "banana", "40"});
        parsedData.add(new String[] {"p", "banana", "10"});
        parsedData.add(new String[] {"s", "banana", "13"});
        List<FruitTransaction> actual = new CreateTaskServiceImpl()
                .createTasks(parsedData);
        assertTrue(Arrays.deepEquals(expected.toArray(), actual.toArray()));
    }
}
