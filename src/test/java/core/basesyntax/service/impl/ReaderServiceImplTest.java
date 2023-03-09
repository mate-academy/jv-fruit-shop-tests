package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String EMPTY_FILE = "src/test/java/testFiles/testEmpty.csv";
    private static final String WORKER_FILE = "src/test/java/testFiles/testInput.csv";
    private static final String UNREAL_FILE = "src/test/java/testFiles/unrealInput.csv";
    private static List<String> PROGRAM_CORRECT_RESULT;
    private static ReaderService reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderServiceImpl();
        PROGRAM_CORRECT_RESULT = new ArrayList<>();
        PROGRAM_CORRECT_RESULT.add("type,fruit,quantity");
        PROGRAM_CORRECT_RESULT.add("b,banana,20");
        PROGRAM_CORRECT_RESULT.add("b,apple,100");
        PROGRAM_CORRECT_RESULT.add("s,banana,100");
        PROGRAM_CORRECT_RESULT.add("p,banana,13");
        PROGRAM_CORRECT_RESULT.add("r,apple,10");
        PROGRAM_CORRECT_RESULT.add("p,apple,20");
        PROGRAM_CORRECT_RESULT.add("p,banana,5");
        PROGRAM_CORRECT_RESULT.add("s,banana,50");
    }

    @Test
    public void readFrom_testingFile_ok() {
        List<String> actual = reader.readFile(WORKER_FILE);
        assertEquals("Expected file content: " + PROGRAM_CORRECT_RESULT + ", but was: "
                + actual, actual, PROGRAM_CORRECT_RESULT);
    }

    @Test
    public void readFrom_emptyFile_ok() {
        List<String> actual = reader.readFile(WORKER_FILE);
        assertTrue("Expected file content: file must be empty, but was: "
                + actual, reader.readFile(EMPTY_FILE).isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_unrealFile_notOk() {
        reader.readFile(UNREAL_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_Null_notOk() {
        reader.readFile(null);
    }
}
