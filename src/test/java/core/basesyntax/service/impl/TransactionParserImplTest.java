package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {

    private static final String INPUT_FILE_NAME = "src/main/resources/fruits.csv";

    private TransactionParser fruitTransactionParser = new TransactionParserImpl();

    @Test
    void parseTransactionTest_Is_ok() {
        FileReader fileReader = new FileReaderImpl();
        TransactionParser transactionParser = new TransactionParserImpl();
        List<String> strings = fileReader.readFile(INPUT_FILE_NAME);
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
        inputData.add("s,apple,10");
        inputData.add("p,orange,5");
        inputData.add("b,banana,20");
        List<FruitTransaction> parsedData = fruitTransactionParser.parseTransaction(inputData);
        assertEquals(3, parsedData.size());
        assertEquals(FruitTransaction.Operation.SUPPLY, parsedData.get(0).getOperation());
        assertEquals("apple", parsedData.get(0).getFruit());
        assertEquals(10, parsedData.get(0).getQuantity());
        assertEquals(FruitTransaction.Operation.PURCHASE, parsedData.get(1).getOperation());
        assertEquals("orange", parsedData.get(1).getFruit());
        assertEquals(5, parsedData.get(1).getQuantity());
        assertEquals(FruitTransaction.Operation.BALANCE, parsedData.get(2).getOperation());
        assertEquals("banana", parsedData.get(2).getFruit());
        assertEquals(20, parsedData.get(2).getQuantity());
    }
}
