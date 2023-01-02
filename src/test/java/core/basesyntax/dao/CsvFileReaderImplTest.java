package core.basesyntax.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static final String TEST_FILE = "src/test/resources/fromFileTest.csv";
    private final CsvFileReader csvFileReader = new CsvFileReaderImpl();

    @Test
    public void readTransactionsTestValidData_Ok() {
        List<FruitTransaction> actual
                = csvFileReader.readTransactions(TEST_FILE);
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void readTransactionsTestNoValidPath_NotOk() throws RuntimeException {
        String noValidPath = "ndbkam.sxc";
        csvFileReader.readTransactions(noValidPath);
    }
}
