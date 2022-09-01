package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private ReaderService readerService;

    @Before
    public void setUp() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readData_validInput_Ok() {
        String fileName = "src/test/resources/outputReport.csv";
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = readerService.readData(fileName);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_NotValidFileName_NotOk() {
        readerService.readData("");
    }
}
