package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionGeneratorImplTest {
    private FruitTransactionGenerator fruitTransactionGenerator;

    @Before
    public void setUp() {
        fruitTransactionGenerator = new FruitTransactionGeneratorImpl();
    }

    @Test
    public void createFruitTransaction_ValidData_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 45));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 11));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "aple", 3));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 30));
        List<String> data = new ArrayList<>();
        data.add("b,banana,45");
        data.add("p,banana,11");
        data.add("r,aple,3");
        data.add("s,banana,30");
        List<FruitTransaction> actual = fruitTransactionGenerator.createFruitTransaction(data);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createFruitTransaction_NotValidData_NotOk() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,45");
        data.add("f,banana,11");
        fruitTransactionGenerator.createFruitTransaction(data);
    }

    @Test(expected = RuntimeException.class)
    public void createFruitTransaction_NullData_NotOk() {
        List<String> data = new ArrayList<>();
        fruitTransactionGenerator.createFruitTransaction(data);
    }
}
