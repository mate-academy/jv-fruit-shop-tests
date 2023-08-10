package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileWriterServiceTest {

    private static final String PATH_TO_WRITE = "src/test/resources/testWriter.csv";
    private final FileWriterService fileWriter = new FileWriterServiceImpl();

    @Test
    void write_checkCorrectWriting_ok() {
        String textForTest = "fruit,quantity" + System.lineSeparator() + "r,banana,20";
        fileWriter.write(textForTest, PATH_TO_WRITE);
        List<String> exceptedData = List.of("r,banana,20");
        List<String> actualData = new FileReaderServiceImpl().read(PATH_TO_WRITE);
        assertEquals(exceptedData, actualData);
    }

    @Test
    void write_pathIsNull_notOk() {
        String report = "test";
        String path = null;
        assertThrows(RuntimeException.class, () -> fileWriter.write(report, path));
    }

    @Test
    void write_dataForWriteIsNull_notOk() {
        String dataForWrite = null;
        assertThrows(RuntimeException.class, () -> fileWriter.write(dataForWrite, PATH_TO_WRITE));
    }

    @Test
    public void write_fileException_notOK() {
        String report = "Sample report content";
        String fileName = "sample.txt";
        String filePath = "nonexistentfolder" + File.separator + fileName;
        assertThrows(RuntimeException.class,() -> fileWriter.write(report, filePath));
    }
}
