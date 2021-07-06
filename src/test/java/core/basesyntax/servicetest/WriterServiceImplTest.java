package core.basesyntax.servicetest;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_regularData_Ok() {
        String expected = "\"Relax, take it easy\" says\nthe famous song,\nbut how can I relax,"
                + "\nif my homework isn't done yet?";
        writerService.writeToFile("src/test/resources/input-file", expected);
        String actual;
        try {
            actual = String.join("\n", Files
                    .readAllLines(new File("src/test/resources/input-file").toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: src/test/resources/input-file", e);
        }
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s", expected, actual),
                expected, actual);
    }

    @Test(expected = InvalidPathException.class)
    public void writeToFile_wrongFileName_NotOk() {
        writerService.writeToFile("???", "I bet this message will never be written)");
    }
}
