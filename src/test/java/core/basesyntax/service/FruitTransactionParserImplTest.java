package core.basesyntax.service;

import core.basesyntax.cvswork.FileReader;
import core.basesyntax.cvswork.FileReaderImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class FruitTransactionParserImplTest {
    private static FileReader read;
    private static FruitTransactionParser lineSeparator;

    @BeforeAll
    public static void init() {
        lineSeparator = new FruitTransactionParserImpl();
        read = new FileReaderImpl();
    }

    @Test
    public void is_null_List_Ok() {
        init();
        int expected = 0;
        List<FruitTransaction> emptyFile = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/emptyLine.cvs"));
        int actual = emptyFile.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void is_valid_filterFile_Ok() {
        List<FruitTransaction> transactions = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/withoutName.cvs"));
        List<FruitTransaction> fruitTransactions = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/normalFile.cvs"));
        int expected = 2;
        int actual = transactions.size();
        Assert.assertEquals(expected, actual);
        expected = 15;
        actual = fruitTransactions.size();
        Assert.assertEquals(expected, actual);
    }
}
