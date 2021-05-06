package core.basesyntax.operationswithfile;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TRUE_PATH = "src/test/java/resources/file1.csv";
    private static final String FALSE_PATH = "srv/test/java/core/basesyntax/resources/newFile.csv";
    private static final Map<String, Integer> balance = new HashMap<>();
    private static final FileWriter fileWriter = new FileWriterImpl();

    @BeforeClass
    public static void addToBalanceMap() {
        balance.put("banana", 50);
        balance.put("apple",20);
    }

    @Test
    public void testWriteToFileWithTruePath_ok() {
        fileWriter.getNewFile(balance,TRUE_PATH);
        File file = new File(TRUE_PATH);
        long expected = 37;
        long actual = file.length();
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void testWriteToFileWithFalsePath_assertException() {
        fileWriter.getNewFile(balance,FALSE_PATH);
    }
}
