package core.basesyntax.parser;

import core.basesyntax.errors.InputDataEqualNullException;
import core.basesyntax.errors.InvalidCodeException;
import core.basesyntax.fileservice.CsvReadFileServiceImpl;
import core.basesyntax.fileservice.ReadFileService;
import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser;
    private ReadFileService readFileService;
    private final List<FruitTransaction> listAfterParsingListLines = new ArrayList<>();

    @Before
    public void initializationOfVariables() {
        transactionParser = new TransactionParserImpl();
        readFileService = new CsvReadFileServiceImpl();
    }

    @Test
    public void parse_checkWork_ok() {
        FruitTransaction fruitTransactionOne = new FruitTransaction();
        fruitTransactionOne.setFruit("banana");
        fruitTransactionOne.setQuantity(30);
        fruitTransactionOne.setOperation(FruitTransaction.Operation.BALANCE);
        listAfterParsingListLines.add(fruitTransactionOne);
        FruitTransaction fruitTransactionTwo = new FruitTransaction();
        fruitTransactionTwo.setFruit("apple");
        fruitTransactionTwo.setQuantity(110);
        fruitTransactionTwo.setOperation(FruitTransaction.Operation.BALANCE);
        listAfterParsingListLines.add(fruitTransactionTwo);
        FruitTransaction fruitTransactionThree = new FruitTransaction();
        fruitTransactionThree.setFruit("banana");
        fruitTransactionThree.setQuantity(110);
        fruitTransactionThree.setOperation(FruitTransaction.Operation.SUPPLY);
        listAfterParsingListLines.add(fruitTransactionThree);
        FruitTransaction fruitTransactionFour = new FruitTransaction();
        fruitTransactionFour.setFruit("banana");
        fruitTransactionFour.setQuantity(23);
        fruitTransactionFour.setOperation(FruitTransaction.Operation.PURCHASE);
        listAfterParsingListLines.add(fruitTransactionFour);
        FruitTransaction fruitTransactionFive = new FruitTransaction();
        fruitTransactionFive.setFruit("apple");
        fruitTransactionFive.setQuantity(20);
        fruitTransactionFive.setOperation(FruitTransaction.Operation.RETURN);
        listAfterParsingListLines.add(fruitTransactionFive);
        FruitTransaction fruitTransactionSix = new FruitTransaction();
        fruitTransactionSix.setFruit("apple");
        fruitTransactionSix.setQuantity(30);
        fruitTransactionSix.setOperation(FruitTransaction.Operation.PURCHASE);
        listAfterParsingListLines.add(fruitTransactionSix);
        FruitTransaction fruitTransactionSeven = new FruitTransaction();
        fruitTransactionSeven.setFruit("banana");
        fruitTransactionSeven.setQuantity(15);
        fruitTransactionSeven.setOperation(FruitTransaction.Operation.PURCHASE);
        listAfterParsingListLines.add(fruitTransactionSeven);
        FruitTransaction fruitTransactionEight = new FruitTransaction();
        fruitTransactionEight.setFruit("banana");
        fruitTransactionEight.setQuantity(60);
        fruitTransactionEight.setOperation(FruitTransaction.Operation.SUPPLY);
        listAfterParsingListLines.add(fruitTransactionEight);

        String filePath = "src/test/resource/test.csv";

        List<String> listStringsLineForParse = readFileService.read(filePath);
        List<FruitTransaction> listForTesting = transactionParser.parse(listStringsLineForParse);
        Assert.assertEquals(listAfterParsingListLines, listForTesting);
    }

    @Test(expected = InvalidCodeException.class)
    public void parse_inputDataIsIncorrect_notOk() {
        List<String> lineForParsMethod = new ArrayList<>();
        lineForParsMethod.add("b banana 30");
        transactionParser.parse(lineForParsMethod);
    }

    @Test(expected = InputDataEqualNullException.class)
    public void parse_inputNull_notOk() {
        transactionParser.parse(null);
    }
}
