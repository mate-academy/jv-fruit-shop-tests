package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServieImplTest {
    private static final String FILE_NOT_EXIST = "src/test/java/";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private WriterService writerService = new WriterServiceImpl();

    @Test
    public void writing_to_file_which_not_exist_Not_OKey() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("File " + FILE_NOT_EXIST + " could not be written to");
        writerService.writeToFile("src/test/java/", "yo");
    }

    @Test
    public void writing_to_file_line_Okey() throws Exception {
        writerService.writeToFile("src/test/java/resources/yo.txt", "yo");
        Assert.assertEquals("yo",
                readFile("src/test/java/resources/yo.txt").get(0));
    }

    private List<String> readFile(String file) throws Exception {
        return Files.readAllLines(Paths.get(file));
    }

    @Test
    public void writing_to_file_lines_Okey() throws Exception {
        List<String> expected = new ArrayList<>();
        expected.add("yo");
        expected.add("hello");
        expected.add("hi");
        String report = "yo" + System.lineSeparator() + "hello"
                + System.lineSeparator() + "hi";
        writerService.writeToFile("src/test/java/resources/yo.txt", report);
        List<String> actual = readFile("src/test/java/resources/yo.txt");
        Assert.assertEquals(expected, actual);
    }
}
