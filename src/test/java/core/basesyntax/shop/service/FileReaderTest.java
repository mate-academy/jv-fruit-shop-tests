package core.basesyntax.shop.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testOkFileReaderImpl() throws IOException {
        FileReader fileReader = new FileReaderImpl();
        String expected = "type,fruit,quantity";
        String actual = fileReader.read("10.09.99")[0];
        assertEquals("File not right read", expected, actual);
    }

    @Test
    public void testNoOkFileReaderImpl() throws IOException {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Error!!! File not found!!!");
        FileReader fileReader = new FileReaderImpl();
        fileReader.read("nofile");
    }

}
