package core.basesyntax.operationswithfile;

import static org.junit.Assert.assertEquals;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TRUE_PATH = "src/test/java/resources/file.csv";
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
        boolean actual = false;
        if (Files.exists(Path.of("src/test/java/resources/file.csv"))){
            actual = true;
        }
        assertEquals(true,actual);
    }

    @Test(expected = RuntimeException.class)
    public void testWriteToFileWithFalsePath_assertException() {
        fileWriter.getNewFile(balance,FALSE_PATH);
    }
}
