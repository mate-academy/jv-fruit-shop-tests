package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {

    private static final String CORRECT_FILE_PATH = "src/main/resources/input.csv";
    private static final String WRONG_FILE_PATH = "src/main/resources/unknown.csv";
    private static final List<String> CORRECT_DATA = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void readerService_getCorrectData_Ok() {
        List<String> actual = readerService.readFromFile(CORRECT_FILE_PATH);
        assertEquals(CORRECT_DATA, actual);
    }

    @Test
    public void readerService_IncorrectPath_NotOk() {
        try {
            readerService.readFromFile(WRONG_FILE_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown if path is wrong!");
    }
}
