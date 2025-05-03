package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProcessingData;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessingDataImplTest {
    private static ProcessingData processingData;
    private static List<String> incomeData;
    private static List<FruitTransaction> expectedData;

    @BeforeClass
    public static void setFileHandler() {
        processingData = new ProcessingDataImpl();
        incomeData = new ArrayList<>();
        expectedData = new ArrayList<>();
        incomeData.add("type,fruit,quantity");
        incomeData.add("b,banana,20");
        incomeData.add("b,apple,100");
        incomeData.add("s,banana,200");
        incomeData.add("p,banana,13");
        expectedData.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        expectedData.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        expectedData.add(new FruitTransaction(Operation.SUPPLY, "banana", 200));
        expectedData.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));
    }

    @Test
    public void parseData_ok() {
        List<FruitTransaction> actualData;
        actualData = processingData.parseData(incomeData);
        assertEquals("You hava incorrect data from list", expectedData, actualData);
    }

    @Test (expected = RuntimeException.class)
    public void parseData_lengthOfListLess_notOk() {
        List<String> incorrectData = List.of("type,fruit,quantity");
        processingData.parseData(incorrectData);
    }

    @Test (expected = RuntimeException.class)
    public void parseData_nullList_notOk() {
        List<String> incorrectData = null;
        processingData.parseData(incorrectData);
    }
}
