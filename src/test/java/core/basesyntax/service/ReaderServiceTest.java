package core.basesyntax.service;

import static java.nio.file.Files.readAllLines;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceTest {
    private static List<String> listAfterRead;
    private static final String FILE_FOR_READ = "src/main/resources/allTransactionForPeriod.csv";
    private static final String COMMA_SEPARATOR = ",";
    private static final int SIZE_ZERO = 0;
    private static final int REDUCE_ON_ONE = 1;
    private static final int AMOUNT_PARAMETERS = 3;
    private static ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        try {
            listAfterRead = readAllLines(Path.of(FILE_FOR_READ));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + FILE_FOR_READ);
        }
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void fileForReadNull_NotOk() {
        assertNotNull(FILE_FOR_READ);
    }

    @Test
    public void fileForReadEmpty_NotOk() {
        assertTrue(listAfterRead.size() > SIZE_ZERO);
    }

    @Test
    public void lineIsEmpty_NotOk() {
        for (String line : listAfterRead) {
            assertFalse(line.isEmpty());
        }
    }

    @Test
    public void lineWithParameters_Ok() {
        for (String line : listAfterRead) {
            String[] fields = line.split(COMMA_SEPARATOR);
            assertEquals(fields.length, AMOUNT_PARAMETERS);
        }
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
