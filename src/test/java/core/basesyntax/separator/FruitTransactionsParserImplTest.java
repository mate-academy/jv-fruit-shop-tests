package core.basesyntax.separator;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Transaction;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;

public class FruitTransactionsParserImplTest {
    private static final List<String> validTestData = List.of(
            "type,fruit,quantity",
            "b,apple,100",
            "b,banana,100");
    private static final List<String> invalidTestData = List.of(
            "type,fruit,quantity",
            "b,100,apple");
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final FruitTransactionsParser parser = new FruitTransactionsParserImpl();

    @AfterClass
    public static void tearDown() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void create_validTransaction_Ok() {
        String expectedApple = "apple";
        String expectedBanana = "banana";
        List<Transaction> actualList = parser.transactionsParser(validTestData);
        String actualFirstName = actualList.get(FIRST_INDEX).getFruit().getName();
        String actualSecondName = actualList.get(SECOND_INDEX).getFruit().getName();
        assertEquals(expectedApple, actualFirstName);
        assertEquals(expectedBanana, actualSecondName);
    }

    @Test(expected = RuntimeException.class)
    public void create_invalidTransaction_notOk() {
        List<Transaction> actualList = parser.transactionsParser(invalidTestData);
        System.out.println(actualList);
    }

}
