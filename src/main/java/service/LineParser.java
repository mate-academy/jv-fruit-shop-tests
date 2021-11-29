package service;

import core.basesyntax.model.ParsedLine;
import java.util.List;

public interface LineParser {
    List<ParsedLine> lineParcer(List<String> input);
}

