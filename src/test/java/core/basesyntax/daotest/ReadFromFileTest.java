package core.basesyntax.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ReadFromFile;
import core.basesyntax.dao.daoimpl.ReadFromFileImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReadFromFileTest {
    public static final int EXPECTED_SIZE_OF_EMPTY_LIST = 0;
    public static final String FILE_PATH = "src/test/resources/inputOk.csv";
    public static final String EMPTY_FILE_PATH = "src/test/resources/inputEmpty.csv";
    public static final String WRONG_FILE_PATH = "src/test/resources/blablabla";
    public static final String NULL_FILE_PATH = null;
    public static final List<String> expected = List.of(
            "type,fruit,quantity", "b,banana,50", "b,apple,100",
            "b,orange,100", "s,banana,150", "s,apple,1000",
            "s,orange,2200", "p,banana,13", "r,orange,2");
    private static ReadFromFile reader;

    @BeforeAll
    static void beforeAll() {
        reader = new ReadFromFileImpl();
    }

    @Test
    void readFromFile_Ok() {
        List<String> actual = reader.readFromFile(FILE_PATH);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void readFromEmptyFile_Ok() {
        List<String> actual = reader.readFromFile(EMPTY_FILE_PATH);
        assertEquals(EXPECTED_SIZE_OF_EMPTY_LIST, actual.size());
    }

    @Test
    void readFromEmptyFile_NotOk() {
        List<String> actual = reader.readFromFile(EMPTY_FILE_PATH);
        assertEquals(EXPECTED_SIZE_OF_EMPTY_LIST, actual.size());
    }

    @Test
    void readFromNullPathFill_NotOk() {
        assertThrows(NullPointerException.class, () -> reader.readFromFile(NULL_FILE_PATH));
    }

    @Test
    void readFromWrongFile_NotOk() {
        assertThrows(RuntimeException.class, () -> reader.readFromFile(WRONG_FILE_PATH));
    }
}
