package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceTest {
    private static CsvFileReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFormFile_validFile_ok() {
        String validReadFromFile =
                "src/test/resources/ValidReadFromFile.csv";
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = readerService.readFormFile(validReadFromFile);
        assertEquals(expected, actual);
    }

    @Test
    public void readFormFile_threeFruits_ok() {
        String threeFruitsReadFromFile =
                "src/test/resources/ThreeFruitsValidFile.csv";
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "b,pear,50", "s,banana,100", "p,pear,20", "p,banana,13", "r,apple,10", "p,apple,20",
                "s,pear,40", "p,banana,5", "s,banana,50", "p,pear,30");
        List<String> actual = readerService.readFormFile(threeFruitsReadFromFile);
        assertEquals(expected, actual);
    }

    @Test
    public void readFormFile_invalidFile_notOk() {
        String invalidReadFromFile =
                "src/main/resources/FileDoestExist.csv";
        assertThrows(RuntimeException.class, () -> readerService.readFormFile(invalidReadFromFile));
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        String emptyFile = "src/test/resources/EmptyFile.csv";
        List<String> expected = Collections.EMPTY_LIST;
        List<String> actual = readerService.readFormFile(emptyFile);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_nullFile_notOk() {
        assertThrows(NullPointerException.class, () -> readerService.readFormFile(null));
    }
}
