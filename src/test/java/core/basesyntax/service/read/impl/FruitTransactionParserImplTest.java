package core.basesyntax.service.read.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.read.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final String csvSeparator = ",";
    private static final String operationTypeName = "type";
    private static final String fruitName = "fruit";
    private static final String fruitQuantityName = "quantity";
    private FruitTransactionParser fruitTransactionParser =
            new FruitTransactionParserImpl(csvSeparator, operationTypeName,
                    fruitQuantityName, fruitName);

    @Test
    public void parse_emptyData_ok() {
        List<String> testData = new ArrayList<>();
        testData.add("type,fruit,quantity");

        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = fruitTransactionParser.parse(testData);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validData_ok() {
        List<String> testData = new ArrayList<>();
        testData.add("type,fruit,quantity");
        testData.add("b,banana,20");
        testData.add("b,apple,100");
        testData.add("s,banana,103");
        testData.add("p,banana,13");
        testData.add("r,apple,10");

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 103));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        List<FruitTransaction> actual = fruitTransactionParser.parse(testData);

        assertEquals(expected, actual);
    }

}
