package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.WriterServiceImpl;
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
