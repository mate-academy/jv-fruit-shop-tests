package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.cvswork.FileReader;
import core.basesyntax.cvswork.FileReaderImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {
    private static FileReader read;
    private static FruitTransactionParser lineSeparator;

    @BeforeAll
    public static void beforeAll() {
        lineSeparator = new FruitTransactionParserImpl();
        read = new FileReaderImpl();
    }

    @Test
    public void parse_emptyFile_ok() {
        int expected = 0;
        List<FruitTransaction> emptyFile = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/emptyLine.cvs"));
        int actual = emptyFile.size();
        assertEquals(expected, actual);
    }

    @Test
    public void parse_notEmptyFile_ok() {
        List<FruitTransaction> transactions = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/withoutName.cvs"));
        List<FruitTransaction> fruitTransactions = lineSeparator.parse(
                read.read("src/test/java/core/basesyntax/resourse/normalFile.cvs"));
        int expected = 2;
        int actual = transactions.size();
        assertEquals(expected, actual);
        expected = 15;
        actual = fruitTransactions.size();
        assertEquals(expected, actual);
    }
}
