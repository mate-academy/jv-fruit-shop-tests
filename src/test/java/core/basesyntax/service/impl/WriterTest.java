package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static Writer writer;

    @BeforeClass
    public static void init() {
        writer = new WriterImpl();
    }

    @Test
    public void write_Ok() {
        Assert.assertTrue(writer.write("src/main/resources/report.csv", "Test"));
    }

    @Test(expected = RuntimeException.class)
    public void writeInvalidPath_NotOk() {
        writer.write("", "Test");
    }

    @Test
    public void writeCorrectData_Ok() {
        String filePathForCustomWriter = "src/main/resources/customWriterTest.csv";
        String filePathForStandardWriter = "src/main/resources/standardWriterTest.csv";
        String testString = "Tested string";
        File report = new File(filePathForStandardWriter);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(report))) {
            bufferedWriter.write(testString);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file "
                    + filePathForStandardWriter, e);
        }
        writer.write(filePathForCustomWriter, testString);
        File wroteWithCustomWriter = new File(filePathForCustomWriter);
        File wroteWithStandardWriter = new File(filePathForStandardWriter);
        try {
            Assert.assertEquals(Files.readAllLines(wroteWithStandardWriter.toPath()),
                    Files.readAllLines(wroteWithCustomWriter.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file ", e);
        }
    }

    @Test
    public void writeMultipleLine_Ok() {
        String testedString = "FirstLine" + System.lineSeparator()
                + "SecondLine" + System.lineSeparator()
                + "ThirdLine";
        String pathToFile = "src/main/resources/customWriterTest.csv";
        writer.write(pathToFile, testedString);
        File testedFile = new File(pathToFile);
        try {
            Assert.assertEquals(3, Files.readAllLines(testedFile.toPath()).size());
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file ", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeEmptyString_NotOk() {
        String pathToFile = "src/main/resources/customWriterTest.csv";
        writer.write(pathToFile, "");
    }
}
