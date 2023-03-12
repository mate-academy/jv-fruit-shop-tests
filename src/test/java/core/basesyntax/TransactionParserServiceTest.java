package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParserServiceTest extends FruitShopTest {
    private static final int SIZE_OF_DEFAULT_FILE = 8;

    @Test
    public void createTransaction_Ok() {
        List<String> infoInStrings = readerService.readFileToList(testFile);
        List<FruitTransaction> actual = transactionParser.createTransaction(infoInStrings);
        assertEquals(SIZE_OF_DEFAULT_FILE, actual.size());
    }

    @Test(expected = RuntimeException.class)
    public void createTransaction_wrongCode_Ok() {
        List<String> info = new ArrayList<>();
        info.add("operation,fruit,quantity");
        info.add("c,banana,75");
        transactionParser.createTransaction(info);
    }
}
