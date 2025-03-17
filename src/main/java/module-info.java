module mate.academy.jvfruitshoptests {
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.commons;

    opens core.basesyntax to org.junit.platform.commons, org.junit.jupiter.api;
}