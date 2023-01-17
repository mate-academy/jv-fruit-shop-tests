package core.basesyntax.servicesimpl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.services.FileCsvWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileCsvWriterImplTest {
    private static FileCsvWriter writer;
    private static final File WRONG_PATH = new File("src/wrongpath/resources/wrongFile.csv");
    private String message;

    @BeforeClass
    public static void setUpBeforeClass() {
        writer = new FileCsvWriterImpl();
    }

    @Before
    public void setUp() throws IOException {
        File fileToWriteIn = new File("src/test/resources/testFileToWRiteIn.csv");
        fileToWriteIn.createNewFile();
        message = "balance, return supply";
    }

    @Test
    public void writeInFile_ok() throws IOException {
        File fileToWriteIn = new File("src/test/resources/testFileToWRiteIn.csv");
        writer.writeInFile(message, fileToWriteIn.getPath());
        assertTrue(Files.readAllLines(fileToWriteIn.toPath())
                .stream().allMatch(x -> x.contains(message)));
    }

    @Test(expected = RuntimeException.class)
    public void writeInFile_wrongPath_notOk() {
        writer.writeInFile(message,WRONG_PATH.getPath());
    }

    @After
    public void tearDown() {
        File fileToWriteIn = new File("src/test/resources/testFileToWRiteIn.csv");
        fileToWriteIn.delete();
    }
}
