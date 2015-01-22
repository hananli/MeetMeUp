package com.meetmeup.helper;

public class WebServices {
	public static final String main_Url = "http://72.167.41.165/meetmeup/webservices/";
	public static final String WEB_LOGIN = main_Url+"fb_login.php";
	public static final String WEB_GET_EVENTS_LIST = main_Url+"get_event_list.php";
	public static final String WEB_GET_EVENT_DETAIL = main_Url+"get_event_detail.php";
	public static final String WEB_CREATE_EVENT = main_Url+"create_event.php";
	public static String SERACH_NEAR_PLACES ="https://maps.googleapis.com/maps/api/place/search/json?";
	public static String SERACH_NEAR_PLACES_BYTAG ="https://maps.googleapis.com/maps/api/place/textsearch/json?";
	public static final String USER_CURRENT_LATLONG = main_Url+"set_user_current_position.php";
	public static final String GET_NEAR_BY = main_Url+"get_nearby_location.php";
	public static final String GET_Fb_Friends = main_Url+"get_fb_friend_list.php";
		public static final String Accept_Reject = main_Url+"accept_reject.php";
		public static final String DeleteEvent = main_Url+"event_delete.php";
		public static final String EditEvent = main_Url+"event_edit.php";

}
