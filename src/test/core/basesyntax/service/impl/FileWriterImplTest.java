package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static String pathToWrite;
    private static String text;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initializeFields() {
        fileWriter = new FileWriterImpl();
        pathToWrite = "src/test/resources/activities.csv";
        text = "Test!\\ //" + System.lineSeparator() + "The end";
    }

    @AfterClass
    public static void clearFile() {
        try {
            Files.deleteIfExists(Paths.get(pathToWrite));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file after test!", e);
        }
    }

    @Test
    public void writeToFile_BadPath_throwException() {
        String path = " ";
        expectedException.expect(java.nio.file.InvalidPathException.class);
        fileWriter.writeToFile(text, path);
    }

    @Test
    public void writeToFile_textIsNull_throwException() {
        expectedException.expect(RuntimeException.class);
        fileWriter.writeToFile(null, pathToWrite);
    }

    @Test
    public void writeToFile_PathIsNull_throwException() {
        expectedException.expect(RuntimeException.class);
        fileWriter.writeToFile(text,null);
    }

    @Test
    public void writeToFile_PathIsNotExist_createNewFileWithData() {
        fileWriter.writeToFile(text, pathToWrite);
        String expected;
        try {
            expected = Files.readString(Path.of(pathToWrite));
        } catch (IOException e) {
            throw new RuntimeException("Util reader was crashed", e);
        }
        String actual = text;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_PathIsExist_rewriteFile() {
        try {
            Files.writeString(Path.of(pathToWrite)," Text is exist ");
        } catch (IOException e) {
            throw new RuntimeException("Util writer was crashed", e);
        }
        fileWriter.writeToFile(text, pathToWrite);
        String expected;
        try {
            expected = Files.readString(Path.of(pathToWrite));
        } catch (IOException e) {
            throw new RuntimeException("Util reader was crashed", e);
        }
        String actual = text;
        Assert.assertEquals(expected, actual);
    }
}
