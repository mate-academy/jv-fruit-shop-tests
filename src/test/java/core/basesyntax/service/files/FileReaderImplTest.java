package core.basesyntax.service.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_PATH =
            "src/main/java/core/basesyntax/recources/validForReader.csv";
    private static final String INVALID_PATH =
            "src/main/java/core/basesyntax/recources/invalid.csv";
    private static final String EMPTY_FILE =
            "src/main/java/core/basesyntax/recources/emptyForReader.csv";
    private final FileReader fileReader = new FileReaderImpl();
    private List<String> testList;

    @Test
    public void readFile_notValidPath_NotOk() {
        assertThrows("Must throw RuntimeExeption when path is not valid!",
                RuntimeException.class, () -> fileReader.readFile(INVALID_PATH));
    }

    @Test
    public void readFile_validPath_ok() {
        testList = new ArrayList<>();
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        List<String> expected = new ArrayList<>(testList);
        List<String> actual = fileReader.readFile(VALID_PATH);
        assertEquals("For valid path we should return valid list", expected, actual);
    }

    @Test
    public void readFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.readFile(EMPTY_FILE);
        assertEquals("For empty file we should return empty list", expected, actual);
    }
}
