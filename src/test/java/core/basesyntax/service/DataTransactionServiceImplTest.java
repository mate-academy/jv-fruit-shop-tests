package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataTransactionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataTransactionServiceImplTest {
    private static DataTransactionService dataTransaction;

    @BeforeClass
    public static void setUp() {
        dataTransaction = new DataTransactionServiceImpl();
    }

    @Test
    public void turnDataToTransactions_parseData_ok() {
        String dataFromInput = new StringBuilder("type,fruit,quantity")
                .append(System.lineSeparator())
                .append("b,banana,20").append(System.lineSeparator())
                .append("b,apple,100").append(System.lineSeparator())
                .append("s,banana,100").append(System.lineSeparator())
                .append("p,banana,13").toString();
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        List<FruitTransaction> actual = dataTransaction.turnDataToTransactions(dataFromInput);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void turnDataToTransactions_nullInput_notOk() {
        dataTransaction.turnDataToTransactions(null);
    }

    @Test(expected = NumberFormatException.class)
    public void turnDataToTransactions_quantityNotNum_notOk() {
        String str = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,abcd" + System.lineSeparator();
        dataTransaction.turnDataToTransactions(str);
    }
}
