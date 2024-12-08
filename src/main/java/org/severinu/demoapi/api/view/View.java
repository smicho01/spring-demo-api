package org.severinu.demoapi.api.view;

public class View {

    public static final String NORMAL = "NORMAL";
    public static final String EXTENDED = "EXTENDED";

    public interface Normal {}
    public interface Extended extends Normal {}
    // Hidden view used as a feature switch for disabled DHR fields in BaseMetadata class
    public interface Hidden {}

}