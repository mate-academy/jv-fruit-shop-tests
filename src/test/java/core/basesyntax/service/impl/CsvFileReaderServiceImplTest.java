package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.WrongFileNameException;
import core.basesyntax.service.CsvFileReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String FILE_NAME_TEST
            = "src/main/resources/test/testInfo.csv";
    private CsvFileReaderService csvFileReaderService;

    @Before
    public void setUp() throws Exception {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test (expected = WrongFileNameException.class)
    public void readFromFile_nullFileNAme_notOk() {
        csvFileReaderService.readFromFile(null);
    }

    @Test (expected = WrongFileNameException.class)
    public void readFromFile_emptyFileNAme_notOk() {
        csvFileReaderService.readFromFile("");
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_wrongFileNAme_notOk() {
        csvFileReaderService.readFromFile("1234463.csv");
    }

    @Test
    public void readFromFile_Ok() {
        int expected = 9;
        int actual = 0;
        List<String> expectedList = List.of("type,fruit,quantity","b,orange,200",
                "b,kiwi,1000", "s,orange,1000", "p,orange,130", "r,kiwi,100",
                "p,kiwi,200", "p,orange,50", "s,orange,500");
        List<String> actualString = csvFileReaderService.readFromFile(FILE_NAME_TEST);
        for (int i = 0; i < actualString.size(); i++) {
            if (actualString.get(i).equals(expectedList.get(i))) {
                actual++;
            }
        }
        assertEquals(expected,actual);
    }
}
