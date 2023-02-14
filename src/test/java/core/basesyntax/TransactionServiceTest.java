package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.db.FruitTransaction;
import core.service.TransactionService;
import core.service.impl.TransactionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionServiceTest {
    private List<FruitTransaction> transactions = new ArrayList<>();

    @BeforeAll
    void setUp() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("p,banana,5");
        lines.add("s,banana,7");
        TransactionService testTransactionService = new TransactionServiceImpl();
        transactions = testTransactionService.createFromList(lines);
    }

    @Test
    void checkingListSize() {
        assertEquals(2, transactions.size(), "wrong list size");
    }

    @Test
    void getListTransactionsFromListString() {
        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(0).getOperation(),
                "Transaction 1. Error in the operation");
        assertEquals("banana", transactions.get(0).getFruit(),
                "Transaction 1. Error in the name of the fruit");
        assertEquals(5, transactions.get(0).getQuantity(),
                "Transaction 1. Error in quantity");

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation(),
                "Transaction 2. Error in the operation");
        assertEquals("banana", transactions.get(1).getFruit(),
                "Transaction 2. Error in the name of the fruit");
        assertEquals(7, transactions.get(1).getQuantity(),
                "Transaction 2. Error in quantity");

    }
}
