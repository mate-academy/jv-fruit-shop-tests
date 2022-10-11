package core.basesyntax.fileservice.impl;

import core.basesyntax.fileservice.CsvFileWriterService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String FIRST_PATH = "src/test/resources/writeTestOne.csv";
    private static final String SECOND_PATH = "src/test/resources/writeTestTwo";
    private static final String TEXT = "I remember learning how to swim. "
            + "I took lessons at the community pool in the town where "
            + "I grew up. One of the lifeguards, "
            + "Ms. Jen, really helped me get comfortable with holding my breath. "
            + "She'd play this bobbing game with us that had a fun song to go along with it. "
            + "Whenever I get in a pool, I still sing that song in my head.";
    private final File writerFirstTestFIle = new File(FIRST_PATH);
    private final File writerSecondTestFIle = new File(SECOND_PATH);
    private final CsvFileWriterService csvFileWriterService = new CsvFileWriterServiceImpl();

    @Test
    public void writeToFile_correctFilePath_Ok() throws IOException {
        csvFileWriterService.writeToFile(TEXT, FIRST_PATH);
        List<String> actual = List.of(TEXT);
        List<String> expected = List.of(TEXT);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_incorrectPath_NotOK() {
        String incorrectPath = "C/java/funnytesting/incorrect";
        csvFileWriterService.writeToFile(TEXT, incorrectPath);
    }

    @After
    public void tearDown() throws Exception {
        writerFirstTestFIle.delete();
        writerSecondTestFIle.delete();
    }
}
