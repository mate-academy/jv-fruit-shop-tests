package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String FILE_PATH_TO_READ = "src/test/resources/reportToRead.csv";
    private static final String NEGATIVE_QUANTITY = "src/test/resources/negativeQuantity.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String WRONG_FILE_PATH = "src/main/resources/emptyFile.csv";
    private static List<String> expectedList;
    private static Reader fileReader;

    @BeforeAll
    static void beforeAll() {
        expectedList = new ArrayList<>();
        fileReader = new FileReaderImpl();
    }

    @AfterEach
    public void afterEachTest() {
        expectedList.clear();
    }

    @Test
    void readFile_validDataInput_ok() {
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        List<String> expected = expectedList;
        List<String> actual = fileReader.readFile(FILE_PATH_TO_READ);
        assertEquals(expected, actual);
    }

    @Test
    void readFile_EmptyFileInput_notOk() {
        List<String> expected = expectedList;
        List<String> actual = fileReader.readFile(EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void readFile_WrongFilePathInput_notOk() {
        assertThrows(RuntimeException.class, () -> fileReader.readFile(WRONG_FILE_PATH));
    }

    @Test
    void readFile_negativeQuantityInput_notOk() {
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,-100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        List<String> expected = expectedList;
        List<String> actual = fileReader.readFile(NEGATIVE_QUANTITY);
        assertEquals(expected, actual);
    }
}
