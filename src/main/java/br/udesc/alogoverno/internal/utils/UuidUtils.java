package br.udesc.alogoverno.internal.utils;

import java.util.UUID;

public class UuidUtils {
    public static String generateUuid() {
        return java.util.UUID.randomUUID().toString();
    }

    public static boolean isValidUuid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
