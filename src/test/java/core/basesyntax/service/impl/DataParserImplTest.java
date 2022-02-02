package core.basesyntax.service.impl;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataParserImplTest {
    private List<String> stringsList;

    @Before
    public void setUp() {
        stringsList = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "r,apple,10");
    }

    @Test
    public void parsingData_Ok() {
        FruitTransaction firstFruit = new FruitTransaction("banana", 20);
        firstFruit.setOperationByIndex("b");
        FruitTransaction secondFruit = new FruitTransaction("apple", 100);
        secondFruit.setOperationByIndex("b");
        FruitTransaction thirdFruit = new FruitTransaction("banana", 100);
        thirdFruit.setOperationByIndex("s");
        FruitTransaction fourthFruit = new FruitTransaction("apple", 10);
        fourthFruit.setOperationByIndex("r");
        List<FruitTransaction> expecteds = List.of(firstFruit, secondFruit,
                thirdFruit, fourthFruit);

        List<FruitTransaction> actuals = new DataParserImpl().parsingData(stringsList);

        assertArrayEquals(expecteds.toArray(), actuals.toArray());
    }

    @Test
    public void parsingData_notValidData_notOk() {
        List<String> arrayList = new ArrayList<>(stringsList);
        arrayList.add("banana,20");

        try {
            new DataParserImpl().parsingData(arrayList);
        } catch (RuntimeException e) {
            return;
        }
        fail("Data not valid");
    }
}
