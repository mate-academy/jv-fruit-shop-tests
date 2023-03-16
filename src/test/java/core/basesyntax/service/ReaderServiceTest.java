package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Test;

public class ReaderServiceTest {
    private static final ReaderService READER = new ReaderServiceImpl();

    @Test(expected = RuntimeException.class)
    public void reader_read_nonExistentFile_NotOK() {
        String from = "";
        READER.read(from);
    }

    @Test

    public void reader_read_ok() {
        String from = "src/main/resources/test.csv";
        assertEquals(READER.read(from),
                List.of("action,fruit,amount",
                        "b,banana,10",
                        "b,apple,20",
                        "p,banana,5",
                        "s,apple,5")
        );
    }

}
