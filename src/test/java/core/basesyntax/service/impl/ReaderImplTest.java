package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String EMPTY_FILE = "src/main/resources/testEmpty.csv";
    private static final String WORKER_FILE = "src/main/resources/testInput.csv";
    private static final String UNREAL_FILE = "src/main/resources/unrealInput.csv";
    private static final String WORKER_FILE_RESULT = "type,fruit,quantity///"
            + "b,banana,20///b,apple,100///s,banana,100///p,banana,13///r,apple,10"
            + "///p,apple,20///p,banana,5///s,banana,50///";

    private static ReaderImpl reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test
    public void reading_testingFile_isOk() {
        assertEquals(reader.readerTransaction(WORKER_FILE), WORKER_FILE_RESULT);
    }

    @Test
    public void reading_emptyFile_isOk() {
        assertEquals(reader.readerTransaction(EMPTY_FILE), "");
    }

    @Test(expected = RuntimeException.class)
    public void reading_unrealFile_isNotOk() {
        reader.readerTransaction(UNREAL_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void reading_Null_notOk() {
        reader.readerTransaction(null);
    }
}
