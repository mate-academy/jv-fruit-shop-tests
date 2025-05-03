package core.basesyntax.fruitshop.service.reporthandlers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private FileWriterImpl write;
    private String filePath;
    private String actual;
    private String expected;

    @Before
    public void setUp() throws Exception {
        write = new FileWriterImpl();
        filePath = "ReportForFruitShop";
        expected = "sample of FruitShop Report";
    }

    @Test
    public void validValue_writeToFile_Ok() {
        File report = write.writeToFile("sample of FruitShop Report", filePath);
        try {
            actual = Files.readString(report.toPath());
        } catch (IOException e) {
            throw new RuntimeException("cant read test file");
        }
        assertEquals(expected, actual);
    }
}
