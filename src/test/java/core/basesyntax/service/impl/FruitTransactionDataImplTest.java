package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionDataImplTest {
    private static FruitTransactionData fruitTransactionData;
    private List<String> dataFromFile;
    private List<FruitTransaction> expected;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionData = new FruitTransactionDataImpl();
    }

    @Before
    public void setUp() {
        expected = new ArrayList<>();
        dataFromFile = new ArrayList<>();
    }

    @Test
    public void parseDataFromFile_EmptyData_ok() {
        dataFromFile.add("type,fruit,quantity");
        List<FruitTransaction> actual = fruitTransactionData.parseDataFromFile(dataFromFile);
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void parseDataFromFile_ok() {
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        dataFromFile.add("s,banana,100");
        dataFromFile.add("p,banana,13");
        dataFromFile.add("r,apple,10");
        dataFromFile.add("p,apple,20");
        dataFromFile.add("p,banana,5");
        dataFromFile.add("s,banana,50");
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(Operation.PURCHASE, "apple", 20));
        expected.add(new FruitTransaction(Operation.PURCHASE, "banana", 5));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = fruitTransactionData.parseDataFromFile(dataFromFile);
        assertEquals(expected, actual);
    }

    @Test
    public void parseDataFromFile_PartOfData_ok() {
        dataFromFile.add("type,fruit,quantity");
        dataFromFile.add("s,banana,100");
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        List<FruitTransaction> actual = fruitTransactionData.parseDataFromFile(dataFromFile);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseDataFromFile_NullData_notOk() {
        fruitTransactionData.parseDataFromFile(null);
    }
}
