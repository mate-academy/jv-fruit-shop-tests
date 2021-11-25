package core.basesyntax.io.ioimpl;

import core.basesyntax.io.MyReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class MyFileReaderTest {
    private static final String TEST_FILE_PATH = "src\\test\\resources\\test.txt";
    private final MyReader reader = new MyFileReader();

    @Test
    public void read_defaultCase_ok() {
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
        writeToFile(TEST_FILE_PATH, expected);
        List<String> actual = reader.read(TEST_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void read_emptyFile_ok() {
        List<String> expected = Collections.emptyList();
        writeToFile(TEST_FILE_PATH, expected);
        List<String> actual = reader.read(TEST_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidPath_notOk() {
        reader.read("inA/Galaxy/Far,Far/Away/ThereWasAPink.unicorn");
    }

    private void writeToFile(String path, List<String> content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String string : content) {
                writer.write(string + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + path, e);
        }
    }
}
