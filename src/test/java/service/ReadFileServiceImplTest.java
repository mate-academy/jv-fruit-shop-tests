package service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileServiceImplTest {
    private static ReadFileService readFileService;

    @BeforeClass
    public static void beforeClass() {
        readFileService = new ReadFileServiceImpl();
    }

    @Test
    public void read_correctFilePath_Ok() {
        List<String> actual = readFileService.read("src/test/resources/TestInput.csv");
        List<String> expected = List.of("b-banana-20", "b-apple-100", "s-banana-100",
                "p-apple-20", "p-banana-5", "s-banana-50", "r-apple-30");
        assertEquals(actual,expected);
    }

    @Test(expected = RuntimeException.class)
    public void read_wrongPath_NotOk() {
        readFileService.read("src/test/java/core/resources/WrongInput.csv");
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyFile_NotOk() {
        readFileService.read("src/test/java/resources/EmptyInput.csv");
    }
}
