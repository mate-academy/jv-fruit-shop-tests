package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderFileService;
import core.basesyntax.service.impl.ReaderFileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadFileServiceImplTest {
    private static String pathFileRead = "src/test/resources/textTest.csv";
    ReaderFileService readerFileService;
    @BeforeEach
    void setUp() {
        readerFileService = new ReaderFileServiceImpl();
    }

    @Test
    void readFile_correctPath_ok() {
        String actual = readerFileService.readFile(pathFileRead);
        String expected = "type,fruit,quantity\nb,banana,20\nb,apple,100";
        assertEquals(expected, actual);
    }

    @Test
    void readFile_pathNull_notOk() {
        assertThrows(RuntimeException.class, () -> readerFileService.readFile(null));
    }

    @Test
    void readFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class, () -> readerFileService.readFile(""));
    }
}
