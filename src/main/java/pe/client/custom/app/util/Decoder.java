package pe.client.custom.app.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Decoder {

    public static String decodeFromBase64(String value) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedValue = decoder.decode(value.getBytes(StandardCharsets.UTF_8));
        return new String(decodedValue, StandardCharsets.UTF_8);
    }
}
