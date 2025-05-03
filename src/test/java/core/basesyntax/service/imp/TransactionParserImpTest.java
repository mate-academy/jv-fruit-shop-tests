package core.basesyntax.service.imp;

import core.basesyntax.model.ProductTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImpTest {
    private static final String CORRECT_LINE_BALANCE = "b,apple,20";
    private static final String CORRECT_LINE_SUPPLY = "s,apple,20";
    private static final String CORRECT_LINE_PURCHASE = "p,apple,20";
    private static final String CORRECT_LINE_RETURN = "r,apple,20";
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImp();
    }

    @Test
    public void parse_validBalanceFull_ok() {
        String productName = "apple";
        int quantity = 20;
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_BALANCE);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.BALANCE,
                transaction.getOperation());
        Assert.assertEquals("Product name: ", productName, transaction.getProduct());
        Assert.assertEquals("Product quantity: ", quantity, transaction.getQuantity());
    }

    @Test
    public void parse_validSupply_ok() {
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_SUPPLY);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.SUPPLY,
                transaction.getOperation());
    }

    @Test
    public void parse_validPurchase_ok() {
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_PURCHASE);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.PURCHASE,
                transaction.getOperation());
    }

    @Test
    public void parse_validReturn_ok() {
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_RETURN);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.RETURN,
                transaction.getOperation());
    }

    @Test
    public void parseAll_validInput_ok() {
        List<String> records = new ArrayList<>();
        records.add(CORRECT_LINE_BALANCE);
        records.add(CORRECT_LINE_SUPPLY);
        records.add(CORRECT_LINE_PURCHASE);
        records.add(CORRECT_LINE_RETURN);
        List<ProductTransaction> list = transactionParser.parseAll(records);
        Assert.assertEquals("Numbers of records: ", records.size(), list.size());

    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidOperationType_notOk() {
        String invalidTypeLine = "j,apple,20";
        transactionParser.parse(invalidTypeLine);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidQuantity_notOk() {
        String zeroQuantityLine = "b,apple,0";
        transactionParser.parse(zeroQuantityLine);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidRecords_notOk() {
        String invalidRecords = "banana, 0";
        transactionParser.parse(invalidRecords);
    }
}
