package core.basesyntax.service;

import core.basesyntax.cvswork.FileReader;
import core.basesyntax.cvswork.FileReaderImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LineSeparatorImplTest {
    private List<FruitTransaction> fruitTransactions = new ArrayList();
    private List<FruitTransaction> emptyFile = new ArrayList();
    private List<FruitTransaction> transactions = new ArrayList();

    @Before
    public void setUp() {
        FileReader read = new FileReaderImpl();
        LineSeparator lineSeparator = new LineSeparatorImpl();
        transactions = lineSeparator.separator(
                read.read("src/test/java/core/basesyntax/resourse/withoutName.cvs"));
        emptyFile = lineSeparator.separator(
                read.read("src/test/java/core/basesyntax/resourse/emptyLine.cvs"));
        fruitTransactions = lineSeparator.separator(
                read.read("src/test/java/core/basesyntax/resourse/normalFile.cvs"));
    }

    @Test
    public void is_null_List() {
        int expected = 0;
        int actual = emptyFile.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void is_valid_filterFile() {
        int expected = 2;
        int actual = transactions.size();
        Assert.assertEquals(expected, actual);
        expected = 15;
        actual = fruitTransactions.size();
        Assert.assertEquals(expected, actual);
    }
}
