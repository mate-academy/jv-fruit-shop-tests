package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static Path existedFilePath;
    private static Path notExistedFilePath;
    private static Path emptyFilePath;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
        existedFilePath = Paths.get("src/test/resources/outputData.csv");
        notExistedFilePath = Paths.get("src/test/resources/notExistedWriter.csv");
        emptyFilePath = Paths.get("src/test/resources/emptyFile.csv");
        Files.deleteIfExists(notExistedFilePath);
    }

    @Test
    public void writeToFile_existedFile_Ok() throws IOException {
        String report = "one\ntwo\nthree";
        writerService.writeToFile(report, existedFilePath);
        List<String> expected = List.of("one", "two", "three");
        List<String> actual = Files.readAllLines(existedFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_notExistedFile_Ok() throws IOException {
        String report = "one\ntwo\nthree";
        writerService.writeToFile(report, notExistedFilePath);
        List<String> expected = List.of("one", "two", "three");
        List<String> actual = Files.readAllLines(notExistedFilePath);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyData_Ok() throws IOException {
        String report = "";
        writerService.writeToFile(report, emptyFilePath);
        List<String> expected = Collections.emptyList();
        List<String> actual = Files.readAllLines(emptyFilePath);
        assertEquals(expected, actual);
    }
}
