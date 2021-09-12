package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.ReaderService;
import core.basesyntax.dao.ReaderServiceImp;
import core.basesyntax.dao.WriterService;
import core.basesyntax.dao.WriterServiceImp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceTest {
    private static ReaderService readerService;
    private static WriterService writerService;
    private static List<String> testInputList;
    private static final String wrongFilePath = "<:main:>";
    private static final String outFilePath = "src/main/resources/";
    private static final String[] testInput = new String[] {"b,banana,20",
            "s,banana,100", "p,banana,100", "r,banana,100"};

    @Before
    public void initialize() {
        readerService = new ReaderServiceImp();
        writerService = new WriterServiceImp();
        testInputList = Stream.of(testInput).collect(Collectors.toList());
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_Path_NotOk() {
        writerService.writeToFile(testInputList, wrongFilePath);
    }

    @Test
    public void writeToFile_Path_Ok() {
        String fullFilePath = writerService.writeToFile(testInputList, outFilePath);
        List<String> actual = readerService.readFromFile(fullFilePath);
        assertTrue(actual.containsAll(testInputList));
    }
}
