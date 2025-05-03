package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final String BANANA_STRING = "b,banana,20";
    private static final String ORANGE_STRING = "p,orange,5";
    private static final String APPLE_STRING = "s,apple,10";
    private static final String BANANA_NAME = "banana";
    private static final String APPLE_NAME = "apple";
    private static final String ORANGE_NAME = "orange";

    private TransactionParser fruitTransactionParser = new TransactionParserImpl();

    @Test
    void parseTransactionTest_ok() {
        TransactionParser transactionParser = new TransactionParserImpl();
        List<String> strings = Stream.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        ).collect(Collectors.toList());
        Assertions.assertNotNull(transactionParser.parseTransaction(strings));
        List<FruitTransaction> listOfTransaction = transactionParser.parseTransaction(strings);
        Assertions.assertEquals(8, listOfTransaction.size());
        Assertions.assertTrue(transactionParser.parseTransaction(new ArrayList<>()).isEmpty());
    }

    @Test
    void operationGetIs_ok() {
        FruitTransaction.Operation operationBalance = FruitTransaction.Operation.getByCode("b");
        Assertions.assertEquals(operationBalance, FruitTransaction.Operation.BALANCE);
        FruitTransaction.Operation operationReturn = FruitTransaction.Operation.getByCode("r");
        Assertions.assertEquals(operationReturn, FruitTransaction.Operation.RETURN);
    }

    @Test
    void operationGetIs_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> FruitTransaction.Operation
                .getByCode("x"));
    }

    @Test
    public void testParseTransaction() {
        List<String> inputData = new ArrayList<>();
        inputData.add(APPLE_STRING);
        inputData.add(ORANGE_STRING);
        inputData.add(BANANA_STRING);
        final int appleExpected = 10;
        final int orangeExpected = 5;
        final int bananaExpected = 20;
        List<FruitTransaction> parsedData = fruitTransactionParser.parseTransaction(inputData);
        assertEquals(3, parsedData.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, parsedData.get(0).getOperation());
        assertEquals(APPLE_NAME, parsedData.get(0).getFruit());
        assertEquals(appleExpected, parsedData.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.PURCHASE, parsedData.get(1).getOperation());
        assertEquals(ORANGE_NAME, parsedData.get(1).getFruit());
        assertEquals(orangeExpected, parsedData.get(1).getQuantity());
        assertEquals(FruitTransaction.Operation.BALANCE, parsedData.get(2).getOperation());
        assertEquals(BANANA_NAME, parsedData.get(2).getFruit());
        assertEquals(bananaExpected, parsedData.get(2).getQuantity());
    }
}
