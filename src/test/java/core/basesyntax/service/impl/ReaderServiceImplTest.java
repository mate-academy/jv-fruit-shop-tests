package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INVALID_PATH = "";
    private static final String VALID_PATH = "src/test/resources/input.csv";
    private final ReaderService reader = new ReaderServiceImpl();

    @Test
    public void read_ValidFile_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        List<String> linesFromFile = reader.readFromFile(VALID_PATH);
        Assert.assertEquals(lines, linesFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void readFromInvalidPath_NotOk() {
        reader.readFromFile(INVALID_PATH);
    }
}
