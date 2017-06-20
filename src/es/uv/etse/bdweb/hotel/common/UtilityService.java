package es.uv.etse.bdweb.hotel.common;

import es.uv.etse.bdweb.hotel.common.Constants;

public class UtilityService {
	 
		public static String getPasswordNumeric(int length) {
			return getPassword(Constants.NUMEROS, length);
		}
	 
		public static String getPasswordAlphanumeric(int length) {
			return getPassword(length);
		}
		
		public static String getPassword(int length) {
			return getPassword(Constants.NUMEROS + Constants.MAYUSCULAS + Constants.MINUSCULAS, length);
		}
	 
		public static String getPassword(String key, int length) {
			String pswd = "";
	 
			for (int i = 0; i < length; i++) {
				pswd+=(key.charAt((int)(Math.random() * key.length())));
			}
			
			return pswd;
		}
}
