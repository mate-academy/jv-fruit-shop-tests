package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String CORRECT_PATH = "src/main/resources/output.csv";
    private static final String FULL_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private static final String EMPTY_REPORT = "";
    private static final List<String> CORRECT_RESULT_FOR_FULL_REPORT = List.of("fruit,quantity",
            "banana,152", "apple,90");
    private static final List<String> CORRECT_RESULT_FOR_EMPTY_REPORT = Collections.emptyList();
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void writeReportToFile_pathToFileIsNull_notOk() {
        String pathToFile = null;
        String report = FULL_REPORT;
        writerService.writeReportToFile(pathToFile, report);
        fail("You must throw Runtime Exception, if the path to file is null");
    }

    @Test (expected = RuntimeException.class)
    public void writeReportToFile_reportIsNull_notOk() {
        String pathToFile = CORRECT_PATH;
        String report = null;
        writerService.writeReportToFile(pathToFile, report);
        fail("You must throw Runtime Exception, if the passed report is null");
    }

    @Test
    public void writeReportToFile_defaultCase_ok() {
        String pathToFile = CORRECT_PATH;
        String report = FULL_REPORT;
        writerService.writeReportToFile(pathToFile, report);
        File createdFile = new File(CORRECT_PATH);
        try {
            List<String> expected = CORRECT_RESULT_FOR_FULL_REPORT;
            List<String> actual = Files.readAllLines(createdFile.toPath());
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from the file " + createdFile.toPath(), e);
        }

    }

    @Test
    public void writeReportToFile_defaultCaseWithEmptyReport_ok() {
        String pathToFile = CORRECT_PATH;
        String report = EMPTY_REPORT;
        writerService.writeReportToFile(pathToFile, report);
        File createdFile = new File(CORRECT_PATH);
        try {
            List<String> expected = CORRECT_RESULT_FOR_EMPTY_REPORT;
            List<String> actual = Files.readAllLines(createdFile.toPath());
            assertEquals(expected, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from the file " + CORRECT_PATH, e);
        }
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(CORRECT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete this file " + CORRECT_PATH);
        }
    }
}
