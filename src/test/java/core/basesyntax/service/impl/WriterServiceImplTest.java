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
    private static WriterService writerService;

    @BeforeClass
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_folderWithFileDoesNotExist_notOk() {
        String fromFileName = "src/test/unknownFolder/OutputTestData.csv";
        String testedInfo = "fruit,quantity";
        writerService.writeFile(fromFileName, testedInfo);
    }

    @Test
    public void writeFile_ok() {
        String toFileName = "src/test/resources/OutputTestData.csv";
        String fileWithAlreadyInputData = "src/test/resources/FileWithTestedText.csv";
        String textToWrite = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        writerService.writeFile(toFileName, textToWrite);
        List<String> fromWrittenFile;
        try {
            fromWrittenFile = Files.readAllLines(Path.of(toFileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + toFileName, e);
        }
        List<String> fromFileWithTestedText;
        try {
            fromFileWithTestedText = Files.readAllLines(Path.of(fileWithAlreadyInputData));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + toFileName, e);
        }
        Assert.assertEquals(fromFileWithTestedText,fromWrittenFile);
    }

    @Test
    public void writeFile_emptyFile_ok() {
        String toFileName = "src/test/resources/OutputTestData.csv";
        String emptyFile = "src/test/resources/EmptyFile.csv";
        String textToWrite = "";
        writerService.writeFile(toFileName, textToWrite);
        List<String> fromWrittenFile;
        try {
            fromWrittenFile = Files.readAllLines(Path.of(toFileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + toFileName, e);
        }
        List<String> fromEmptyFile;
        try {
            fromEmptyFile = Files.readAllLines(Path.of(emptyFile));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from list" + toFileName, e);
        }
        Assert.assertEquals(fromEmptyFile,fromWrittenFile);
    }
}
