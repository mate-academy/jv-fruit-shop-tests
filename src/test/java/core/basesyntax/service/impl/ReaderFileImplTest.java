package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderFile;
import java.util.List;
import org.junit.Test;

public class ReaderFileImplTest {
    private static final String FILE_NAME_TEST = "src/test/resources/inputFileTest.csv";
    private static final ReaderFile readerFile = new ReaderFileImpl();

    @Test
    public void readFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> actual = readerFile.readFromFile(FILE_NAME_TEST);
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void readIncorectFilePath_NotOk() {
        readerFile.readFromFile("src/test/resources/incorectFileName");
    }

    @Test
    public void readNullFile_Ok() {
        readerFile.readFromFile("src/test/resources/inputNullFile.csv");
    }

    @Test (expected = NullPointerException.class)
    public void readPathIsNull_notOk() {
        readerFile.readFromFile(null);
    }
}
