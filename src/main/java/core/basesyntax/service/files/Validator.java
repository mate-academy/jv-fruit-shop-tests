package core.basesyntax.service.files;

import java.util.List;

public interface Validator {
    boolean isValid(List<String> infoFromInputFile);
}
