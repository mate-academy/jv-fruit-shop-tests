package core.basesyntax;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriteDataImplTest {
    private WriteDataImpl writeData;
    private File file;

    @BeforeEach
    void setUp() {
        file = new File("test_fruit_data.csv");
        writeData = new WriteDataImpl("test_fruit_data.csv");
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
            assertNull(br.readLine(), "The file should not contain any other lines.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}