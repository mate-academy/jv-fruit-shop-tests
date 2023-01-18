package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.model.Transaction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceTest {
    private static List<Transaction> listTransactions;
    private static final String FILE_FOR_WRITE = "src/main/resources/reportTotalResult.csv";
    private static final int SIZE_ZERO = 0;

    @Before
    public void setUp() {
        listTransactions = new ArrayList<>();
        listTransactions.add(new Transaction("apple", 2));
    }

    @Test
    public void reportWrite_Ok() {
        File csvOutputFile = new File(FILE_FOR_WRITE);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            for (Transaction result : listTransactions) {
                pw.println(result.getFruit() + "," + result.getQuantity());
            }
        } catch (FileNotFoundException e) {
            fail("Can't write file " + FILE_FOR_WRITE);
        }
    }

    @Test
    public void reportEmpty_NotOk() {
        assertTrue(listTransactions.size() > SIZE_ZERO);
    }

    @AfterClass
    public static void afterClass() {
        listTransactions.clear();
    }
}
