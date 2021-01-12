package ehu.isad.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sarea {

    private static final Sarea nSarea = new Sarea();

    public static Sarea getSarea() {
        return nSarea;
    }

    private Sarea() {}

    public String urlMd5Lortu(String pUrl) {
        String digest = "";
        try {
            URL url = new URL(pUrl + "/README");
            InputStream is = url.openStream();
            MessageDigest md = MessageDigest.getInstance("MD5");
            digest = getDigest(is, md, 2048);
        } catch (UnknownHostException | MalformedURLException e) {
            return "";
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest;
    }

    private static String getDigest(InputStream is, MessageDigest md, int byteArraySize)
            throws NoSuchAlgorithmException, IOException {

        md.reset();
        byte[] bytes = new byte[byteArraySize];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            md.update(bytes, 0, numBytes);
        }
        byte[] digest = md.digest();
        String result = new String(Hex.encodeHex(digest));
        return result;
    }
}