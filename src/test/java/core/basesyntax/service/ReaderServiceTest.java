package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderServiceTest {
    @Test(expected = RuntimeException.class)
    public void reader_read_nonExistentFile_NotOK() {
        String from = "";
        new ReaderServiceImpl().read(from);
    }

    @Test
    public void reader_read_ok() {
        String from = "src/main/resources/test.csv";
        Assert.assertEquals(new ReaderServiceImpl().read(from),
                List.of("action,fruit,amount",
                        "b,banana,10",
                        "b,apple,20",
                        "p,banana,5",
                        "s,apple,5")
        );
    }

}
