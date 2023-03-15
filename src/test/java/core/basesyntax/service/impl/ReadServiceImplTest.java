package core.basesyntax.service.impl;

import core.basesyntax.service.ReadService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ReadServiceImplTest {
    private static final ReadService readService = new ReadServiceImpl();

    @Test(expected = RuntimeException.class)
    public void read_notExist_notOk() {
        readService.readFromFile("src/test/notExist.csv");
    }

    @Test
    public void read_return_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,orange,44",
                "b,banana,20",
                "b,apple,100",
                "s,orange,75",
                "s,banana,100");
        assertEquals(expected, readService.readFromFile("src/test/actual.csv"));
    }

}