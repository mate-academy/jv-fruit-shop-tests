package core.basesyntax;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.ReaderService;
import core.basesyntax.dao.ReaderServiceImp;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;
    private static final List<String> testInputList = List.of("b,banana,20",
            "s,banana,100", "p,banana,100", "r,banana,100");
    private static final String wrongFilePath = "<:main:>";
    private static final String nonExistingFile = "src/main/resources/non-existing-file.csv";
    private static final String inFilePath = "src/main/resources/test-input.csv";

    @Before
    public void initialize() {
        readerService = new ReaderServiceImp();
    }

    @Test
    public void readFromFile_ActivityCodes_Ok() {
        List<String> actual = readerService.readFromFile(inFilePath);
        assertTrue(actual.containsAll(testInputList));
    }

    @Test
    public void readFromFile_wrongFilePath_NotOk() {
        assertThrows(RuntimeException.class,() -> {
            readerService.readFromFile(wrongFilePath);
        });
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nonExistingFile_NotOk() {
        readerService.readFromFile(nonExistingFile);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_Null_NotOk() {
        readerService.readFromFile(null);
    }
}
