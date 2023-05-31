package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;
import core.basesyntax.service.impl.DataParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataParserImplTest {
    private DataParser parser;
    private FruitTransaction expectedFruitTransaction;

    @Before
    public void setUp() {
        parser = new DataParserImpl();
        expectedFruitTransaction = new FruitTransaction();
        expectedFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        expectedFruitTransaction.setFruitType("orange");
        expectedFruitTransaction.setFruitQuantity(20);
    }

    @Test
    public void makeFruitTransaction_Ok() {
        List<String[]> info = new ArrayList<>();
        info.add(new String[] {"b", "orange", "20"});
        List<FruitTransaction> actualFruitTransactionList = parser.parseFruitTransactions(info);
        FruitTransaction actualFruitTransaction = actualFruitTransactionList.get(0);
        assertEquals(expectedFruitTransaction, actualFruitTransaction);
    }
}
