package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static String pathFileWrite = "src/test/resources/textTestResult.csv";
    private WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_correctData_ok() {
        String expected = "fruit,quantity\nbanana,10";
        writerService.writeToFile(pathFileWrite, expected);
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathFileWrite));
            lines.forEach(s -> builder.append(s).append(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can`t find file by path " + pathFileWrite, e);
        }
        String actual = builder.toString().strip();
        assertEquals(expected, actual);
    }

    @Test
    void writeToFile_dataNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(pathFileWrite, null));
    }

    @Test
    void writeToFile_pathNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null,
                        "fruit,quantity\nbanana,10"));
    }

    @Test
    void writeToFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("",
                        "fruit,quantity\nbanana,10"));
    }
}
