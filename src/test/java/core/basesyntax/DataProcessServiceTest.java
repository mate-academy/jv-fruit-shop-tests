package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessService;
import core.basesyntax.service.impl.DataProcessServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataProcessServiceTest {
    private static DataProcessService dataProcessService;

    @BeforeClass
    public static void beforeClass() {
        dataProcessService = new DataProcessServiceImpl();
    }

    @Test
    public void processData_validInput_ok() {
        List<String> inputList = new ArrayList<>(List.of(
                "type,fruit,quantity", "b,orange,20", "b,pineapple,10",
                "s,orange,10", "p,orange,13", "r,apple,20", "p,pineapple,5", "s,pineapple,50"));
        List<FruitTransaction> expectedList = generateFruitTransactionList();
        List<FruitTransaction> actualList = dataProcessService.processData(inputList);
        Assert.assertEquals(
                String.format("Incorrect result of FruitTransaction List for the input data:\n%s.",
                        inputList), expectedList, actualList);
        Assert.assertEquals("Incorrect size of FruitTransaction List. Should be: ",
                expectedList.size(), actualList.size());
    }

    @Test(expected = RuntimeException.class)
    public void processData_invalidInput_notOk() {
        List<String> invalidInputList = new ArrayList<>(List.of("Hello, world!", "Hello, mate!"));
        dataProcessService.processData(invalidInputList);
        Assert.assertTrue("You should throw an Exception with explanation", true);
    }

    @Test(expected = RuntimeException.class)
    public void processData_emptyInput_notOk() {
        List<String> emptyList = Collections.emptyList();
        dataProcessService.processData(emptyList);
        Assert.assertTrue("You should throw an Exception with explanation", true);
    }

    @Test(expected = RuntimeException.class)
    public void processData_nullInput_notOk() {
        dataProcessService.processData(null);
        Assert.assertTrue("You should throw an Exception with explanation", true);
    }

    private List<FruitTransaction> generateFruitTransactionList() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getType("b"), "orange", 20));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getType("b"), "pineapple", 10));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getType("s"), "orange", 10));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getType("p"), "orange", 13));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getType("r"), "apple", 20));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getType("p"), "pineapple", 5));
        fruitTransactionList.add(new FruitTransaction(
                FruitTransaction.Operation.getType("s"), "pineapple", 50));
        return fruitTransactionList;
    }
}
