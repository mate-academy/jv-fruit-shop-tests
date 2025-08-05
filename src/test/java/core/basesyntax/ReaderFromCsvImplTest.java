package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderFromCsv;
import core.basesyntax.service.ReaderFromCsvImpl;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderFromCsvImplTest {
    private static final String FILE_TO_READ = "src/test/resources/test_reportToRead.csv";
    private static ReaderFromCsv readerFromCsv;

    @BeforeAll
    static void setUp() {
        readerFromCsv = new ReaderFromCsvImpl();
    }

    @Test
    public void readFromFile_Ok() {
        Path path = Paths.get(FILE_TO_READ);
        String filePath = path.toString();
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = readerFromCsv.read(filePath);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_FileDoesNotExist_NotOk() {
        String filePath = "src/test/resources/non_existing.csv";
        assertThrows(RuntimeException.class, () ->
                readerFromCsv.read(filePath));
    }

    @Test
    public void readFromFile_FileIsEmpty_NotOk() {
        assertThrows(RuntimeException.class, () ->
                readerFromCsv.read(null));
    }
}
