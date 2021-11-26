package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static Path existedFilePath;
    private static Path notExistedFilePath;
    private static Path emptyFilePath;

    @BeforeClass
    public static void beforeClass() throws Exception {
        readerService = new ReaderServiceImpl();
        existedFilePath = Paths.get("src/test/resources/inputData.csv");
        notExistedFilePath = Paths.get("src/test/resources/notExisted.csv");
        emptyFilePath = Paths.get("src/test/resources/emptyFile.csv");
    }

    @Test
    public void readFile_existedFile_Ok() {
        List<String> expected = List.of("first", "second", "third");
        List<String> actual = readerService.readFile(existedFilePath);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_notExistedFile_notOk() {
        readerService.readFile(notExistedFilePath);
    }

    @Test
    public void readFile_emptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = readerService.readFile(emptyFilePath);
        assertEquals(expected, actual);
    }
}
