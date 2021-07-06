package core.basesyntax.servicetest;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_regularData_Ok() {
        List<String> expected = List.of("This is the test text",
                "it should match what the readerService",
                "read.", "Or may not match, it depends on what kind", "of java developer I am");
        List<String> actual = readerService.readFromFile("src/test/resources/output-file");
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s", expected, actual),
                expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongFileName_NotOk() {
        readerService.readFromFile("???");
    }

    @Test
    public void readFromFile_emptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = readerService.readFromFile("src/test/resources/empty-file");
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s", expected, actual),
                expected, actual);
    }
}
