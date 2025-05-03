package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static String pathToWrite;
    private static String text;

    @BeforeClass
    public static void initializeFields() {
        fileWriter = new FileWriterImpl();
        pathToWrite = "src/test/resources/activities.csv";
        text = "Test! \\ //" + System.lineSeparator() + "The end";
    }

    @AfterClass
    public static void clearFile() {
        try {
            Files.deleteIfExists(Paths.get(pathToWrite));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file after test!", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_badPath_throwException() {
        String path = " ";
        fileWriter.writeToFile(text, path);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_textIsNull_throwException() {
        fileWriter.writeToFile(null, pathToWrite);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_pathIsNull_throwException() {
        fileWriter.writeToFile(text, null);
    }

    @Test
    public void writeToFile_pathIsNotExist_createNewFileWithData() {
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
    public void writeToFile_pathIsExist_rewriteFile() {
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
