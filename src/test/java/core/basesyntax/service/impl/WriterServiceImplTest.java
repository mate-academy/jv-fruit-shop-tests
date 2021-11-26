package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static ReaderService readerService;
    private static Path existedFilePath;
    private static Path notExistedFilePath;
    private static Path emptyFilePath;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
        existedFilePath = Paths.get("src/test/resources/outputData.csv");
        notExistedFilePath = Paths.get("src/test/resources/notExistedWriter.csv");
        emptyFilePath = Paths.get("src/test/resources/emptyFile.csv");
        Files.deleteIfExists(notExistedFilePath);
    }

    @Test
    public void writeToFile_existedFile_Ok() {
        String report = "one\ntwo\nthree";
        writerService.writeToFile(report, existedFilePath);
        List<String> expected = List.of("one", "two", "three");
        List<String> actual = readerService.readFile(existedFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_notExistedFile_Ok() {
        String report = "one\ntwo\nthree";
        writerService.writeToFile(report, notExistedFilePath);
        List<String> expected = List.of("one", "two", "three");
        List<String> actual = readerService.readFile(notExistedFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyData_Ok() {
        String report = "";
        writerService.writeToFile(report, emptyFilePath);
        List<String> expected = List.of();
        List<String> actual = readerService.readFile(emptyFilePath);
        assertEquals(expected, actual);
    }
}
