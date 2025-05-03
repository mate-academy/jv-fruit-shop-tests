package mate.academy.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import mate.academy.model.FruitTransaction;
import mate.academy.service.impl.ParseServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceTest {
    private static ParseService parseService;
    private List<String> stringList = new ArrayList<>();
    private List<FruitTransaction> expectedResult = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void parseCorrectData_ok() {
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,20");
        stringList.add("b,apple,100");
        stringList.add("s,banana,100");
        stringList.add("p,banana,13");
        stringList.add("r,apple,10");
        stringList.add("p,apple,20");
        stringList.add("p,banana,5");
        stringList.add("s,banana,50");
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expectedResult.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> result = parseService.parse(stringList);
        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void parseLessColumnData_notOk() {
        stringList.add("type,fruit");
        stringList.add("b,banana");
        stringList.add("b,apple");
        stringList.add("s,banana");
        stringList.add("p,banana");
        stringList.add("r,apple");
        stringList.add("p,apple");
        stringList.add("p,banana");
        stringList.add("s,banana");
        try {
            parseService.parse(stringList);
        } catch (RuntimeException e) {
            return;
        }
        fail("You must throw exception if file has an incorrect format.");
    }

    @Test
    public void parseMoreColumnData_notOk() {
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,20,21");
        stringList.add("b,apple,100,21");
        stringList.add("s,banana,100,12");
        stringList.add("p,banana,13,12");
        stringList.add("r,apple,10,32");
        stringList.add("p,apple,20,43");
        stringList.add("p,banana,5,34");
        stringList.add("s,banana,50,34");
        try {
            parseService.parse(stringList);
        } catch (RuntimeException e) {
            return;
        }
        fail("You must throw exception if file has an incorrect format.");
    }

    @Test
    public void parseNonCorrectQuantity_notOk() {
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,-19");
        stringList.add("b,apple,-1");
        stringList.add("s,banana,-100");
        stringList.add("p,banana,-13");
        stringList.add("r,apple,-10");
        stringList.add("p,apple,-20");
        stringList.add("p,banana,-5");
        stringList.add("s,banana,-50");
        try {
            parseService.parse(stringList);
        } catch (RuntimeException e) {
            return;
        }
        fail("You must throw exception if file has an incorrect quantity.");
    }
}
