package core.basesyntax.service;

import static java.nio.file.Files.readAllLines;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String FILE_INPUT = "src/main/resources/allTransactionForPeriod.csv";
    private static final int REDUCE_ON_ONE = 1;
    private static List<String> listAfterRead;
    private static ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        try {
            listAfterRead = readAllLines(Path.of(FILE_INPUT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file.");
        }
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
        List<String> list = readerService.readAndGetList(FILE_INPUT);
        assertFalse(list.isEmpty());
    }

    @Test
    public void getListTransaction_Ok() {
        List<Transaction> listTransaction = readerService.getListTransaction(FILE_INPUT);
        assertFalse(listTransaction.isEmpty());
    }

    @Test
    public void sizeTransactionEqualsSizeCsv_Ok() {
        assertEquals(listAfterRead.size() - REDUCE_ON_ONE,
                readerService.getListTransaction(FILE_INPUT).size());
    }

    @AfterClass
    public static void afterClass() {
        listAfterRead.clear();
    }
}
