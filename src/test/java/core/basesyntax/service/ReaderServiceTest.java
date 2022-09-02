package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;

    @BeforeAll
    public static void init() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_Ok() {
        String fileName = "src/test/java/core/basesyntax/recourses/input-test.csv";
        List<String> expected = List.of(
                "type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = readerService.readFromFile(fileName);

        assertEquals(expected, actual);
    }

    @Test
    void readFromNonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(""));
    }
}
