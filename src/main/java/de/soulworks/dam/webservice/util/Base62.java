package de.soulworks.dam.webservice.util;

import java.math.BigInteger;

public class Base62 {
	private static final String baseDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static String toBase62(int decimalNumber) {
		return fromDecimalToOtherBase(62, decimalNumber);
	}

	public static int fromBase62(String base62Number) {
		return fromOtherBaseToDecimal(62, base62Number);
	}

	private static String fromDecimalToOtherBase(int base, int decimalNumber) {
		String result = decimalNumber == 0 ? "0" : "";
		while (decimalNumber != 0) {
			int mod = decimalNumber % base;
			result = baseDigits.substring(mod, mod + 1) + result;
			decimalNumber = decimalNumber / base;
		}
		return result;
	}

	private static int fromOtherBaseToDecimal(int base, String number) {
		int result = 0;
		for (int pos = number.length(), multiplier = 1; pos > 0; pos--) {
			result += baseDigits.indexOf(number.substring(pos - 1, pos)) * multiplier;
			multiplier *= base;
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(toBase62(37984));
		System.out.println(fromBase62("9se"));
	}
}
