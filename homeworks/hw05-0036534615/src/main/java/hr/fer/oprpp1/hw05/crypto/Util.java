package hr.fer.oprpp1.hw05.crypto;

public class Util {

	private static char[] hexChars = "0123456789abcdef".toCharArray();
	/**
	 * Metoda koja pretvara hex broj zapisan u string formatu u byte niz.
	 * @param keyText
	 * @return  niz bajtova, koji predstavljaju taj hex.
	 * @throws IllegalArgumentException
	 */
	public static byte[] hextobyte(String keyText) {
		int length = keyText.length();
		
		if (length % 2  != 0) {
			throw new IllegalArgumentException();
		} else if (length == 0) {
			return new byte[0];
		}
		
		int counter = 0;
		byte[] array = new byte[length/2];
		
		for (int i=0;i < length;i+=2) {
			int first = hexToBinary(keyText.charAt(i));
			int second = hexToBinary(keyText.charAt(i+1));
			
			if (first == -1 || second == -1) throw new IllegalArgumentException();
			
			array[counter] = (byte)(first*16+second);
			counter++;		
		}
		
		return array;
	}

	public static String bytetohex(byte[] bytearray) {
		char[] result = new char[bytearray.length*2];
		
		for (int i=0;i< bytearray.length;i++) {
			int r = bytearray[i] & 0xFF;
			result[i*2] = hexChars[ r >>> 4];
			result[i*2+1] = hexChars[ r & 0x0F];
		}
		
		return new String(result);
	} 
	/**
	 * Privatna metoda, koja preko Ascii tablice pretvara zadani joj znak u integer reprezentaciju hex znamenke
	 * @param c
	 * @return broj
	 * @return -1. Ako je zadan karakter nije heksadekadski karakter.
	 */
	private static int hexToBinary(char c) {
		if (c >= '0' && c <= '9') return c-48;
		else if (c >= 'A' && c<= 'F') return c-65+10;
		else if (c >= 'a' && c<= 'f') return c-97+10;
		else return -1;
	}
}
