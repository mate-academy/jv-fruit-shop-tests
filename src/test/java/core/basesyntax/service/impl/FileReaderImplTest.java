package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String FILE_FOR_FIRST_TEST = "fileWithFruits.csv";
    private static final String FILE_FOR_SECOND_TEST = "emptyFile.csv";

    @BeforeAll
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void fileReader_completedList_ok() {
        List<String> expected =
                List.of("b,banana,10",
                        "b,apple,100",
                        "s,banana,100",
                        "p,banana,13",
                        "r,apple,10",
                        "p,apple,20",
                        "p,banana,5",
                        "s,banana,50");
        List<String> actual = fileReader.readFile(FILE_FOR_FIRST_TEST);

        assertEquals(expected, actual);
    }

    @Test
    public void fileReader_emptyList_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.readFile(FILE_FOR_SECOND_TEST);

        assertEquals(expected, actual);
    }

    @Test
    public void fileReader_fileFound_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.readFile("f");
        });
    }
}
