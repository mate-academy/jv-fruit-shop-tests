package core.basesyntax.fileservice.impl;

import core.basesyntax.fileservice.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String PATH = "src/test/resources/writeTestOne.csv";
    private static final String TEXT = "I remember learning how to swim. "
            + "I took lessons at the community pool in the town where "
            + "I grew up. One of the lifeguards, "
            + "Ms. Jen, really helped me get comfortable with holding my breath. "
            + "She'd play this bobbing game with us that had a fun song to go along with it. "
            + "Whenever I get in a pool, I still sing that song in my head.";

    private static CsvFileWriterService csvFileWriterService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        csvFileWriterService = new CsvFileWriterServiceImpl();
    }

    @Test
    public void writeToFile_correctFilePath_Ok() throws IOException {
        csvFileWriterService.writeToFile(TEXT, PATH);
        String actual = Files.readString(Path.of(PATH));
        String expected = TEXT;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_incorrectPath_NotOK() {
        String incorrectPath = "C/java/funnytesting/incorrect";
        csvFileWriterService.writeToFile(TEXT, incorrectPath);
    }
}
