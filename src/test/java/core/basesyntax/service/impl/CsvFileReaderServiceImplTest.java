package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String FILE_NAME_TEST
            = "src/test/resources/testInfo.csv";
    private CsvFileReaderService csvFileReaderService;

    @Before
    public void setUp() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nullFileName_notOk() {
        csvFileReaderService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_emptyFileName_notOk() {
        csvFileReaderService.readFromFile("");
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_wrongFileName_notOk() {
        csvFileReaderService.readFromFile("1234463.csv");
    }

    @Test
    public void readFromFile_rightFileName_Ok() {
        List<String> expected = List.of("type,fruit,quantity","b,orange,200",
                "b,kiwi,1000", "s,orange,1000", "p,orange,130", "r,kiwi,100",
                "p,kiwi,200", "p,orange,50", "s,orange,500");
        List<String> actual = csvFileReaderService.readFromFile(FILE_NAME_TEST);
        assertEquals(expected,actual);
    }
}
