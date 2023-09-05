package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static final List<String> DATA = new ArrayList<>();
    private final WriterService writerService = new WriterServiceImpl();

    @BeforeAll
    public static void init() {
        DATA.add("lemon,30");
        DATA.add("apple,441");
        DATA.add("banana,2");
        DATA.add("pineapple,231");
        DATA.add("apple,37");
        DATA.add("lemon,73");
        DATA.add("pineapple,74");
    }

    @Test
    void writeToFile_ExistFilePath_Ok() {
        String path = "src/test/resources/fruitReportTest.csv";
        writerService.writeToFile(path, DATA);
        assertTrue(new File(path).exists());
    }

    @Test
    void writeToFile_notExistFilePath_notOk() {
        String path = "sys/null/obj.csv";
        assertThrows(RuntimeException.class, () -> writerService.writeToFile(path, DATA));
    }
}
