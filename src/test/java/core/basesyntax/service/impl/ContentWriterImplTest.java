package core.basesyntax.service.impl;

import core.basesyntax.service.ContentWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentWriterImplTest {
    private static final String line = "Hello" + System.lineSeparator();
    private static final String resourcesPath = "src" + File.separator + "test" + File.separator
                + "resources" + File.separator;
    private static final String resultFile = "outfile.txt";
    private static ContentWriter contentWriter;

    @BeforeClass
    public static void setUp() throws Exception {
        contentWriter = new ContentWriterImpl();
    }

    @Test
    public void test_write_ok() {
        contentWriter.write(line, resourcesPath + resultFile);
        try {
            List<String> list = Files.readAllLines(Path.of(resourcesPath + resultFile));
            String actual = list.get(list.size() - 1) + System.lineSeparator();
            Assert.assertEquals("Must be equals", line, actual);
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    @After
    public void cleanUp() {
        try {
            Files.newBufferedWriter(
                    Path.of(resourcesPath + resultFile),
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("File not found " + resourcesPath + resultFile);
        }
    }
}
