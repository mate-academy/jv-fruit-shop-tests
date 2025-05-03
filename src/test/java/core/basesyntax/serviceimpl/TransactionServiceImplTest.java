package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.impl.TransactionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static final String TITLE_LINE = "type,fruit,quantity";
    private TransactionService transactionService;
    private List<String> data;

    @Before
    public void setUp() {
        transactionService = new TransactionServiceImpl();
        data = new ArrayList<>();
        data.add(TITLE_LINE);
    }

    @Test
    public void parse_validData_ok() {
        data.add("b,apple,152");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 152));
        List<FruitTransaction> actual = transactionService.toTransaction(data);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidOperationData_notOk() {
        data.add("##,banana,20");
        transactionService.toTransaction(data);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void parse_invalidFruitData_notOk() {
        data.add("b,20");
        transactionService.toTransaction(data);
    }

    @Test(expected = NumberFormatException.class)
    public void parse_invalidQuantityData_notOk() {
        data.add("b,banana,2$");
        transactionService.toTransaction(data);
    }
}
