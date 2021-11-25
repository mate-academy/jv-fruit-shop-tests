package core.basesyntax.io.ioimpl;

import core.basesyntax.io.MyWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

public class MyFileWriterTest {
    private static final String TEST_FILE_PATH = "src/test/resources/test.txt";
    private final MyWriter writer = new MyFileWriter();

    @Test
    public void write_defaultCase_ok() {
        List<String> expected = List.of(
                "Lorem ipsum dolor sit amet,",
                "consectetur adipiscing elit,",
                "sed do eiusmod tempor incididunt",
                "ut labore et dolore magna aliqua.",
                "Ut enim ad minim veniam, quis nostrud",
                "exercitation ullamco laboris nisi ut aliquip",
                "ex ea commodo consequat. Duis aute irure dolor",
                "in reprehenderit in voluptate velit esse cillum",
                "dolore eu fugiat nulla pariatur. Excepteur sint",
                "occaecat cupidatat non proident, sunt in culpa qui",
                "officia deserunt mollit anim id est laborum.");
        writer.write(TEST_FILE_PATH, expected);
        List<String> actual = read(TEST_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void write_noContent_ok() {
        List<String> expected = Collections.emptyList();
        writer.write(TEST_FILE_PATH, expected);
        List<String> actual = read(TEST_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    private List<String> read(String path) {
        List<String> result;
        try {
            Stream<String> lines = Files.lines(Paths.get(path));
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + path, e);
        }
        return result;
    }
}
