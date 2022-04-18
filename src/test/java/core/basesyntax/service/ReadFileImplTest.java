package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ReadFileImplTest {
    private final ReadFile readFile = new ReadFileImpl();

    @Test
    public void readFileToList_Ok() {
        String correctPathToReadingFile = "src/main/resources/fullreport.csv";
        List<String> actual = readFile.readFileToList(correctPathToReadingFile);
        List<String> expected = Arrays.asList("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void fileIsMissing_NotOk() {
        String incorrectPathToReadingFile = "src/main/resources/t.csv";
        readFile.readFileToList(incorrectPathToReadingFile);
    }

    @Test
    public void fileIsEmpty_NotOk() {
        String correctPathToEmptyFile = "src/main/resources/emptyfile.scv";
        List<String> actual = readFile.readFileToList(correctPathToEmptyFile);
        assertTrue(actual.isEmpty());
    }
}
