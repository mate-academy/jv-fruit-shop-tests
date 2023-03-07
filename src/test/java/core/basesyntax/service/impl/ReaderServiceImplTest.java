package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String EMPTY_FILE = "src/main/resources/testEmpty.csv";
    private static final String WORKER_FILE = "src/main/resources/testInput.csv";
    private static final String UNREAL_FILE = "src/main/resources/unrealInput.csv";
    private static List<String> WORKER_FILE_RESULT;
    private static ReaderService reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderServiceImpl();
        WORKER_FILE_RESULT = new ArrayList<>();
        WORKER_FILE_RESULT.add("type,fruit,quantity");
        WORKER_FILE_RESULT.add("b,banana,20");
        WORKER_FILE_RESULT.add("b,apple,100");
        WORKER_FILE_RESULT.add("s,banana,100");
        WORKER_FILE_RESULT.add("p,banana,13");
        WORKER_FILE_RESULT.add("r,apple,10");
        WORKER_FILE_RESULT.add("p,apple,20");
        WORKER_FILE_RESULT.add("p,banana,5");
        WORKER_FILE_RESULT.add("s,banana,50");
    }

    @Test
    public void reading_testingFile_isOk() {
        assertEquals(reader.readTransactionWithFile(WORKER_FILE), WORKER_FILE_RESULT);
    }

    @Test
    public void reading_emptyFile_isOk() {
        assertTrue(reader.readTransactionWithFile(EMPTY_FILE).isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void reading_unrealFile_isNotOk() {
        reader.readTransactionWithFile(UNREAL_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void reading_Null_notOk() {
        reader.readTransactionWithFile(null);
    }
}
