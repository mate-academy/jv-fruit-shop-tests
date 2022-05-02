package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static FileWriter fileWriter;
    private static String outputPath;
    private static String newFilePath;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        outputPath = "src/test/testResources/output.csv";
        newFilePath = "src/test/testResources/newFile.csv";
    }

    @Test
    public void write_correctData_Ok() {
        Assert.assertTrue(fileWriter.write(outputPath, "test"));
    }

    @Test
    public void write_createFile_Ok() {
        Assert.assertTrue(fileWriter.write(newFilePath, "create new file"));
    }

    @Test(expected = NullPointerException.class)
    public void write_nullReport_NotOk() {
        fileWriter.write(outputPath, null);
    }

    @Test(expected = NullPointerException.class)
    public void write_nullFilePath_NotOk() {
        fileWriter.write(null, "test");
    }

    @Test(expected = RuntimeException.class)
    public void write_emptyFilePath_NotOk() {
        fileWriter.write("", "test");
    }

    @AfterClass
    public static void clear() {
        try {
            Files.delete(Path.of(newFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file newFile.csv");
        }
    }
}
