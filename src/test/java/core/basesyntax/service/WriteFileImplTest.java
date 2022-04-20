package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class WriteFileImplTest {
    private final WriteFile writeFile = new WriteFileImpl();
    private final List<String> report = new ArrayList<>();

    @Test
    public void writeFile_Ok() {
        report.add("apple,50");
        assertTrue(writeFile.writeFileReport(report,"src/main/resources/daylireport.csv"));
    }

    @Test(expected = NullPointerException.class)
    public void nullFileName_Ok() {
        report.add("apple,50");
        assertTrue(writeFile.writeFileReport(report,null));
    }

    @Test (expected = RuntimeException.class)
    public void emptyFileName_Ok() {
        report.add("apple,50");
        assertTrue(writeFile.writeFileReport(report,"src/main/resources/"));
    }
}
