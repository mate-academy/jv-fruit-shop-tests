package core.basesyntax.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INPUT_FILE = "input.csv";
    private static final String EMPTY_FILE = "empty.csv";
    private ReaderServiceImpl readerService = new ReaderServiceImpl();
    private List<String> valuesFromFile;

    @BeforeEach
    public void setUp() {
        readerService = new ReaderServiceImpl();
        valuesFromFile = new ArrayList<>();
        valuesFromFile.add("type,fruit,quantity");
        valuesFromFile.add("b,banana,20");
        valuesFromFile.add("b,apple,100");
        valuesFromFile.add("s,banana,100");
        valuesFromFile.add("p,banana,13");
        valuesFromFile.add("r,apple,10");
        valuesFromFile.add("p,apple,20");
        valuesFromFile.add("p,banana,5");
        valuesFromFile.add("s,banana,50");
    }

    @Test
    public void readRightFile_Ok() {
        List<String> actualResult = readerService.readFromFile("input.csv");
        Assertions.assertEquals(valuesFromFile.toString().trim(), actualResult.toString()
                .replaceAll("\\s+"," "));
    }

    @Test
    public void readEmptyFile_Ok() {
        List<String> actualResult = readerService.readFromFile("empty.csv");
        valuesFromFile = new ArrayList<>();
        Assertions.assertEquals(valuesFromFile.toString().trim(), actualResult.toString().trim());
    }

    @Test
    public void readEmptyFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                readerService.readFromFile(""));
    }
}
