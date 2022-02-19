package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dataservice.FileReaderService;
import core.basesyntax.dataservice.FileReaderServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final FileReaderService fileReaderService = new FileReaderServiceImpl();
    private static final String pathToReadFile = "src/test/resources/dayFile.csv";

    @Test
    public void getDataFromFile_emptyFile_notOk() {
        String pathToFile = "";
        assertThrows(RuntimeException.class,
                () -> fileReaderService.getDataFromFile(pathToFile));
    }

    @Test
    public void getDataFromFile_fileIsNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> fileReaderService.getDataFromFile(null));
    }

    @Test
    public void getDataFromFile_wrongFileType_notOk() {
        String pathToFile = "not.correct.bbp";
        assertThrows(RuntimeException.class,
                () -> fileReaderService.getDataFromFile(pathToFile));
    }

    @Test
    public void getDataFromFile_correctReturnData_Ok() throws IOException {
        List<String> actual = fileReaderService.getDataFromFile(pathToReadFile);
        List<String> expected = Files.readAllLines(Path.of(pathToReadFile));
        assertEquals(expected,actual);
    }

    @Test
    public void getDataFromFile_wrongReturnData_notOk() {
        List<String> actual = fileReaderService.getDataFromFile(pathToReadFile);
        List<String> expected = List.of("something","failed","0011");
        assertNotEquals(expected,actual);
    }
}
