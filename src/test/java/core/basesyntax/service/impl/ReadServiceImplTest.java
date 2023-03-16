package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReadService;
import java.util.List;
import org.junit.Test;

public class ReadServiceImplTest {
    private static final ReadService readService = new ReadServiceImpl();

    @Test(expected = RuntimeException.class)
    public void read_notExist_notOk() {
        readService.readFromFile("src/test/java/resources/notExist.csv");
    }

    @Test
    public void read_return_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,orange,44",
                "b,banana,20",
                "b,apple,100",
                "s,orange,75",
                "s,banana,100");
        assertEquals(expected, readService.readFromFile("src/test/java/resources/actual.csv"));
    }

    @Test
    public void read_emptyFile_Ok() {
        List<String> actual = readService.readFromFile("src/test/java/resources/emptyFile.csv");
        assertTrue("Empty List expected", actual.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void read_null_notOk() {
        readService.readFromFile(null);
    }
}
