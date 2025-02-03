package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CsvDataConverterTest {
    private final List<FruitTransaction> validFruitTransactions = fillTransactionList();
    private final DataConverter dataConverter = new CsvDataConverterImpl();

    @Test
    public void convertToTransaction_validData_Ok() {
        List<String> actualArray = fillInputArray();
        List<FruitTransaction> currentTransactions
                = dataConverter.convertToTransaction(actualArray);
        assertEquals(validFruitTransactions, currentTransactions);
    }

    @Test
    public void convertToTransaction_invalidOperation_notOk() {
        List<String> invalidOperationList = fillInputArray();
        invalidOperationList.add("a,apple,20"); // wrong operation "a"
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidOperationList));
    }

    @Test
    public void convertToTransaction_emptyOperation_notOk() {
        List<String> invalidOperationList = fillInputArray();
        invalidOperationList.add(",apple,-5"); // empty operation ""
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidOperationList));
    }

    @Test
    public void convertToTransaction_emptyFruit_notOk() {
        List<String> invalidOperationList = fillInputArray();
        invalidOperationList.add("s,,-5"); // empty fruit ""
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidOperationList));
    }

    @Test
    public void convertToTransaction_nonMatchingPatternFruit_notOk() {
        List<String> invalidOperationList = fillInputArray();
        invalidOperationList.add("s, ,-5"); // whitespace fruit " "
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidOperationList));
    }

    @Test
    public void convertToTransaction_emptyQuantity_notOk() {
        List<String> invalidOperationList = fillInputArray();
        invalidOperationList.add("s,apple,"); // empty quantity ""
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidOperationList));
    }

    @Test
    public void convertToTransaction_nonNumberQuantity_notOk() {
        List<String> invalidOperationList = fillInputArray();
        invalidOperationList.add("s,apple,abc"); // non number quantity
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidOperationList));
    }

    @Test
    public void convertToTransaction_notAllParamsProvided_notOk() {
        List<String> invalidOperationList = fillInputArray();
        invalidOperationList.add("s,apple"); // only 2 params
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(invalidOperationList));
    }

    private List<FruitTransaction> fillTransactionList() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100));
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 100));
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 13));
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.RETURN,
                "apple", 10));
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20));
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 5));
        fruitTransactionList.add(createTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50));
        return fruitTransactionList;
    }

    private FruitTransaction createTransaction(FruitTransaction.Operation operation,
                                               String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(operation);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    private List<String> fillInputArray() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");
        return data;
    }
}
