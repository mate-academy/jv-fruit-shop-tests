package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.MyFileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MyFileReaderImplTest {

    public static final String REPORT_FILE_PATH_TO_READ
            = "src/main/resources/reportToRead.csv";
    public static final String WRONG_PATH = "src/main/resources/fi/reportToRead.csv";
    private static List<String> report;
    private static MyFileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new MyFileReaderImpl();
        report = List.of("type,fruit,quantity","b,banana,20","b,apple,100",
                "s,banana,100","p,banana,13","r,apple,10",
                "p,apple,20","p,banana,5","s,banana,50");
    }

    @Test
    void read_exitingReport_ok() {
        List<String> actual = fileReader.readFromFile(REPORT_FILE_PATH_TO_READ);
        assertEquals(report, actual);
    }

    @Test
    void read_notExitingReport_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readFromFile(WRONG_PATH));
    }
}
