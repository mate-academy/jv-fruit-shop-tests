package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readNotExistingFile_notOk() {
        reader.readFromFile("src/test/java/core/basesyntax/resources/notExistingFile.csv");
    }

    @Test
    public void readFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("b,orange,20");
        List<String> actual = reader.readFromFile(
                "src/test/java/core/basesyntax/resources/testFile.csv");
        assertEquals(expected, actual);
    }
}
