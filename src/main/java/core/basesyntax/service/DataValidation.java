package core.basesyntax.service;

import java.util.List;

public interface DataValidation {
    boolean checkListNotEmpty(List<String> records);

    boolean checkLine(String line);

    boolean subtractCheck(int currentQuantity, int subtract);
}
