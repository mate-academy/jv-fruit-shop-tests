package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReader;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/fruits.csv";

    @Test
    void parseTransactionTest() {
        FileReader fileReader = new FileReaderImpl();
        TransactionParser transactionParser = new TransactionParserImpl();
        List<String> strings = fileReader.readFile(INPUT_FILE_NAME);
        Assertions.assertNotNull(transactionParser.parseTransaction(strings));
        List<FruitTransaction> listOfTransaction = transactionParser.parseTransaction(strings);
        Assertions.assertEquals(8, listOfTransaction.size());
        Assertions.assertTrue(transactionParser.parseTransaction(new ArrayList<>()).isEmpty());
    }

    @Test
    void operationGetTest() {
        FruitTransaction.Operation b = FruitTransaction.Operation.getByCode("b");
        Assertions.assertTrue(b.equals(FruitTransaction.Operation.BALANCE));
        FruitTransaction.Operation r = FruitTransaction.Operation.getByCode("r");
        Assertions.assertTrue(r.equals(FruitTransaction.Operation.RETURN));
        Assertions.assertThrows(RuntimeException.class, () -> FruitTransaction.Operation
                .getByCode("x"));
    }
}
