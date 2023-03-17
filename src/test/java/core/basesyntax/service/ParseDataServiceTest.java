package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseDataServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseDataServiceTest {
    private static ParseDataService parseDataService;

    @BeforeClass
    public static void beforeAll() {
        parseDataService = new ParseDataServiceImpl();
    }

    @Test
    public void parseData_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(creteTransaction(FruitTransaction.Operation.BALANCE, "banana", 200));
        expected.add(creteTransaction(FruitTransaction.Operation.PURCHASE, "apple", 100));
        expected.add(creteTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        expected.add(creteTransaction(FruitTransaction.Operation.RETURN, "apple", 100));
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,200");
        input.add("p,apple,100");
        input.add("s,banana,50");
        input.add("r,apple,100");
        List<FruitTransaction> actual = parseDataService.parseData(input);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void inputNullData_NotOk() {
        List<FruitTransaction> list = parseDataService.parseData(null);
    }

    @Test(expected = RuntimeException.class)
    public void inputInvalidData_NotOk() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("o,banana,200");
        List<FruitTransaction> list = parseDataService.parseData(input);
    }

    private FruitTransaction creteTransaction(FruitTransaction.Operation operation,
                                              String fruitName,
                                              int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruitName);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
