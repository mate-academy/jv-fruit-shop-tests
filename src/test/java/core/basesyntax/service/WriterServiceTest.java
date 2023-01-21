package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceTest {
    private static List<Transaction> listTransactions;
    private static WriterServiceImpl writerService;
    private static ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
        listTransactions = new ArrayList<>();
        listTransactions.add(new Transaction("apple", 2));
    }

    @Test
    public void reportWrite_Ok() {
        writerService.writeFile(listTransactions);
        List<String> list = readerService.readAndGetList(
                "src/main/resources/reportTotalResult.csv");
        assertEquals(2, list.size());
    }

    @AfterClass
    public static void afterClass() {
        listTransactions.clear();
    }
}
