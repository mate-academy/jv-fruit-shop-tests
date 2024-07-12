package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String FILE_TO_READ = "src/test/reportToRead.csv";
    private static final String EMPTY_FILE = "src/test/empty.csv";
    private static final String NOT_EXISTENT_FILE = "";
    private static FileReader fileReader;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void reading_notEmptyFile_Ok() {
        List<String> stringList = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> strings = fileReader.readLinesFromFile(FILE_TO_READ);
        assertEquals(stringList, strings);
    }

    @Test
    void reading_emptyFile_notOk() {
        List<String> stringList = List.of();
        List<String> strings = fileReader.readLinesFromFile(EMPTY_FILE);
        assertEquals(stringList, strings);
    }

    @Test
    void reading_fileDoesntExist_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readLinesFromFile(NOT_EXISTENT_FILE));
    }
}
