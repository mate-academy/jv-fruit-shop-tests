package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static List<String> correctText;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
        correctText = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void readFile_valid_Ok() {
        List<String> textInFile = readerService
                .readFromFile("src/test/java/resources/input_file.csv");
        textInFile.remove(0);
        assertEquals(correctText, textInFile);
    }

    @Test
    void readFile_notCorrectPath_notOk() {
        String notCorrectPath = "src/test/java//@%resources/input_file.csv.";
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(notCorrectPath));
    }
}
