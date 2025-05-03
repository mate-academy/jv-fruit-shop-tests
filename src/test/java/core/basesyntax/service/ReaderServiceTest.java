package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService reader;

    @BeforeClass
    public static void initialize() {
        reader = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentFile_NotOK() {
        String from = "";
        reader.read(from);
    }

    @Test
    public void read_normalInput_ok() {
        String from = "src/test/resources/test.csv";
        List<String> expected = List.of("action,fruit,amount",
                "b,banana,10",
                "b,apple,20",
                "p,banana,5",
                "s,apple,5");
        assertEquals(expected, reader.read(from));
    }
}
