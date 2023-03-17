package service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionConverterImplTest {
    private static FruitTransactionConverter fruitTransactionConverter;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionConverter = new FruitTransactionConverterImpl();
    }

    @Test
    public void convertToFruitTransaction_correctData_Ok() {
        List<String> expected = List.of("b,apple,100", "s,plum,30", "s,grapes,90",
                                        "r,orange,46", "p,peach,82", "r,plum,18");
        List<FruitTransaction> transactions = fruitTransactionConverter
                                            .convertToFruitTransaction(expected);
        List<String> actual = transactions.stream()
                                 .map(t -> new StringBuilder(t.getOperation().getCode())
                                               .append(",").append(t.getFruit())
                                               .append(",").append(t.getAmount()))
                                 .map(StringBuilder::toString)
                                 .collect(Collectors.toList());
        Assert.assertEquals("Incorrect result.", expected, actual);
    }

    @Test
    public void convertToFruitTransaction_incorrectOperationSign_NotOk() {
        List<String> strings = List.of("d,banana,200", "a,plum,30", "k,cherry,19",
                                        "c,melon,325", "f,pineapple,68", "o,coconut,0");
        try {
            fruitTransactionConverter.convertToFruitTransaction(strings);
        } catch (NoSuchElementException e) {
            return;
        }
        Assert.assertEquals("For incorrect operation sign "
                        + "should throw NoSuchElementException", true, false);
    }

    @Test
    public void convertToFruitTransaction_incorrectData_NotOk() {
        List<String> strings = List.of("error", "Windows XP", "");
        List<FruitTransaction> fruitTransactions =
                fruitTransactionConverter.convertToFruitTransaction(strings);
        int expectedSize = 0;
        int actualSize = fruitTransactions.size();
        Assert.assertEquals("For inputted incorrect data, "
                        + "size of returned list should be 0", expectedSize, actualSize);
    }

    @Test
    public void convertToFruitTransaction_emptyList_Ok() {
        List<String> list = new ArrayList<>();
        List<FruitTransaction> fruitTransactions =
                    fruitTransactionConverter.convertToFruitTransaction(list);
        int expectedSize = 0;
        int actualSize = fruitTransactions.size();
        Assert.assertEquals("For inputted empty list, size of returned list should be 0",
                expectedSize, actualSize);
    }

    @Test
    public void convertToFruitTransaction_null_NotOk() {
        List<FruitTransaction> fruitTransactions =
                fruitTransactionConverter.convertToFruitTransaction(null);
        int expectedSize = 0;
        int actualSize = fruitTransactions.size();
        Assert.assertEquals("For inputted null, size of returned list should be 0",
                expectedSize, actualSize);
    }
}
