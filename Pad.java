public class Pad {
	static private String key;
	static private int count;

	public static void setKey(String k) {
		key = k;
		count = 0;
	}

	public static boolean canEncrypt(String s) {
		return (s.length() < (key.length() - count));
	}

	public static void undo(int i) {
		count -= i;
	}

	public static String encrypt(String msg) {
		byte[] m = msg.getBytes();
		byte[] k = key.getBytes();

		for (int i = 0; i < msg.length(); i++) {
			m[i] ^= k[count];	
			count++;
		}

		return new String(m);
	}

	public static String decrypt(String msg) {
		return encrypt(msg);
	}
}

