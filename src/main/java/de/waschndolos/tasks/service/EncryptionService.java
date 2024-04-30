package de.waschndolos.tasks.service;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
@Service
public class EncryptionService {

    private static final String SALT_STR = "adlsfokjaogtiltnq034p983hn89tgowöäonspy80ü9z4üthg";

    public String getEncryptedIdentifier() {
        String username = System.getProperty("user.name");
        return hashUsername(username, SALT_STR.getBytes(StandardCharsets.UTF_8));
    }

    private static String hashUsername(String username, byte[] salt) {
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        generator.init(username.getBytes(StandardCharsets.UTF_8), salt, 1000);
        byte[] hash = ((KeyParameter) generator.generateDerivedParameters(256)).getKey();
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
