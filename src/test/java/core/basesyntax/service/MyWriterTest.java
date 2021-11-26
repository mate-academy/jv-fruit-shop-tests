package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.writer.MyWriter;
import core.basesyntax.service.writer.MyWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyWriterTest {
    private static MyWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new MyWriterImpl();
    }

    @Test
    public void writeToFile_correctWork_ok() throws IOException {
        String report = "report test#1";
        String fileName = "src/test/resourcesTest/outputTestFile.cvs";
        fileWriter.writeToFile(report,fileName);
        List<String> actual = new ArrayList<>();
        actual.add(report);
        List<String> expected = Files.readAllLines(Paths.get(fileName));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullAndNull_notOk() {
        String report = null;
        String fileName = null;
        fileWriter.writeToFile(report,fileName);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_reportAndNull_notOk() {
        String report = "report test#3";
        String fileName = null;
        fileWriter.writeToFile(report,fileName);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullAndFileName_notOk() {
        String report = null;
        String fileName = "src/test/resourcesTest/outputTestFile.cvs";
        fileWriter.writeToFile(report,fileName);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_incorrectFileName_notOk() {
        String report = "report test#5";
        String fileName = "crs/tset/errscyrtes/fileOutputter.dvd";
        fileWriter.writeToFile(report, fileName);
    }
}
