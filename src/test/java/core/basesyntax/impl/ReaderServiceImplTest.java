package core.basesyntax.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/main/resources/input.csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/empty.csv";
    private ReaderServiceImpl readerService;
    private List<String> records;

    @BeforeEach
    public void setUp() {
        readerService = new ReaderServiceImpl();
        records = new ArrayList<>();
        records.add("type,fruit,quantity");
        records.add("b,banana,20");
        records.add("b,apple,100");
        records.add("s,banana,100");
        records.add("p,banana,13");
        records.add("r,apple,10");
        records.add("p,apple,20");
        records.add("p,banana,5");
        records.add("s,banana,50");
    }

    @Test
    public void read_rightFile_ok() {
        List<String> actualResult = readerService.readData(INPUT_FILE_PATH);
        Assertions.assertEquals(records, actualResult);
    }

    @Test
    public void read_emptyFile_ok() {
        List<String> actualResult = readerService.readData(EMPTY_FILE_PATH);
        records = new ArrayList<>();
        Assertions.assertEquals(records, actualResult);
    }

    @Test
    public void read_emptyPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                readerService.readData(""));
    }

    @Test
    public void read_wrongPath_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                readerService.readData("wrongPath"));
    }
}
