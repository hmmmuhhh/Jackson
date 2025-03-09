package model;

public record Configuration(String configVersion, int buildCount, String author, Mode appMode, Encoding encoding) {
}