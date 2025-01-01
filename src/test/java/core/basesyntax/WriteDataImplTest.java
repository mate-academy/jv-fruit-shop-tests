package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import core.basesyntax.services.impl.FileDataWriterImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriteDataImplTest {
    private FileDataWriterImpl writeData;
    private File file;

    @BeforeEach
    void setUp() {
        file = new File("test_fruit_data.csv");
        writeData = new FileDataWriterImpl("test_fruit_data.csv");
    }

    @AfterEach
    void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void shouldReturnCorrectDataWhenInputDataIsNotNull() {
        List<String> list = Arrays.asList("banana,152","apple,90");
        file = writeData.writeData(list);
        try (BufferedReader br = new BufferedReader(new FileReader("test_fruit_data.csv"))) {
            assertEquals("fruit,quantity", br.readLine());
            assertEquals(list.get(0),br.readLine());
            assertEquals(list.get(1), br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldReturnHeaderWhenIsNoInputData() {
        List<String> empty = Arrays.asList();
        file = writeData.writeData(empty);
        try (BufferedReader br = new BufferedReader(new FileReader("test_fruit_data.csv"))) {
            assertEquals("fruit,quantity", br.readLine());
            assertNull(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
