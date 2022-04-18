package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ReadFileImplTest {
    private ReadFile readFile = new ReadFileImpl();
    private String correctPathToReadingFile = "src/main/resources/fullreport.csv";
    private String incorrectPathToReadingFile = "src/main/resources/t.csv";
    private String correctPathToUnreadableFile = "src/main/resources/unreadable_file.cvs";
    private String correctPathToEmptyFile = "src/main/resources/emptyfile.scv";

    @Test
    public void readFileToList_Ok() {
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
        readFile.readFileToList(incorrectPathToReadingFile);
    }

    @Test
    public void fileIsEmpty_NotOk() {
        List<String> actual = readFile.readFileToList(correctPathToEmptyFile);
        assertTrue(actual.isEmpty());
    }
}
