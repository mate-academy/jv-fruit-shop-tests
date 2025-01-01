package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import core.basesyntax.services.impl.FileDataReaderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataReaderImplTest {
    private File fileReader;
    private FileDataReaderImpl dataReader;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new File("text.txt");
        fileReader.createNewFile();
        dataReader = new FileDataReaderImpl(new FileReader(fileReader));
    }

    @AfterEach
    void tearDown() {
        if (fileReader.exists()) {
            fileReader.delete();
        }
    }

    @Test
    void shouldReturnCorrectListWhenFileIsNotEmpty() {
        try (FileWriter fileWriter = new FileWriter(fileReader)) {
            fileWriter.write("operation,fruitType,amount\n");
            fileWriter.write("b,banana,100\n");
            fileWriter.write("s,banana,300\n");
            fileWriter.write("p,banana,100\n");
            fileWriter.write("b,apple,200");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> list = dataReader.readData(Path.of(fileReader.getAbsolutePath()));
        assertNotNull(list);
        assertEquals(4, list.size());
        assertEquals("b,banana,100",list.get(0));
        assertEquals("s,banana,300", list.get(1));
        assertEquals("p,banana,100", list.get(2));
        assertEquals("b,apple,200", list.get(3));
    }

    @Test
    void shouldReturnNotNullListWhenFileIsEmpty() {
        List<String> list = dataReader.readData(Path.of(fileReader.getAbsolutePath()));
        assertNotNull(list);
        assertTrue(list.isEmpty(),"List should be empty for an empty file");
    }
}
