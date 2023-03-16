package core.basesyntax.service.implementation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserServiceImplTest {
    private static final String CORRECT_LINE_FIRST = "b,apple,101";
    private static final String CORRECT_LINE_SECOND = "s,apple,10";
    private static final String CORRECT_LINE_THIRD = "s,apple,15";
    private static final String APPLE_FRUIT = "apple";
    private static final int FIRST_ELEMENT = 0;
    private static final int EXPECTED_QUANTITY = 101;
    ParserServiceImpl parserService;
    List<String> stringList;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
        stringList = new ArrayList<>();
        stringList.add(CORRECT_LINE_FIRST);
        stringList.add(CORRECT_LINE_SECOND);
        stringList.add(CORRECT_LINE_THIRD);
        Storage.fruits.clear();
    }

    @After
    public void tearDown() {
        stringList.clear();
    }

    @Test
    public void parseFruitTransactions() {
        List<FruitTransaction> listTest = parserService.parseFruitTransactions(stringList);
        FruitTransaction fruitTransaction = listTest.get(FIRST_ELEMENT);

        String expectedFruit = APPLE_FRUIT;
        String actualFruit = listTest.get(FIRST_ELEMENT).getFruit();
        assertEquals("Expected fruits = " + expectedFruit + ", but was: " + actualFruit,
                expectedFruit, actualFruit);

        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actualOperation = fruitTransaction.getOperation();
        assertEquals("Expected operation = " + expectedOperation + ", but was: "
                        + actualOperation, expectedOperation, actualOperation);

        int expectedQuantity = EXPECTED_QUANTITY;
        int actualQuantity = listTest.get(FIRST_ELEMENT).getQuantity();
        assertEquals("Expected amount = " + expectedQuantity + ", but was: "
                        + actualQuantity, expectedQuantity, actualQuantity);
    }
}