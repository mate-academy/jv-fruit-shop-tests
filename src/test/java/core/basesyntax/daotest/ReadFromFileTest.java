package core.basesyntax.daotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ReadFromFile;
import core.basesyntax.dao.daoimpl.ReadFromFileImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReadFromFileTest {
    public static final int EXPECTED_SIZE_OF_EMPTY_LIST = 0;
    public static final String FILE_PATH = "src/test/resources/inputOk.csv";
    public static final String EMPTY_FILE_PATH = "src/test/resources/inputEmpty.csv";
    public static final String WRONG_FILE_PATH = "src/test/resources/blablabla";
    public static final String NULL_FILE_PATH = null;

    @Test
    void readFromFile_Ok() {
        List<String> expected = new ArrayList<>();
        fillTransactionsList(expected);
        ReadFromFile reader = new ReadFromFileImpl();
        List<String> actual = reader.readFromFile(FILE_PATH);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void readFromEmptyFile_Ok() {
        ReadFromFile reader = new ReadFromFileImpl();
        List<String> actual = reader.readFromFile(EMPTY_FILE_PATH);
        assertEquals(EXPECTED_SIZE_OF_EMPTY_LIST, actual.size());
    }

    @Test
    void readFromEmptyFile_NotOk() {
        List<String> expected = new ArrayList<>();
        fillTransactionsList(expected);
        ReadFromFile reader = new ReadFromFileImpl();
        List<String> actual = reader.readFromFile(EMPTY_FILE_PATH);
        assertEquals(EXPECTED_SIZE_OF_EMPTY_LIST, actual.size());
    }

    @Test
    void readFromNullPathFill_NotOk() {
        ReadFromFile reader = new ReadFromFileImpl();
        assertThrows(NullPointerException.class, () -> reader.readFromFile(NULL_FILE_PATH));
    }

    @Test
    void readFromWrongFile_NotOk() {
        ReadFromFile reader = new ReadFromFileImpl();
        assertThrows(RuntimeException.class, () -> reader.readFromFile(WRONG_FILE_PATH));
    }

    private void fillTransactionsList(List<String> list) {
        list.add("type,fruit,quantity");
        list.add("b,banana,50");
        list.add("b,apple,100");
        list.add("b,orange,100");
        list.add("s,banana,150");
        list.add("s,apple,1000");
        list.add("s,orange,2200");
        list.add("p,banana,13");
        list.add("r,orange,2");
    }
}
