package core.basesyntax.dao;

import core.basesyntax.models.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class DaoTest {

    @Test
    public void testDataConverter_Ok() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> input = List.of("type,product,quantity",
                "b,banana,20", "b,apple,20", "s,banana,30", "s,apple,40",
                "p,banana,10", "p,apple,10", "r,banana,70", "r,apple,80");
        List<FruitTransaction> expected = List.of(
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "apple", 20),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "banana", 30),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "apple", 40),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "banana", 10),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.PURCHASE, "apple", 10),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.RETURN, "banana", 70),
                FruitTransaction.of(FruitTransaction.TypeOfActivity.RETURN, "apple", 80));
        Assert.assertEquals(expected, dataConverter.convertToTransaction(input));
    }

    @Test
    public void testDataConverter_NotOk() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> input = List.of("type,product,quantity",
                "b,banana,20", "BALANCE,apple,20");
        Assert.assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(input));
    }

}
