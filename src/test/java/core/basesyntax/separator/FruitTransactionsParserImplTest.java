package core.basesyntax.separator;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Transaction;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionsParserImplTest {
    private static List<String> validTestData;
    private static List<String> invalidTestData;
    private static FruitTransactionsParser parser;

    @AfterClass
    public static void tearDown() {
        FruitShopStorage.storageFruits.clear();
    }

    @BeforeClass
    public static void setUo() {
        parser = new FruitTransactionsParserImpl();
    }

    @Test
    public void create_validTransaction_Ok() {
        validTestData = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "b,banana,100");
        String expectedApple = "apple";
        String expectedBanana = "banana";
        List<Transaction> actualList = parser.transactionsParser(validTestData);
        String actualFirstName = actualList.get(0).getFruit().getName();
        String actualSecondName = actualList.get(1).getFruit().getName();
        assertEquals(expectedApple, actualFirstName);
        assertEquals(expectedBanana, actualSecondName);
    }

    @Test(expected = RuntimeException.class)
    public void create_invalidTransaction_notOk() {
        invalidTestData = List.of(
                "type,fruit,quantity",
                "b,100,apple");
        List<Transaction> actualList = parser.transactionsParser(invalidTestData);
        System.out.println(actualList);
    }

}
