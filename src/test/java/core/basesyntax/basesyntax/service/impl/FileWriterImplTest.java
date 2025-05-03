package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriterService fileWriter;
    private static final String FILE_TO_WRITE
            = "src/test/java/core/basesyntax/resources/fileToWrite.csv";
    private static final String FILE_FROM_READ
            = "src/test/java/core/basesyntax/resources/fileFromRead";
    private static final String WRONG_PATH_FILE = "src/test";
    private static String data = "type,fruit,quantity";

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_isOk() throws IOException {
        List<String> expected = Files.readAllLines(Path.of(FILE_FROM_READ));
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_TO_WRITE));
        for (String line : expected) {
            writer.write(line + System.lineSeparator());
        }
        writer.close();
        List<String> actual = Files.readAllLines(Path.of(FILE_TO_WRITE));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_wrongPath_NotOk() {
        fileWriter.writeToFile(data, WRONG_PATH_FILE);
    }
}
