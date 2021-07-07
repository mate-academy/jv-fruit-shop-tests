package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    private FileWriter fileWriter;

    @Before
    public void init() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void fileWriter_correctWork_ok() throws IOException {
        String report = "Hello";
        String fileName = "src\\test\\resources\\FileWriterTest\\correctWork.cvs";
        fileWriter.writeToFile(report,fileName);
        List<String> actual = new ArrayList<>();
        actual.add(report);
        List<String> expected = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_NullAndNull_notOk() {
        String report = null;
        String fileName = null;
        fileWriter.writeToFile(report,fileName);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_ReportAndNull_notOk() {
        String report = "Hello";
        String fileName = null;
        fileWriter.writeToFile(report,fileName);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_NullAndFileName_notOk() {
        String report = null;
        String fileName = "src/test/resources/FileWriter/correctWork.cvs";
        fileWriter.writeToFile(report,fileName);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_incorrectFileName_notOk() {
        String report = "Hello";
        String fileName = "//\\";
        fileWriter.writeToFile(report, fileName);
    }
}
