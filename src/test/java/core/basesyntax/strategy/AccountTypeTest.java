package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AccountTypeTest {
    @Test
    void fromCode_validCode_ok() {
        assertEquals(AccountType.BALANCE, AccountType.fromCode("b"));
        assertEquals(AccountType.SUPPLY, AccountType.fromCode("s"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> AccountType.fromCode("x"));
    }
}
