package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;
    private static final String fileName = "src/test/resources/fruitsBalanceTest.csv";
    private static List<String> DEFAULT_DATA_TEST = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFruitsData_isValid() {
        List<String> actual = fileReaderService.readData(fileName);
        assertEquals(DEFAULT_DATA_TEST, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFruitsData_wrongFileName_isNotValid() {
        fileReaderService.readData("wrongName.csv");
    }

    @Test(expected = RuntimeException.class)
    public void readFruitsData_isNotValid() {
        fileReaderService.readData("can't read this.");
    }
}
