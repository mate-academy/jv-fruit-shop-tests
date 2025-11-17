package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

class ReaderServiceImplTest {
    private final ReaderServiceImpl reader = new ReaderServiceImpl();

    @Test
    public void positiveCsvFile() throws IOException {
        Path path = Files.createTempFile("test", ".csv");
        List<String> list = List.of("type,fruit,quantity", "b,banana,20",
                "s,strawberry,100", "b,apple,50");
        Files.write(path, list);

        List<String> test = reader.read(path.toString());
        List<String> expected = List.of("b,banana,20", "s,strawberry,100", "b,apple,50");

        assertEquals(expected, test);
    }

    @Test
    public void negativeEmptyFile() throws IOException {
        Path path = Files.createTempFile("test", ".csv");
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reader.read(path.toString()));

        assertTrue(exception.getMessage().contains("CSV-file is empty"));
    }

    @Test
    public void incorrectPath() throws IOException {
        String path = "path/nopath/path";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reader.read(path));
        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof IOException);
    }
}
