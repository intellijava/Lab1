module allentity {
    requires jakarta.persistence;
    exports allentity;
    opens allentity to org.eclipse.persistence.core, com.google.gson;
}