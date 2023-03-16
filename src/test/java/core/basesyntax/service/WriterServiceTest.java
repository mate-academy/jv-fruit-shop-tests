package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class WriterServiceTest {
    @Test
    public void writer_write_ok() {
        String toWrite = "fruit, quantity" + System.lineSeparator()
                + "banana, 5" + System.lineSeparator()
                + "apple, 25";
        String to = "src/main/resources/out.csv";
        new WriterServiceImpl().write(toWrite, to);
        try {
            Assert.assertEquals(toWrite, Files.readString(Path.of(to)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the output file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void writer_write_nonExistentFile_exception() {
        new WriterServiceImpl().write("Writing something in the file",
                "");
    }
}
