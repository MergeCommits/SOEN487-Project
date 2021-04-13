package com.thing.soap;

public class HexConverter {
    public static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for(byte b: data)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
    public static byte[] fromHex(String hex) {
        final String digits = "0123456789ABCDEF";
        if(hex == null || (hex.length() % 2 != 0)) {
            return null; // invalid hex
        }
        hex = hex.toUpperCase();
        byte[] result = new byte[hex.length() / 2];
        for(int i = 0; i < result.length; i++) {
            int n =
                digits.indexOf(hex.charAt(2*i + 1)) < 0? -1:
                    digits.indexOf(hex.charAt(2*i)) * 16 + digits.indexOf(hex.charAt(2*i + 1));
            if(n < 0)
                return null; // invalid hex
            result[i] = (byte)n;
        }
        return result;
    }
}
