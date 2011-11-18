package de.soulworks.dam.webservice.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ISRC (International Standard Recording Code) is the international identification
 * system for sound recordings and music videorecordings. Each ISRC is a unique and
 * permanent identifier for a specific recording which can be permanently encoded into
 * a product as its digital fingerprint. Encoded ISRC provide the means to automatically
 * identify recordings for royalty payments.
 *  
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class InternationalStandardRecordingCode {
	public static final Pattern PATTERN = Pattern.compile("^(\\w{2})([\\w\\d]{3})(\\d{2})(\\d{5})$");

	// ISO 3166-1 alpha-2 country code
	String country;
	String registrant;
	int year;
	int number;

	public static InternationalStandardRecordingCode createFromString(String code) {
		if(code.length() != 12) {
			throw new IllegalArgumentException("Invalid irs code");
		}

		Matcher m = PATTERN.matcher(code);

		// If the code doesn't match or we don't get exactly four matches
		if(!m.matches() || m.groupCount() != 4) {
			throw new IllegalArgumentException("Invalid code");
		}

		// Instantiate a new isrc
		InternationalStandardRecordingCode isrc = new InternationalStandardRecordingCode();

		// Bind the retrieved data to our newly created isrc
		isrc.setCountry(m.group(1));
		isrc.setRegistrant(m.group(2));
		isrc.setYear(Integer.parseInt(m.group(3)));
		isrc.setNumber(Integer.parseInt(m.group(4)));

		// Return our isrc
		return isrc;
	}

	public String toString() {
		return country + registrant + String.format("%02d", (year > 2000 ? year - 2000 : year)) + String.format("%05d", number);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		country = country.trim().toUpperCase();
		
		boolean isValidCountry = false;
		for(String c: Locale.getISOCountries()) {
			if(country.equals(c)) {
				isValidCountry = true;
				break;
			}
		}

		if(!isValidCountry) {
			throw new IllegalArgumentException("Invalid country "+country+" at isrc");
		}

		this.country = country;
	}

	public String getRegistrant() {
		return registrant;
	}

	public void setRegistrant(String registrant) {
		registrant = registrant.trim().toUpperCase();
		
		if(registrant.length() != 3) {
			throw new IllegalArgumentException("Invalid registrant");
		}

		/**
		 * The US National ISRC Agency uses the Registrant Code S1Z for
		 * illustrative purposes in documentation and training materials.
		 * It will never be allocated to a registrant and is invalid in an ISRC.
		 */
		if(registrant.equals("S1Z")) {
			throw new IllegalArgumentException("Invalid registrant");
		}

		this.registrant = registrant;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
/*
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		int currentYear = calendar.get(java.util.Calendar.YEAR);
		
		if(year < 2000) {
			year += 2000;
		}
*/
		this.year = year;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
