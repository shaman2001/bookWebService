package com.epam.rest.tests.helper;

public class HttpUtil {

    private static final String EX = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";


    public static String encodeURIComponent(String input) throws Exception {
        int l = input.length();
        StringBuilder o = new StringBuilder(l * 3);
        for(int i=0; i < l; i++) {
            String e = input.substring(i,i+1);
            if(!EX.contains(e)) {
                byte[] b = e.getBytes("utf-8");
                o.append(getHex(b));
                continue;
            }
            o.append(e);
        }
        return o.toString();
    }

    private static String getHex(byte buf[])     {
        StringBuilder o = new StringBuilder(buf.length * 3);
        for (byte aBuf : buf) {
            int n = (int) aBuf & 0xff;
            o.append("%");
            if (n < 0x10) o.append("0");
            o.append(Long.toString(n, 16).toUpperCase());
        }
        return o.toString();
    }
}