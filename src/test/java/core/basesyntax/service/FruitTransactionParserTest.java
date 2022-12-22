package core.basesyntax.service;

import static org.hamcrest.CoreMatchers.is;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static final String INFO_LINE = "type,fruit,quantity";
    private static final String FIRST_TRANSACTION_LINE = "b,apple,50";
    private static final String SECOND_TRANSACTION_LINE = "p,apple,40";
    private static final String FIRST_INCORRECT_TRANSACTION_LINE = "p.orange;100";
    private static final String SECOND_INCORRECT_TRANSACTION_LINE = "";
    private static final String THIRD_INCORRECT_TRANSACTION_LINE = null;
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parse_inputEmptyList_notOk() {
        try {
            fruitTransactionParser.parse(new ArrayList<>());
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong input data"));
        }
    }

    @Test
    public void parse_listWithoutInfoLine_notOk() {
        List<String> invalidList = new ArrayList<>();
        invalidList.add(FIRST_TRANSACTION_LINE);
        invalidList.add(SECOND_TRANSACTION_LINE);
        try {
            fruitTransactionParser.parse(invalidList);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong input data"));
        }
    }

    @Test
    public void parse_nullInputValue_notOk() {
        try {
            fruitTransactionParser.parse(null);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong input data"));
        }
    }

    @Test
    public void parse_incorrectInputData_notOk() {
        List<String> firstIncorrectData = new ArrayList<>();
        firstIncorrectData.add(INFO_LINE);
        firstIncorrectData.add(FIRST_INCORRECT_TRANSACTION_LINE);
        List<String> secondIncorrectData = new ArrayList<>();
        secondIncorrectData.add(INFO_LINE);
        secondIncorrectData.add(SECOND_INCORRECT_TRANSACTION_LINE);
        List<String> thirdIncorrectData = new ArrayList<>();
        thirdIncorrectData.add(INFO_LINE);
        thirdIncorrectData.add(THIRD_INCORRECT_TRANSACTION_LINE);
        try {
            fruitTransactionParser.parse(firstIncorrectData);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong format data in line, line: "
                    + FIRST_INCORRECT_TRANSACTION_LINE));
        }
        try {
            fruitTransactionParser.parse(secondIncorrectData);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Wrong format data in line, line: "
                    + SECOND_INCORRECT_TRANSACTION_LINE));
        }
        try {
            fruitTransactionParser.parse(thirdIncorrectData);
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Line can't be null"));
        }
    }

    @Test
    public void parse_correctInputData_ok() {
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setFruit(new Fruit("apple"));
        firstTransaction.setAmount(50);
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setFruit(new Fruit("apple"));
        secondTransaction.setAmount(40);
        secondTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(firstTransaction);
        expected.add(secondTransaction);
        List<String> correctData = new ArrayList<>();
        correctData.add(INFO_LINE);
        correctData.add(FIRST_TRANSACTION_LINE);
        correctData.add(SECOND_TRANSACTION_LINE);
        List<FruitTransaction> actual = fruitTransactionParser.parse(correctData);
        Assert.assertEquals(expected, actual);
    }
}
