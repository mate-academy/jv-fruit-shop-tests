package core.basesyntax.parser;

import core.basesyntax.errors.InputDataEqualNullException;
import core.basesyntax.errors.InvalidCodeException;
import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser;
    private final List<FruitTransaction> expectedListAfterParsingListLines = new ArrayList<>();
    private final List<String> listForParsingMethod = new ArrayList<>();

    @Before
    public void initializationOfVariables() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parse_checkWork_ok() {
        listForParsingMethod.add("b,banana,30");
        listForParsingMethod.add("b,apple,110");
        listForParsingMethod.add("s,banana,110");
        listForParsingMethod.add("p,banana,23");
        listForParsingMethod.add("r,apple,20");
        listForParsingMethod.add("p,apple,30");
        listForParsingMethod.add("p,banana,15");
        listForParsingMethod.add("s,banana,60");

        FruitTransaction fruitTransactionOne = new FruitTransaction();
        fruitTransactionOne.setFruit("banana");
        fruitTransactionOne.setQuantity(30);
        fruitTransactionOne.setOperation(FruitTransaction.Operation.BALANCE);
        expectedListAfterParsingListLines.add(fruitTransactionOne);
        FruitTransaction fruitTransactionTwo = new FruitTransaction();
        fruitTransactionTwo.setFruit("apple");
        fruitTransactionTwo.setQuantity(110);
        fruitTransactionTwo.setOperation(FruitTransaction.Operation.BALANCE);
        expectedListAfterParsingListLines.add(fruitTransactionTwo);
        FruitTransaction fruitTransactionThree = new FruitTransaction();
        fruitTransactionThree.setFruit("banana");
        fruitTransactionThree.setQuantity(110);
        fruitTransactionThree.setOperation(FruitTransaction.Operation.SUPPLY);
        expectedListAfterParsingListLines.add(fruitTransactionThree);
        FruitTransaction fruitTransactionFour = new FruitTransaction();
        fruitTransactionFour.setFruit("banana");
        fruitTransactionFour.setQuantity(23);
        fruitTransactionFour.setOperation(FruitTransaction.Operation.PURCHASE);
        expectedListAfterParsingListLines.add(fruitTransactionFour);
        FruitTransaction fruitTransactionFive = new FruitTransaction();
        fruitTransactionFive.setFruit("apple");
        fruitTransactionFive.setQuantity(20);
        fruitTransactionFive.setOperation(FruitTransaction.Operation.RETURN);
        expectedListAfterParsingListLines.add(fruitTransactionFive);
        FruitTransaction fruitTransactionSix = new FruitTransaction();
        fruitTransactionSix.setFruit("apple");
        fruitTransactionSix.setQuantity(30);
        fruitTransactionSix.setOperation(FruitTransaction.Operation.PURCHASE);
        expectedListAfterParsingListLines.add(fruitTransactionSix);
        FruitTransaction fruitTransactionSeven = new FruitTransaction();
        fruitTransactionSeven.setFruit("banana");
        fruitTransactionSeven.setQuantity(15);
        fruitTransactionSeven.setOperation(FruitTransaction.Operation.PURCHASE);
        expectedListAfterParsingListLines.add(fruitTransactionSeven);
        FruitTransaction fruitTransactionEight = new FruitTransaction();
        fruitTransactionEight.setFruit("banana");
        fruitTransactionEight.setQuantity(60);
        fruitTransactionEight.setOperation(FruitTransaction.Operation.SUPPLY);
        expectedListAfterParsingListLines.add(fruitTransactionEight);

        List<FruitTransaction> listForTesting = transactionParser.parse(listForParsingMethod);
        Assert.assertEquals(expectedListAfterParsingListLines, listForTesting);
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
