package core.basesyntax.service;

import static org.hamcrest.CoreMatchers.is;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService readerService;
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static final String SECOND_LINE = "b,apple,50";
    private static final String CORRECT_FILE_PATH = "src/main/resources/correct-file.Csv";
    private static final String CORRECT_FILE_WITHOUT_TRANSACTIONS_PATH
            = "src/main/resources/correct-file-without-transactions.Csv";
    private static final String FILE_WITHOUT_INFO_LINE_PATH
            = "src/main/resources/file-without-info-line.Csv";
    private static final String NOT_EXISTED_FILE_PATH = "src/main/resources/not-exist-database.Csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/empty.Csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        readerService = new ReaderServiceImpl();
        Files.createFile(Path.of(CORRECT_FILE_PATH));
        Files.createFile(Path.of(CORRECT_FILE_WITHOUT_TRANSACTIONS_PATH));
        Files.createFile(Path.of(FILE_WITHOUT_INFO_LINE_PATH));
        Files.createFile(Path.of(EMPTY_FILE_PATH));
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Files.delete(Path.of(CORRECT_FILE_PATH));
        Files.delete(Path.of(CORRECT_FILE_WITHOUT_TRANSACTIONS_PATH));
        Files.delete(Path.of(FILE_WITHOUT_INFO_LINE_PATH));
        Files.delete(Path.of(EMPTY_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void read_notExistedFilePath_notOk() {
        readerService.read(NOT_EXISTED_FILE_PATH);
    }

    @Test
    public void read_emptyFilePath_NotOk() {
        try {
            readerService.read(EMPTY_FILE_PATH);
            Assert.fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("File is empty"));
        }
    }

    @Test
    public void read_nullPath_notOk() {
        try {
            readerService.read(null);
            Assert.fail("Expected a RuntimeException to be thrown");
        } catch (RuntimeException e) {
            Assert.assertThat(e.getMessage(), is("Path is null"));
        }
    }

    @Test
    public void read_correctFileData_Ok() throws IOException {
        List<String> expected = new ArrayList<>();
        expected.add(FIRST_LINE);
        expected.add(SECOND_LINE);
        Files.writeString(Path.of(CORRECT_FILE_PATH), FIRST_LINE
                + System.lineSeparator() + SECOND_LINE);
        List<String> actual = readerService.read(CORRECT_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }
}
