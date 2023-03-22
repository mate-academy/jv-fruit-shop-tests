package core.basesyntax.parser;

import core.basesyntax.errors.InvalidCodeException;
import core.basesyntax.fileservice.CsvReadFileServiceImpl;
import core.basesyntax.fileservice.ReadFileService;
import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser = new TransactionParserImpl();
    private ReadFileService readFileService = new CsvReadFileServiceImpl();
    private List<FruitTransaction> listAfterParsingListLines = new ArrayList<>();

    @Test
    public void checkingTheWorkOfTheParseMethod_Ok() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(30);
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        listAfterParsingListLines.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(110);
        fruitTransaction2.setOperation(FruitTransaction.Operation.BALANCE);
        listAfterParsingListLines.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setQuantity(110);
        fruitTransaction3.setOperation(FruitTransaction.Operation.SUPPLY);
        listAfterParsingListLines.add(fruitTransaction3);
        FruitTransaction fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setFruit("banana");
        fruitTransaction4.setQuantity(23);
        fruitTransaction4.setOperation(FruitTransaction.Operation.PURCHASE);
        listAfterParsingListLines.add(fruitTransaction4);
        FruitTransaction fruitTransaction5 = new FruitTransaction();
        fruitTransaction5.setFruit("apple");
        fruitTransaction5.setQuantity(20);
        fruitTransaction5.setOperation(FruitTransaction.Operation.RETURN);
        listAfterParsingListLines.add(fruitTransaction5);
        FruitTransaction fruitTransaction6 = new FruitTransaction();
        fruitTransaction6.setFruit("apple");
        fruitTransaction6.setQuantity(30);
        fruitTransaction6.setOperation(FruitTransaction.Operation.PURCHASE);
        listAfterParsingListLines.add(fruitTransaction6);
        FruitTransaction fruitTransaction7 = new FruitTransaction();
        fruitTransaction7.setFruit("banana");
        fruitTransaction7.setQuantity(15);
        fruitTransaction7.setOperation(FruitTransaction.Operation.PURCHASE);
        listAfterParsingListLines.add(fruitTransaction7);
        FruitTransaction fruitTransaction8 = new FruitTransaction();
        fruitTransaction8.setFruit("banana");
        fruitTransaction8.setQuantity(60);
        fruitTransaction8.setOperation(FruitTransaction.Operation.SUPPLY);
        listAfterParsingListLines.add(fruitTransaction8);

        String filePath = "src/test/resource/test.csv";

        List<String> listStringsLineForParse = readFileService.read(filePath);
        List<FruitTransaction> listForTesting = transactionParser.parse(listStringsLineForParse);
        Assert.assertEquals(listAfterParsingListLines, listForTesting);
    }

    @Test(expected = InvalidCodeException.class)
    public void throwsInvalidCodeException_NotOk() {
        List<String> lineForParsMethod = new ArrayList<>();
        lineForParsMethod.add("b banana 30");
        transactionParser.parse(lineForParsMethod);
    }

    @Test(expected = NullPointerException.class)
    public void theInputParameterIsNull_NotOk() {
        transactionParser.parse(null);
    }
}
