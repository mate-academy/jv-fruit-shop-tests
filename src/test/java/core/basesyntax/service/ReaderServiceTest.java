package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void init() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        String fileName = "src/test/recourses/input-test.csv";
        List<String> expected = List.of(
                "type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = readerService.readFromFile(fileName);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
     public void readFromNonExistentFile_NotOk() {
        readerService.readFromFile("");
    }
}
