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
    private static final String INVALID_TYPE_LINE = "j,apple,20";
    private static final String ZERO_QUANTITY_LINE = "b,apple,0";
    private static final String INVALID_RECORDS = "banana, 0";
    private static final String APPLE = "apple";
    private static final int QUANTITY = 20;
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImp();
    }

    @Test
    public void parseValidBalanceFull_ok() {
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_BALANCE);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.BALANCE,
                transaction.getOperation());
        Assert.assertEquals("Product name: ", APPLE, transaction.getProduct());
        Assert.assertEquals("Product quantity: ", QUANTITY, transaction.getQuantity());
    }

    @Test
    public void parseValidSupply_ok() {
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_SUPPLY);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.SUPPLY,
                transaction.getOperation());
    }

    @Test
    public void parseValidPurchase_ok() {
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_PURCHASE);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.PURCHASE,
                transaction.getOperation());
    }

    @Test
    public void parseValidReturn_ok() {
        ProductTransaction transaction = transactionParser.parse(CORRECT_LINE_RETURN);
        Assert.assertEquals("Product type: ", ProductTransaction.Operation.RETURN,
                transaction.getOperation());
    }

    @Test
    public void parseAllValidInput_ok() {
        List<String> records = new ArrayList<>();
        records.add(CORRECT_LINE_BALANCE);
        records.add(CORRECT_LINE_SUPPLY);
        records.add(CORRECT_LINE_PURCHASE);
        records.add(CORRECT_LINE_RETURN);
        List<ProductTransaction> list = transactionParser.parseAll(records);
        Assert.assertEquals("Numbers of records: ", records.size(), list.size());

    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidOperationType_notOk() {
        transactionParser.parse(INVALID_TYPE_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidQuantity_notOk() {
        transactionParser.parse(ZERO_QUANTITY_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidRecords_notOk() {
        transactionParser.parse(INVALID_RECORDS);
    }

}
