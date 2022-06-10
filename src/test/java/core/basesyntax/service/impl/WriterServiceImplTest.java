package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final Path project = Path.of("").toAbsolutePath();
    private static final Path resources =
            Paths.get(project.toString(), "src", "test", "resources");
    private final Path fileNameNormal = resources.resolve("temporary-file.txt");
    private final Path fileNameFailed =
            Paths.get(resources.toString(), "bad").resolve("temporary-file.txt");

    @BeforeClass
    public static void beforeClass() {
        createResourceDirectory();
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_notEmptyFile_ok() {
        String expected = "Be great in act, as you have been in thought";
        writerService.write(fileNameNormal, expected);
        String actual = readFromFile(fileNameNormal);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void write_emptyFile_ok() {
        String expected = "";
        writerService.write(fileNameNormal, expected);
        String actual = readFromFile(fileNameNormal);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void write_notExistedFile_notOk() {
        writerService.write(fileNameFailed, "");
    }

    private static void createResourceDirectory() {
        try {
            if (!Files.exists(resources)) {
                Files.createDirectories(resources);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error create directory" + resources, e);
        }
    }

    private String readFromFile(Path fileName) {
        try {
            if (!Files.exists(resources)) {
                throw new RuntimeException("File doesn't exit. Error read from file " + fileName);
            }
            return Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error read from file " + fileName, e);
        }
    }

    private void deleteTestFile() {
        if (!Files.exists(fileNameNormal)) {
            return;
        }
        try {
            Files.delete(fileNameNormal);
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Error remove file '%s'", fileNameFailed), e);
        }
    }

    @After
    public void tearDown() {
        deleteTestFile();
    }
}
