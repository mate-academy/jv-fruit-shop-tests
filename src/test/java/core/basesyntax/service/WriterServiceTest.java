package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writerService;

    @BeforeClass
    public static void initialize() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_normalInput_ok() {
        String expected =
                "fruit, quantity" + System.lineSeparator()
                + "banana, 5" + System.lineSeparator()
                + "apple, 25";
        String to = "src/test/resources/out.csv";
        writerService.write(expected, to);
        try {
            assertEquals(expected, Files.readString(Path.of(to)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the output file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_nonExistentFile_exception() {
        writerService.write("Writing something in the file",
                "");
    }
}
