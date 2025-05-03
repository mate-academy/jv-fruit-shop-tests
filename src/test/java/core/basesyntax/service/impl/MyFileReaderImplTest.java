package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.MyFileReader;
import java.io.File;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MyFileReaderImplTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private final MyFileReader myFileReader = new MyFileReaderImpl();

    @Test
    public void readInfo_valid_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,57",
                "s,banana,50");
        List<String> actual = myFileReader.readFromFile(
                "src" + File.separator + "main" + File.separator
                        + "resources" + File.separator + "input.csv");
        assertEquals(expected, actual);
    }

    @Test
    public void readInfo_nullPath_notOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.reportMissingExceptionWithMessage("Should get exception for null file path");
        myFileReader.readFromFile(null);
    }

    @Test
    public void readInfo_emptyPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage("Should get exception for empty file path");
        myFileReader.readFromFile("");
    }

    @Test
    public void readInfo_nonExistentPath_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.reportMissingExceptionWithMessage(
                "Should get exception for nonExistent file path");
        myFileReader.readFromFile("src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "nonExistent.csv");
    }
}
