package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dataservice.FileWriterService;
import core.basesyntax.dataservice.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void writeDataToFile_inputDataIsNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeDataToFile("hello.csv",null));
    }

    @Test
    public void writeDataToFile_inputDataIsEmpty_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeDataToFile("hello.csv",new ArrayList<>()));
    }

    @Test
    public void writeDataToFile_wrongFileType_notOk() {
        String pathToFile = "a*b/c";
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeDataToFile(pathToFile,
                        new ArrayList<>(List.of("fruit,quantity", "banana,152", "apple,90"))));
    }

    @Test
    public void writeDataToFile_correctFileType_ok() throws IOException {
        String pathToFile = "src/test/resources/fileToreport.csv";
        fileWriterService.writeDataToFile(pathToFile,
                new ArrayList<>(List.of("fruit,quantity", "banana,152", "apple,90")));
        List<String> actual = Files
                .readAllLines(Path.of(pathToFile));
        List<String> expected =
                new ArrayList<>(List.of("fruit,quantity", "banana,152", "apple,90"));
        assertEquals(expected, actual);
    }
}
