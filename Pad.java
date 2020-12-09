public class Pad {
	static String key;
	static private int count;

	public static void setKey(String k) {
		key = k;
		count = 0;
	}

	public static String encrypt(String msg) {
		byte[] m = msg.getBytes();
		byte[] k = key.getBytes();

		for (int i = 0; i < msg.length(); i++) {
			m[i] ^= k[count];	
			count++;

			if (count > key.length()) {
				break;
			}
		}

		return new String(m);
	}

	public static String decrypt(String msg) {
		return encrypt(msg);
	}
}

