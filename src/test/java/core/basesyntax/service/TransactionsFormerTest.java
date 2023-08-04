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
    private static final String INCORRECT_CODE_EXCEPTION_TEXT = "Invalid code";
    private static final String INCORRECT_FRUIT_NAME_EXCEPTION_TEXT = "Incorrect fruit name ";
    private static final String INCORRECT_LINE_EXCEPTION_TEXT = "Wrong operation input";
    private static final String INCORRECT_QUANTITY_EXCEPTION_TEXT =
            "Quantity should ba a positive number, provided \"";
    private static final String NO_DATA_EXCEPTION_TEXT = "There is no operations to execute";
    private static final String NO_FRUIT_NAME_EXCEPTION_TEXT = "Fruit name can`t be null";
    private static final TransactionsFormerImpl transactionsFormer = new TransactionsFormerImpl();

    @Test
    public void formTransactionListFromCorrectData_Ok() {
        List<String> correctDataList = new ArrayList<>();
        correctDataList.add("b,banana,20");
        correctDataList.add("b,apple,100");
        correctDataList.add("s,banana,100");
        correctDataList.add("p,banana,13");
        correctDataList.add("r,apple,10");
        correctDataList.add("p,apple,20");
        correctDataList.add("p,banana,5");
        correctDataList.add("s,banana,50");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = transactionsFormer.formTransactionList(correctDataList);
        assertEquals(expected, actual);
    }

    @Test
    public void formTransactionListFromNull_notOk() {
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(null));
        assertEquals(NO_DATA_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListFromEmptyList_notOk() {
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(Collections.emptyList()));
        assertEquals(NO_DATA_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListWithIncorrectOperation_notOk() {
        List<String> listWithIncorrectOperation = new ArrayList<>();
        listWithIncorrectOperation.add("u,banana,20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithIncorrectOperation));
        assertEquals(INCORRECT_CODE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListWithNoOperation_notOk() {
        List<String> listWithNoOperation = new ArrayList<>();
        listWithNoOperation.add(",banana,20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithNoOperation));
        assertEquals(INCORRECT_CODE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListWithEmptyOperation_notOk() {
        List<String> listWithEmptyOperation = new ArrayList<>();
        listWithEmptyOperation.add(" ,banana,20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithEmptyOperation));
        assertEquals(INCORRECT_CODE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListWithNoFruit_notOk() {
        List<String> listWithNoFruit = new ArrayList<>();
        listWithNoFruit.add("b,,20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithNoFruit));
        assertEquals(NO_FRUIT_NAME_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListWithEmptyFruit_notOk() {
        List<String> listWithEmptyFruit = new ArrayList<>();
        listWithEmptyFruit.add("b,,20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithEmptyFruit));
        assertEquals(NO_FRUIT_NAME_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListWithNumberFruit_notOk() {
        List<String> listWithNumberFruit = new ArrayList<>();
        listWithNumberFruit.add("b,99,20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithNumberFruit));
        assertEquals(INCORRECT_FRUIT_NAME_EXCEPTION_TEXT + 99, exception.getMessage());
    }

    @Test
    public void formTransactionListWithSymbolsFruit_notOk() {
        List<String> listWithSymbolFruit = new ArrayList<>();
        listWithSymbolFruit.add("b,%!&,20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithSymbolFruit));
        assertEquals(INCORRECT_FRUIT_NAME_EXCEPTION_TEXT + "%!&", exception.getMessage());
    }

    @Test
    public void formTransactionListWithNumberedFruit_Ok() {
        List<String> listWithWithNumberedFruit = new ArrayList<>();
        listWithWithNumberedFruit.add("b,banana99,20");
        List<FruitTransaction> actual =
                transactionsFormer.formTransactionList(listWithWithNumberedFruit);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana99", 20));
        assertEquals(expected, actual);
    }

    @Test
    public void formTransactionListWithNegativeQuantity_notOk() {
        List<String> listWithWithNegativeQuantity = new ArrayList<>();
        listWithWithNegativeQuantity.add("b,banana,-20");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithNegativeQuantity));
        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT + -20 + "\"", exception.getMessage());
    }

    @Test
    public void formTransactionListWithZeroQuantity_Ok() {
        List<String> listWithWithZeroQuantity = new ArrayList<>();
        listWithWithZeroQuantity.add("b,banana,0");
        List<FruitTransaction> actual =
                transactionsFormer.formTransactionList(listWithWithZeroQuantity);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 0));
        assertEquals(expected, actual);
    }

    @Test
    public void formTransactionListWithLetterQuantity_notOk() {
        List<String> listWithWithLetterQuantity = new ArrayList<>();
        listWithWithLetterQuantity.add("b,banana,b");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithLetterQuantity));
        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT + "b\"", exception.getMessage());
    }

    @Test
    public void formTransactionListWithSymbolQuantity_notOk() {
        List<String> listWithWithSymbolQuantity = new ArrayList<>();
        listWithWithSymbolQuantity.add("b,banana,&!#");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithSymbolQuantity));
        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT + "&!#\"", exception.getMessage());
    }

    @Test
    public void formTransactionListWithNoQuantity_notOk() {
        List<String> listWithWithNoQuantity = new ArrayList<>();
        listWithWithNoQuantity.add("b,banana,");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithNoQuantity));
        assertEquals(INCORRECT_LINE_EXCEPTION_TEXT, exception.getMessage());
    }

    @Test
    public void formTransactionListWithEmptyQuantity_notOk() {
        List<String> listWithWithEmptyQuantity = new ArrayList<>();
        listWithWithEmptyQuantity.add("b,banana, ");
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> transactionsFormer.formTransactionList(listWithWithEmptyQuantity));
        assertEquals(INCORRECT_QUANTITY_EXCEPTION_TEXT + " \"", exception.getMessage());
    }
}
