package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.WriterFile;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WriterFileImplTest {
    private static final String OUT_FILE_NAME = "src/main/resources/report.csv";

    @Test
    void isFinalResult_Ok() {
        FileReader fileReader = new FileReaderImpl();
        WriterFile fileWriter = new WriterFileImpl();
        fileWriter.writeToFile("Test message", OUT_FILE_NAME);
        List<String> strings = fileReader.readFile(OUT_FILE_NAME);
        Assertions.assertEquals("[Test message]", strings.toString());
        Assertions.assertThrows(RuntimeException.class, () -> fileReader
                .readFile("wrong file"));
    }
}
