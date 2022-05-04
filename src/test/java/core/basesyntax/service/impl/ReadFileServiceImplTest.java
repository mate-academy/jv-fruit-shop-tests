package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReadFileService;
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
    public void read_FileWithCorrectInput_Ok() {
        List<String> actual = readFileService.read("src/test/resources/TestInput.csv");
        int expected = 7;
        assertEquals("Size not equals expected size!", actual.size(), expected);
    }

    @Test(expected = RuntimeException.class)
    public void read_WrongInputPath_NotOk() {
        readFileService.read("src/test/java/core/resources/WrongInput.csv");
    }

    @Test(expected = RuntimeException.class)
    public void read_EmptyFile_NotOk() {
        readFileService.read("src/test/java/resources/EmptyInput.csv");
    }
}
