package core.basesyntax.service.transaction;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private TransactionParser transactionParser = new TransactionParserImpl();
    private FruitTransaction expect = new FruitTransaction();

    @BeforeEach
    void setUp() {
        expect.setOperation(FruitTransaction.Operation.PURCHASE);
        expect.setFruit("banana");
        expect.setQuantity(13);
    }

    @Test
    void getFruitTransaction_correctData_Ok() {
        List<String> transactionData = new ArrayList<>();
        transactionData.add("type,fruit,quantity");
        transactionData.add("p,banana,13");
        List<FruitTransaction> fruitTransactionList
                = transactionParser.getFruitTransaction(transactionData);
        assertEquals(fruitTransactionList.get(0), expect);
    }

    @Test
    void getFruitTransaction_incorrectData_NotOk() {
        List<String> transactionData = new ArrayList<>();
        transactionData.add("type,fruit,quantity");
        transactionData.add("p,banana");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            transactionParser.getFruitTransaction(transactionData);
        });
    }

    @Test
    void getFruitTransaction_emptyList_NotOk() {
        List<String> transactionData = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            List<FruitTransaction> fruitTransactionList
                    = transactionParser.getFruitTransaction(transactionData);
            assertEquals(fruitTransactionList.get(0), expect);
        });
    }

    @Test
    void getFruitTransaction_nullInList_NotOk() {
        List<String> transactionData = new ArrayList<>();
        transactionData.add("type,fruit,quantity");
        transactionData.add(null);
        assertThrows(NullPointerException.class, () -> {
            transactionParser.getFruitTransaction(transactionData);
        });
    }

    @Test
    void getFruitTransaction_null_NotOk() {
        assertThrows(NullPointerException.class, () -> {
            transactionParser.getFruitTransaction(null);
        });
    }
}
