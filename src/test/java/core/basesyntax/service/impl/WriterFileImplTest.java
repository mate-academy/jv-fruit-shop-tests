package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.WriterFile;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WriterFileImplTest {
    private static final String OUT_FILE_NAME = "src/main/resources/report.csv";
    private FileReader fileReader = new FileReaderImpl();
    private WriterFile fileWriter = new WriterFileImpl();

    @Test
    void isFileWriter_Ok() {
        fileWriter.writeToFile("Test message", OUT_FILE_NAME);
        List<String> strings = fileReader.readFile(OUT_FILE_NAME);
        Assertions.assertEquals("[Test message]", strings.toString());
    }
}
