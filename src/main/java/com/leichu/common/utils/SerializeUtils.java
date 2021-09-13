package com.leichu.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {

	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
		     ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(object);
			byte[] bytes = bos.toByteArray();
			return bytes;
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Object unSerialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		     ObjectInputStream ois = new ObjectInputStream(bis);) {
			return ois.readObject();
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
