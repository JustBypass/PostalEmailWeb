package com.mail.global.methods;

import java.io.*;
import java.util.Base64;
import java.util.Optional;

public class WithStreamMethods {
    public String convertToString(final Serializable object) {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public <T> T convertFrom(final String objectAsString) {
        final byte[] data = Base64.getDecoder().decode(objectAsString);
        try (final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (T) ois.readObject();
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
