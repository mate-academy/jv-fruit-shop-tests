package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.ReadFileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadFileTest {
    private static ReadFileService readFileServiceTest;
    private static String[] defaultCorrectData;

    @BeforeAll
    static void createReadServiceTest() {
        readFileServiceTest = new ReadFileServiceImpl();
        defaultCorrectData = new String[]{
                "b,banana,120",
                "b,apple,130",
                "r,banana,40",
                "s,banana,20",
                "p,apple,50",
                "p,banana,40"
        };
    }

    @Test
    void read_emptyPath_notOk() {
        assertThrows(ValidationDataException.class, () -> new ReadFileServiceImpl().read(""));
    }

    @Test
    void read_emptyFile_notOk() {
        assertThrows(ValidationDataException.class,
                () -> readFileServiceTest
                        .read("resources/testFiles/inputFileTestEmpty.csv"));
    }

    @Test
    void read_notCorrectPath_notOk() {
        assertThrows(ValidationDataException.class,
                () -> readFileServiceTest
                        .read("resources/newFolder/inputFileTestEmpty.csv"));
    }

    @Test
    void read_CorrectTest_ok() {
        String[] actual = readFileServiceTest
                .read("resources/testFiles/inputFileTestCorrectLines.csv");
        String[] expected = defaultCorrectData;
        assertArrayEquals(expected, actual);
    }
}
