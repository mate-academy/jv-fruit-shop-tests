package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FileReaderImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/reportToRead.csv";
    private static final String INCORRECT_FILE_PATH = "src/test/resources/report.csv";
    private static FileReader fileReader;
    private static List<String> correctDataFromFile;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @BeforeEach
    void setUp() {
        correctDataFromFile = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
    }

    @Test
    void read_correctFailRead_Ok() {
        List<String> actual = fileReader.read(CORRECT_FILE_PATH);
        List<String> expected = correctDataFromFile;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void read_incorrectFailPath_NotOk() {
        Assertions.assertThrows(RuntimeException.class, ()-> fileReader.read(INCORRECT_FILE_PATH),
                "Expected RuntimeException was not thrown in " + FileReaderImpl.class);
    }
}