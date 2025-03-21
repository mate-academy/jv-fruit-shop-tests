package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.infrastructure.db.FileReader;
import core.basesyntax.infrastructure.db.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainTest {
    private static final String OPERATION_LIST_FILE_PATH
            = "src/main/resources/operationslist.csv";
    private static final String DB_FILE_PATH
            = "src/main/resources/database.csv";

    @Test
    void applicationWorkResultOk() {
        List<String> expected = new ArrayList<>();
        expected.add("banana,152");
        expected.add("apple,90");

        Main.main(new String[0]);
        FileReader reader = new FileReaderImpl();
        List<String> actual = reader.read(DB_FILE_PATH);

        assertEquals(actual, expected);
    }
}
