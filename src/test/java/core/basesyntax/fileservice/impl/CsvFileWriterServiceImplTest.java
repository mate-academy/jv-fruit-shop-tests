package core.basesyntax.fileservice.impl;

import static org.junit.Assert.fail;

import core.basesyntax.fileservice.CsvFileWriterService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String FIRST_PATH = "src/test/resources/writeTestOne.csv";
    private static final String SECOND_PATH = "src/test/resources/writeTestTwo";
    private static final String INCORRECT_PATH = "C/java/funnytesting/incorrect";
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
    public void correctFIlePath_Ok() throws IOException {
        csvFileWriterService.writeToFile(TEXT, FIRST_PATH);
        csvFileWriterService.writeToFile(TEXT, SECOND_PATH);
        List<String> infoFromFirstPath = Files.readAllLines(Path.of(FIRST_PATH));
        List<String> infoFromSecondPath = Files.readAllLines(Path.of(SECOND_PATH));
        Assert.assertEquals(infoFromSecondPath, infoFromFirstPath);
    }

    @Test
    public void incorrectPath_NotOK() {
        try {
            csvFileWriterService.writeToFile(TEXT, INCORRECT_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("IOException should be thrown if path is incorrect");
    }

    @After
    public void tearDown() throws Exception {
        writerFirstTestFIle.delete();
        writerSecondTestFIle.delete();
    }
}
