package core.basesyntax.read.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderFromFileImplTest {
    private static final String INPUT_FILE_PATH = "src\\test\\resources\\inputData.csv";
    private static final String INVALID_PATH = " ";
    private static ReaderFromFile readerFromFile;
    private static List<String> dataList;

    @BeforeAll
    static void beforeAll() {
        readerFromFile = new ReaderFromFileImpl();
        dataList = new ArrayList<>();
        dataList.add("b,banana,20");
        dataList.add("b,apple,100");
        dataList.add("s,banana,100");
        dataList.add("p,banana,13");
        dataList.add("r,apple,10");
        dataList.add("p,apple,20");
        dataList.add("p,banana,5");
        dataList.add("s,banana,50");
    }

    @Test
    void reader_readFromFile_Ok() {
        List<String[]> fromReader = readerFromFile.readFromFile(INPUT_FILE_PATH);
        List<String> actual = convertList(fromReader);
        List<String> expected = dataList;
        assertEquals(actual, expected, "Actual list "
                + actual + " doesn't equal expected list " + expected + ".");
    }

    @Test
    void reader_readFromFile_not0k() {
        assertThrows(RuntimeException.class,()
                -> readerFromFile.readFromFile(INVALID_PATH));
    }

    @Test
    void reader_nullValuePath_not0k() {
        assertThrows(RuntimeException.class,()
                -> readerFromFile.readFromFile(null));
    }

    private List<String> convertList(List<String[]> data) {
        List<String> stringList = new ArrayList<>();
        for (String[] line : data) {
            stringList.add(String.join(",", line));
        }
        return stringList;
    }
}
