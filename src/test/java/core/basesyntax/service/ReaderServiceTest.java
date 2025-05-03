package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService reader;
    private static List<String> lines;
    private static final String VALID_PATH = "src/test/resources/inputFile.csv";
    private static final String NOT_VALID_PATH = "src/test/resources/inputFile1.csv";

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderServiceImpl();
        lines = new ArrayList<>();
    }

    @Test
    public void readFromFile_validFilePath_ok() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("s,banana,100");
        lines.add("p,banana,99");
        lines.add("r,banana,5");
        List<String> actual = reader.readFromFile(VALID_PATH);
        assertEquals(lines, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nonExistentFile_notOk() {
        reader.readFromFile(NOT_VALID_PATH);
    }
}
