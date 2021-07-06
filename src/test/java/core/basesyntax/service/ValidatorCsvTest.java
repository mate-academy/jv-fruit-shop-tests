package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorCsvTest {
    private static final String WORDS_SEPARATOR = ",";
    private static Validator validator;

    @BeforeClass
    public static void setValidator() {
        validator = new ValidatorCsv();
    }

    @Test
    public void validator_isValid_randomCharacters_ok() {
        String[] operations = {"r", "b", "s", "p"};
        Random random = new Random();
        StringBuilder line;
        for (int i = 0; i < 100; i++) {
            line = new StringBuilder(operations[random.nextInt(4)] + WORDS_SEPARATOR);
            for (int j = 0; j < random.nextInt(10) + 4; j++) {
                line.append((char) (97 + random.nextInt((int) 'z' - (int) 'a')));
            }
            line.append(WORDS_SEPARATOR);
            line.append(random.nextInt(1000));
            assertTrue(validator.test(line.toString()));
        }
    }

    @Test
    public void validator_isValid_wrongCharacters_notOk() {
        String line = "r,banana,-456";
        assertFalse(validator.test(line));

        line = "r , banana , -456";
        assertFalse(validator.test(line));

        line = "a,banana78,345";
        assertFalse(validator.test(line));
    }
}
