package core.basesyntax.service.reader;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceICsvImplTest {
    private String emptyFileName;
    private String secondFileName;
    private ReaderService readerService;

    @Before
    public void setUp() throws Exception {
        emptyFileName = "test1";
        secondFileName = "test2.csv";
        readerService = new ReaderServiceICsvImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_readFromNoExistsFile_notOk() {
        String inputFile = "someNameFile";
        readerService.readFromFile(inputFile);
    }

    @Test
    public void readFromFile_validFile_ok() {
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("b,banana,20");
        expectedResult.add("b,apple,100");
        expectedResult.add("s,banana,100");
        expectedResult.add("p,banana,13");
        expectedResult.add("r,apple,10");
        expectedResult.add("p,apple,20");
        expectedResult.add("p,banana,5");
        expectedResult.add("s,banana,50");
        List<String> actual = readerService.readFromFile(secondFileName);
        assertEquals(expectedResult, actual);
    }

    @Test
    public void readFromFile_inputFileIsEmpty_notOk() {
        List<String> emptyList = new ArrayList<>();
        List<String> actual = readerService.readFromFile(emptyFileName);
        assertEquals(emptyList, actual);
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_inputFileIsNull_notOk() {
        readerService.readFromFile(null);
    }

}
