public class Pad {

	public static String encrypt(String msg, String key) {
		char[] m = msg.toCharArray();

		for (int i = 0; i < msg.length(); i++) {
			m[i] ^= key.charAt(i);	
		}

		return m.toString();
	}

	public static String decrypt(String msg, String key) {
		return encrypt(msg, key);
	}
}

