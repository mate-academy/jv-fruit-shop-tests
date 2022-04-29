package core.basesyntax.service.reader;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderServiceICsvImplTest {
    private static final String EMPTY_FILE_NAME = "test1";
    private static final String SECOND_FILE_NAME = "test2.csv";
    private static final List<String> RESULT_EMPTY_ARRAY = new ArrayList<>();
    private ReaderService readerService = new ReaderServiceICsvImpl();

    @Test
    public void readFromNoExistsFile_notOk() {
        try {
            String inputFile = "someNameFile";
            readerService.readFromFile(inputFile);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> file not exists");
    }

    @Test
    public void readFromFileListOfStrings_lines_Ok() {
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("b,banana,20");
        expectedResult.add("b,apple,100");
        expectedResult.add("s,banana,100");
        List<String> actual = readerService.readFromFile(SECOND_FILE_NAME);
        assertEquals(expectedResult, actual);
    }

    @Test
    public void inputFileIsEmpty_notOk() {
        List<String> actual = readerService.readFromFile(EMPTY_FILE_NAME);
        assertEquals(RESULT_EMPTY_ARRAY, actual);
    }

    @Test
    public void inputFileIsNull_notOk() {
        try {
            readerService.readFromFile(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("test should fail -> pathFile is null");
    }

}
