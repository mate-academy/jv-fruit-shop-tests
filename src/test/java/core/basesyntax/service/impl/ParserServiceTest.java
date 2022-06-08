package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void getTransactionFromString_Ok() {
        List<String> dataFromFile = new ArrayList<>();
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        List<FruitTransaction> expectedResult = new ArrayList<>();
        FruitTransaction fruitTransaction = createFruitTransaction("banana", 20,
                FruitTransaction.Operation.BALANCE);
        expectedResult.add(fruitTransaction);
        FruitTransaction fruitTransaction1 = createFruitTransaction("apple", 100,
                FruitTransaction.Operation.BALANCE);
        expectedResult.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = createFruitTransaction("banana", 100,
                FruitTransaction.Operation.SUPPLY);
        expectedResult.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = createFruitTransaction("banana", 13,
                FruitTransaction.Operation.PURCHASE);
        expectedResult.add(fruitTransaction3);
        List<FruitTransaction> actualResult = parserService.getTransactionFromString(dataFromFile);
        Assert.assertEquals(expectedResult, actualResult);
    }

    private FruitTransaction createFruitTransaction(String fruit, int quantity,
                                                    FruitTransaction.Operation operation) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setOperation(operation);
        return fruitTransaction;
    }

    @Test(expected = RuntimeException.class)
    public void getTransactionFromString_emptyInput_notOk() {
        parserService.getTransactionFromString(Collections.emptyList());
    }
}
