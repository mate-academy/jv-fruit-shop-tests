package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFile_withValidData_Ok() {
        String file = "src/test/java/resources/fruitsInfo.csv";
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        List<String> actual = readerService.readFile(file);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readFile_NonExistingFile_notOk() {
        String doesNotExist = "not_existing_File";
        assertThrows(RuntimeException.class, () -> readerService.readFile(doesNotExist));
    }

    @Test
    void readFile_fileIsEmpty_notOk() {
        List<String> actual = readerService.readFile("src/test/java/resources/emptyFile.csv");
        Assertions.assertTrue(actual.isEmpty());
    }
}
