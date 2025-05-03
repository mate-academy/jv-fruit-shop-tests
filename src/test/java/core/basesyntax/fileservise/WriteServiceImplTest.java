package core.basesyntax.fileservise;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src\\test\\resourcesTest\\report.csv";
    private static final String INCORRECT_FILE_PATH = "src\\mein\\report.cvs";
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "banana,172" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static final WriteServiceImpl writeService = new WriteServiceImpl();

    @Test
    public void write_reportToFile_Ok() {
        writeService.write(report, CORRECT_FILE_PATH);

        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(CORRECT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + CORRECT_FILE_PATH, e);
        }
        List<String> expectedList = List.of("fruit,quantity", "banana,172", "apple,90");
        assertEquals(expectedList, actualList);
    }

    @Test (expected = RuntimeException.class)
    public void write_to_incorrectPath_Ok() {
        writeService.write(report, INCORRECT_FILE_PATH);
    }
}
