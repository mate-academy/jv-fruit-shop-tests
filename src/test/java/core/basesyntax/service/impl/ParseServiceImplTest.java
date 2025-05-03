package core.basesyntax.service.impl;

import core.basesyntax.model.ProductTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;
    private final List<String> inputGoodDataWithoutHeader;
    private final Queue<ProductTransaction> expectedForInputGoodData;

    {
        inputGoodDataWithoutHeader = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");

        expectedForInputGoodData = new LinkedList<>();
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.BALANCE, "banana", 20));
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.BALANCE, "apple", 100));
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.SUPPLY, "banana", 100));
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.PURCHASE, "banana", 13));
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.RETURN, "apple", 10));
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.PURCHASE, "apple", 20));
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.PURCHASE, "banana", 5));
        expectedForInputGoodData.add(new ProductTransaction(
                ProductTransaction.Operation.SUPPLY, "banana", 50));
    }

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void parse_notEmptyCorrectDataWithHeader_ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.addAll(inputGoodDataWithoutHeader);
        Queue<ProductTransaction> actual = parseService.parse(inputData);
        Assert.assertEquals(expectedForInputGoodData, actual);
    }

    @Test
    public void parse_notEmptyCorrectDataWithoutHeader_ok() {
        Queue<ProductTransaction> actual = parseService.parse(inputGoodDataWithoutHeader);
        Assert.assertEquals(expectedForInputGoodData, actual);
    }

    @Test
    public void parse_emptyInputData_ok() {
        List<String> inputData = new ArrayList<>();
        Queue<ProductTransaction> actual = parseService.parse(inputData);
        Assert.assertEquals(0, actual.size());
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongHeader_notOk() {
        List<String> inputData = List.of("wrong,header");
        parseService.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongDelimiter_notOk() {
        List<String> inputData = List.of("b|banana|20");
        parseService.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongOperation_notOk() {
        List<String> inputData = List.of("o,banana,20");
        parseService.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongQuantity_notOk() {
        List<String> inputData = List.of("b,banana,20a");
        parseService.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_emptyProduct_notOk() {
        List<String> inputData = List.of("b,,20");
        parseService.parse(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_negativeQuantity_notOk() {
        List<String> inputData = List.of("b,banana,-20");
        parseService.parse(inputData);
    }
}
