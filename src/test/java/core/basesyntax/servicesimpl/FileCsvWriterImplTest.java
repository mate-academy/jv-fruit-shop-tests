package core.basesyntax.servicesimpl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.services.FileCsvWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileCsvWriterImplTest {

    private final FileCsvWriter writer = new FileCsvWriterImpl();
    private final File fileToWriteIn = new File("src/test/resources/testFileToWRiteIn.csv");
    private final File wrongPath = new File("src/wrongpath/resources/wrongFile.csv");
    private String message;

    @Before
    public void setUp() throws IOException {
        fileToWriteIn.createNewFile();
        message = "balance, return supply";
    }

    @After
    public void tearDown() {
        fileToWriteIn.delete();
    }

    @Test
    public void writeInFile_Ok() throws IOException {
        writer.writeInFile(message, fileToWriteIn.getPath());
        assertTrue(Files.readAllLines(fileToWriteIn.toPath())
                .stream().allMatch(x -> x.contains(message)));
    }

    @Test(expected = RuntimeException.class)
    public void writeInFIle_WrongPath() {
        writer.writeInFile(message,wrongPath.getPath());
    }
}
