package core.basesyntax.separator;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionsParserImplTest {
    private static List<String> validTestData;
    private static List<String> invalidTestData;
    private static FruitTransactionsParser parser;

    @BeforeClass
    public static void setUp() {
        validTestData = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "b,banana,100");
        invalidTestData = List.of(
                "type,fruit,quantity",
                "b,100,apple");
        parser = new FruitTransactionsParserImpl();
    }

    @Test
    public void transactionParser_validTransaction_Ok() {
        String expectedApple = "apple";
        String expectedBanana = "banana";
        List<Transaction> actualList = parser.transactionsParser(validTestData);
        String actualFirstName = actualList.get(0).getFruit().getName();
        String actualSecondName = actualList.get(1).getFruit().getName();
        assertEquals(expectedApple, actualFirstName);
        assertEquals(expectedBanana, actualSecondName);
    }

    @Test(expected = RuntimeException.class)
    public void transactionParser_invalidTransaction_notOk() {
        List<Transaction> actualList = parser.transactionsParser(invalidTestData);
        System.out.println(actualList);
    }
}
