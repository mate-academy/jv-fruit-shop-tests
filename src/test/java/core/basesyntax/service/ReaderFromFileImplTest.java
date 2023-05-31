package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderFromFileImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderFromFileImplTest {
    private static final String FROM_FILE_NAME = "src/test/resources/dataTestFile.csv";
    private static final String INCORRECT_FILE_NAME = "src/main/java/resources/";
    private static ReaderFromFile reader;
    private static List<String> testInputList;

    @BeforeAll
    static void beforeAll() {
        reader = new ReaderFromFileImpl();
        testInputList = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        testInputList.add("type,fruit,quantity");
        testInputList.add("b,banana,100");
        testInputList.add("s,banana,50");
        testInputList.add("p,banana,70");
        testInputList.add("r,banana,10");
    }

    @AfterEach
    void tearDown() {
        testInputList.clear();
    }

    @Test
    void readFromFile_IncorrectFileName_NotOk() {
        assertThrows(RuntimeException.class, () ->
                reader.readFromFile(INCORRECT_FILE_NAME));
    }

    @Test
    void readFromFile_ValidFileName_Ok() {
        List<String> actual = reader.readFromFile(FROM_FILE_NAME);

        assertEquals(5,actual.size());
        assertEquals(actual, testInputList);
    }
}
