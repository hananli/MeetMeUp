package com.meetmeup.helper;

public class AppConstants {
	public static final String networkError = "Please Cheack internate connectivity.";
	private static String lattitude;
	private static  String logitude;
	public static String getLattitude() {
		return lattitude;
	}
	public static void setLattitude(String lattitude) {
		AppConstants.lattitude = lattitude;
	}
	public static String getLogitude() {
		return logitude;
	}
	public static void setLogitude(String logitude) {
		AppConstants.logitude = logitude;
	}
}
