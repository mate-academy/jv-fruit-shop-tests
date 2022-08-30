package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.NumberOfColumnsException;
import core.basesyntax.exceptions.WrongOperationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private List<String> inputData = new ArrayList<>();
    private FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl();

    @Before
    public void setUp() {
        inputData.add(TITLE);
    }

    @Test
    public void get_fruitTransaction_Ok() {
        inputData.add("b,banana,20");
        inputData.add("b,apple,100");
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(inputData);
        int actualSize = fruitTransactions.size();
        FruitTransaction.Operation actualOperation = fruitTransactions.get(0).getOperation();
        String actualFruitName = fruitTransactions.get(0).getFruit();
        assertEquals(2, actualSize);
        assertEquals(FruitTransaction.Operation.BALANCE, actualOperation);
        assertEquals("banana", actualFruitName);
        int actualFruitQuantity = fruitTransactions.get(0).getQuantity();
        assertEquals(20, actualFruitQuantity);
    }

    @Test
    public void get_supplyActivity_Ok() {
        inputData.add("s,banana,100");
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(inputData);
        FruitTransaction.Operation actual = fruitTransactions.get(0).getOperation();
        assertEquals(FruitTransaction.Operation.SUPPLY, actual);
    }

    @Test
    public void get_purchaseActivity_Ok() {
        inputData.add("p,banana,100");
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(inputData);
        FruitTransaction.Operation actual = fruitTransactions.get(0).getOperation();
        assertEquals(FruitTransaction.Operation.PURCHASE, actual);
    }

    @Test
    public void get_returnActivity_Ok() {
        inputData.add("r,banana,100");
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(inputData);
        FruitTransaction.Operation actual = fruitTransactions.get(0).getOperation();
        assertEquals(FruitTransaction.Operation.RETURN, actual);
    }

    @Test(expected = WrongOperationException.class)
    public void get_wrongActivity_NotOk() {
        inputData.add("w,banana,100");
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(inputData);
    }

    @Test(expected = NumberOfColumnsException.class)
    public void get_invalidNumberOfColumns_NotOk() {
        inputData.add("b,banana");
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(inputData);
    }

    @Test(expected = NumberFormatException.class)
    public void get_wrongFruitQuantityFormat_NotOk() {
        inputData.add("b,banana,ten");
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void get_inputDataIsNull_NotOk() {
        List<FruitTransaction> fruitTransactions = fruitTransactionService.get(null);
    }
}
