package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String TO_FILE_NAME = "src/test/resources/OutputTestData.csv";
    private static final String FILE_WITH_ALREADY_INPUT_DATA =
            "src/test/resources/FileWithTestedText.csv";
    private static final String EMPTY_FILE = "src/test/resources/EmptyFile.csv";
    private static final String FROM_FILE_NAME = "src/test/unknownFolder/OutputTestData.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_folderWithFileDoesNotExist_notOk() {
        String testedInfo = "fruit,quantity";
        writerService.writeFile(FROM_FILE_NAME, testedInfo);
    }

    @Test
    public void writeFile_ok() {
        String textToWrite = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        writerService.writeFile(TO_FILE_NAME, textToWrite);
        List<String> fromWrittenFile;
        try {
            fromWrittenFile = Files.readAllLines(Path.of(TO_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + TO_FILE_NAME, e);
        }
        List<String> fromFileWithTestedText;
        try {
            fromFileWithTestedText = Files.readAllLines(Path.of(FILE_WITH_ALREADY_INPUT_DATA));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + TO_FILE_NAME, e);
        }
        Assert.assertEquals(fromFileWithTestedText,fromWrittenFile);
    }

    @Test
    public void writeFile_emptyFile_ok() {
        String textToWrite = "";
        writerService.writeFile(TO_FILE_NAME, textToWrite);
        List<String> fromWrittenFile;
        try {
            fromWrittenFile = Files.readAllLines(Path.of(TO_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + TO_FILE_NAME, e);
        }
        List<String> fromEmptyFile;
        try {
            fromEmptyFile = Files.readAllLines(Path.of(EMPTY_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + TO_FILE_NAME, e);
        }
        Assert.assertEquals(fromEmptyFile,fromWrittenFile);
    }
}
