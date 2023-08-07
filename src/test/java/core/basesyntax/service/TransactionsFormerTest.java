package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class TransactionsFormerTest {
    private static final int NEGATIVE_QUANTITY = -20;
    private static final int NUMBERS_INSTEAD_OF_FRUIT_NAME = 99;
    private static final String EMPTY_OPERATION_CODE_LINE = " ,banana,20";
    private static final String EMPTY_FRUIT_LINE = "b, ,20";
    private static final String EMPTY_NAME = " ";
    private static final String EMPTY_QUANTITY_LINE = "b,banana, ";
    private static final String FRUIT_NAME_WITH_NUMBERS_LINE = "b,banana99,20";
    private static final String INCORRECT_CODE_EXCEPTION_TEXT = "Invalid code";
    private static final String INCORRECT_FRUIT_NAME_EXCEPTION_TEXT = "Incorrect fruit name ";
    private static final String INCORRECT_LINE_EXCEPTION_TEXT = "Wrong operation input";
    private static final String INCORRECT_OPERATION_CODE_LINE = "u,banana,20";
    private static final String INCORRECT_QUANTITY_EXCEPTION_TEXT =
            "Quantity should ba a positive number, provided \"";
    private static final String LETTER_QUANTITY_LINE = "b,banana,b";
    private static final String NEGATIVE_QUANTITY_LINE = "b,banana,-20";
    private static final String NO_DATA_EXCEPTION_TEXT = "There is no operations to execute";
    private static final String NO_FRUIT_NAME_EXCEPTION_TEXT = "Fruit name can`t be null";
    private static final String NO_FRUIT_NAME_LINE = "b,,20";
    private static final String NO_OPERATION_CODE_LINE = ",banana,20";
    private static final String NO_QUANTITY_LINE = "b,banana,";
    private static final String NUMBERS_INSTEAD_OF_FRUIT_NAME_LINE = "b,99,20";
    private static final String STRING_QUANTITY = "b";
    private static final String SYMBOLS_INSTEAD_OF_FRUIT_NAME_LINE = "b,%!&,20";
    private static final String SYMBOLS_INSTEAD_OF_QUANTITY_LINE = "b,banana,%!&";
    private static final String SYMBOLS = "%!&";
    private static final String ZERO_QUANTITY_LINE = "b,banana,0";
    private static final TransactionsFormerImpl transactionsFormer = new TransactionsFormerImpl();
    private static final List<FruitTransaction> expectedCorrectList = new ArrayList<>();
    private static final List<FruitTransaction> fruitWithNumbersList = new ArrayList<>();
    private static final List<FruitTransaction> listWithZeroBananas = new ArrayList<>();

    static {
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100));
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 13));
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10));
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20));
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5));
        expectedCorrectList.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50));
        fruitWithNumbersList.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana99", 20));
        listWithZeroBananas.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 0));
    }

    @Test
    public void transactionFormer_CorrectData_Ok() {
        List<String> correctDataList = formCorrectList();
        List<FruitTransaction> actual = transactionsFormer.formTransactionList(correctDataList);

        assertEquals(expectedCorrectList, actual);
    }

    @Test
    public void transactionFormer_fromNull_notOk() {
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(null));

        assertEquals(NO_DATA_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void transactionFormer_romEmptyList_notOk() {
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(Collections.emptyList()));

        assertEquals(NO_DATA_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void transactionFormer_incorrectOperation_notOk() {
        List<String> listWithIncorrectOperation = new ArrayList<>();
        listWithIncorrectOperation.add(INCORRECT_OPERATION_CODE_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithIncorrectOperation));

        assertEquals(INCORRECT_CODE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void transactionFormer_NoOperation_notOk() {
        List<String> listWithNoOperation = new ArrayList<>();
        listWithNoOperation.add(NO_OPERATION_CODE_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithNoOperation));

        assertEquals(INCORRECT_CODE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void transactionFormer_emptyOperation_notOk() {
        List<String> listWithEmptyOperation = new ArrayList<>();
        listWithEmptyOperation.add(EMPTY_OPERATION_CODE_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithEmptyOperation));

        assertEquals(INCORRECT_CODE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void transactionFormer_noFruit_notOk() {
        List<String> listWithNoFruit = new ArrayList<>();
        listWithNoFruit.add(NO_FRUIT_NAME_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithNoFruit));

        assertEquals(NO_FRUIT_NAME_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void transactionFormer_emptyFruit_notOk() {
        List<String> listWithEmptyFruit = new ArrayList<>();
        listWithEmptyFruit.add(EMPTY_FRUIT_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithEmptyFruit));

        assertEquals(INCORRECT_FRUIT_NAME_EXCEPTION_TEXT + EMPTY_NAME, exception.getMessage());
    }

    @Test
    public void transactionFormer_numbersFruit_notOk() {
        List<String> listWithNumberFruit = new ArrayList<>();
        listWithNumberFruit.add(NUMBERS_INSTEAD_OF_FRUIT_NAME_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithNumberFruit));

        assertEquals(INCORRECT_FRUIT_NAME_EXCEPTION_TEXT
                + NUMBERS_INSTEAD_OF_FRUIT_NAME, exception.getMessage());
    }

    @Test
    public void transactionFormer_symbolsFruit_notOk() {
        List<String> listWithSymbolFruit = new ArrayList<>();
        listWithSymbolFruit.add(SYMBOLS_INSTEAD_OF_FRUIT_NAME_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithSymbolFruit));

        assertEquals(INCORRECT_FRUIT_NAME_EXCEPTION_TEXT + SYMBOLS, exception.getMessage());
    }

    @Test
    public void transactionFormer_fruitWithNumbers_Ok() {
        List<String> listWithWithNumberedFruit = new ArrayList<>();
        listWithWithNumberedFruit.add(FRUIT_NAME_WITH_NUMBERS_LINE);

        List<FruitTransaction> actual =
                transactionsFormer.formTransactionList(listWithWithNumberedFruit);

        assertEquals(fruitWithNumbersList, actual);
    }

    @Test
    public void transactionFormer_negativeQuantity_notOk() {
        List<String> listWithWithNegativeQuantity = new ArrayList<>();
        listWithWithNegativeQuantity.add(NEGATIVE_QUANTITY_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithNegativeQuantity));

        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT
                + NEGATIVE_QUANTITY + "\"", exception.getMessage());
    }

    @Test
    public void transactionFormer_zeroQuantity_Ok() {
        List<String> listWithWithZeroQuantity = new ArrayList<>();
        listWithWithZeroQuantity.add(ZERO_QUANTITY_LINE);

        List<FruitTransaction> actual =
                transactionsFormer.formTransactionList(listWithWithZeroQuantity);

        assertEquals(listWithZeroBananas, actual);
    }

    @Test
    public void transactionFormer_LetterQuantity_notOk() {
        List<String> listWithWithLetterQuantity = new ArrayList<>();
        listWithWithLetterQuantity.add(LETTER_QUANTITY_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithLetterQuantity));

        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT
                + STRING_QUANTITY + "\"", exception.getMessage());
    }

    @Test
    public void transactionFormer_symbolQuantity_notOk() {
        List<String> listWithWithSymbolQuantity = new ArrayList<>();
        listWithWithSymbolQuantity.add(SYMBOLS_INSTEAD_OF_QUANTITY_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithSymbolQuantity));

        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT + SYMBOLS + "\"", exception.getMessage());
    }

    @Test
    public void transactionFormer_noQuantity_notOk() {
        List<String> listWithWithNoQuantity = new ArrayList<>();
        listWithWithNoQuantity.add(NO_QUANTITY_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithNoQuantity));

        assertEquals(INCORRECT_LINE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void transactionFormer_emptyQuantity_notOk() {
        List<String> listWithWithEmptyQuantity = new ArrayList<>();
        listWithWithEmptyQuantity.add(EMPTY_QUANTITY_LINE);

        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithEmptyQuantity));

        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT + " \"", exception.getMessage());
    }

    private List<String> formCorrectList() {
        List<String> correctDataList = new ArrayList<>();
        correctDataList.add("b,banana,20");
        correctDataList.add("b,apple,100");
        correctDataList.add("s,banana,100");
        correctDataList.add("p,banana,13");
        correctDataList.add("r,apple,10");
        correctDataList.add("p,apple,20");
        correctDataList.add("p,banana,5");
        correctDataList.add("s,banana,50");
        return correctDataList;
    }
}
