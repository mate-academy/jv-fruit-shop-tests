package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;
    private static List<String> dataFromFile;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
        dataFromFile = new ArrayList<>();
    }

    @Test
    public void getTransactionFromString_Ok() {
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        final List<FruitTransaction> expectedResult = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        expectedResult.add(fruitTransaction);
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);
        expectedResult.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(100);
        expectedResult.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setQuantity(13);
        expectedResult.add(fruitTransaction3);
        List<FruitTransaction> actualResult = parserService.getTransactionFromString(dataFromFile);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void getTransactionFromString_emptyInput_notOk() {
        parserService.getTransactionFromString(dataFromFile);
    }

    @After
    public void tearDown() {
        dataFromFile.clear();
    }
}
