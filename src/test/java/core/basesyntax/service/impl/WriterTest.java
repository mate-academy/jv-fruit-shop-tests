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
    private static final String FILE_PATH_TO_CUSTOM_WRITER
            = "src/test/resources/customWriterTest.csv";
    private static final String FILE_PATH_TO_STANDARD_WRITER
            = "src/test/resources/standardWriterTest.csv";
    private static final String INVALID_PATH = "";
    private static Writer writer;

    @BeforeClass
    public static void init() {
        writer = new WriterImpl();
    }

    @Test
    public void write_Ok() {
        Assert.assertTrue(writer.write(FILE_PATH_TO_CUSTOM_WRITER, "Test"));
    }

    @Test(expected = RuntimeException.class)
    public void writeInvalidPath_NotOk() {
        writer.write(INVALID_PATH, "Test");
    }

    @Test
    public void writeCorrectData_Ok() {
        String testString = "Tested string";
        File report = new File(FILE_PATH_TO_STANDARD_WRITER);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(report))) {
            bufferedWriter.write(testString);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file "
                    + FILE_PATH_TO_STANDARD_WRITER, e);
        }
        writer.write(FILE_PATH_TO_CUSTOM_WRITER, testString);
        File wroteWithCustomWriter = new File(FILE_PATH_TO_CUSTOM_WRITER);
        File wroteWithStandardWriter = new File(FILE_PATH_TO_STANDARD_WRITER);
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
        writer.write(FILE_PATH_TO_CUSTOM_WRITER, testedString);
        File testedFile = new File(FILE_PATH_TO_CUSTOM_WRITER);
        try {
            Assert.assertEquals(3, Files.readAllLines(testedFile.toPath()).size());
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file ", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeEmptyString_NotOk() {
        writer.write(FILE_PATH_TO_CUSTOM_WRITER, "");
    }
}
