package core.basesyntax.service;

import static java.nio.file.Files.readAllLines;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceTest {
    private static final int REDUCE_ON_ONE = 1;
    private static List<String> listAfterRead;
    private static ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        try {
            listAfterRead = readAllLines(Path.of("src/main/resources/allTransactionForPeriod.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file.");
        }
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void sizeTransactionEqualsSizeCsv_Ok() {
        assertEquals(listAfterRead.size() - REDUCE_ON_ONE,
                readerService.getListTransaction().size());
    }

    @AfterClass
    public static void afterClass() {
        listAfterRead.clear();
    }
}
