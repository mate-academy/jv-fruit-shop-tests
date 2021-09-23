package core.basesyntax.services.writetofile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class WriteToFileImplTest {
    @Test
    public void writeToFile_EmptyReport_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        String filePath = "src/main/resources/report.csv";
        WriteToFile writeToFile = new WriteToFileImpl();
        writeToFile.writeToFile(expected);
        List<String> actual = null;
        try {
            actual = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            fail("File not exist!" + e);
        }
        assertEquals(expected, actual);
    }
}
