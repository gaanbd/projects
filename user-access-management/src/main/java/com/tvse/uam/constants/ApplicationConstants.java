package com.tvse.uam.constants;

import java.util.UUID;

import org.springframework.http.HttpStatus;

/**
 * ApplicationConstants Class to keep the constant values
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
public class ApplicationConstants {

	private ApplicationConstants() {
	}

	public static final String TEMP_USERP = "$TEMP_PASSWORD$";
	public static final String USERNAME = "$USERNAME$";
	public static final String CREATE_PASS_LINK = "$CREATEPASSURL$";
	public static final String DATE = "$DATETIME$";
	public static final String TVSE_LOGO = "$TVSE_LOGO$";

	public static final String MENU = "Menu";
	public static final String GRANT_TYPE = "password";
	public static final String AUTHORIZATION_CODE = "authorization_code";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String IMPLICIT = "implicit";
	public static final String SCOPE_READ = "read";
	public static final String SCOPE_WRITE = "write";
	public static final String RESOURCE_ID = "resource_id";
	public static final String MOBILE_PATTERN = "[0-9]+";
	public static final String EMPTY_SPACE = " ";
	public static final String EDIT = "edit";
	public static final String DELETE = "delete";

	public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60;
	public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;
	public static final UUID SUPER_ADMIN = UUID.fromString("17e405e6-46eb-11e9-b210-d663bd873d93");
	public static final String PASSWRD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	public static final String GETALLUSERDETAILS = "/getAllUserDetails";
	public static final String GETALLCOUNTRY = "/getAllCountry";
	public static final String GETALLSTATEBYCOUNTRY = "/getAllStateByCountry/{countryId}";
	public static final String COUNTRYID = "countryId";
	public static final String STATEID = "stateId";
	public static final String GETALLCITYBYSTATE = "/getAllCityByState/{stateId}";
	public static final String LOGINNAMECHECK = "/loginNameCheck/{loginName}";
	public static final String EMPLOYEEID_UNIQUE = "/employeeId/{employeeId}";
	public static final String USER = "/user";
	public static final String GETBYID = "getById/{id}";
	public static final String ID_STATUS = "/{id}/status";
	public static final String USER_PROFILE = "/{id}/profile";
	public static final String LOGINNAME = "loginName";
	public static final String EMPLOYEEID = "employeeId";
	public static final String APPLICATIONID = "applicationId";
	public static final String ROLEDESCRIPTION = "roleDescription";
	public static final String ROLENAME = "roleName";
	public static final String ROLEDISPLAYNAME = "roleDisplayName";
	public static final String ROLEID = "roleId";

	public static final String MENUS = "/menus";
	public static final String USERS = "/users";
	public static final String ROLES = "/roles";
	public static final String USERROLES = "/userroles";
	public static final String APPLICATIONS = "/applications";
	public static final String BRANDS = "/brands";
	public static final String APPLICATIONROLES = "/applicationRole";
	public static final String USERROLES_USERID = "/{userId}";
	public static final String USERROLES_VIEW = "/view/{userId}";
	public static final String USERROLES_DELETE = "/{userId}";
	public static final String BRANCHES_BY_ZONE = "/branches/{zoneId}";
	public static final String ZONE = "/zone";
	public static final String USERID = "userId";
	public static final String ZONEID = "zoneId";
	public static final String ID = "id";

	// Role Creation and Role Permission
	public static final char READ_ONLY_OPERATION = 'R';
	public static final String READ_ONLY_PERMISSION = "ReadOnly";
	public static final char CREATE_OPERATION = 'C';
	public static final String CREATE_PERMISSION = "Create";
	public static final char CREATE_MODIFY_OPERATION = 'M';
	public static final String CREATE_MODIFY_PERMISSION = "CreateModify";
	public static final char FULL_ACCESS_OPERATION = 'F';
	public static final String FULL_ACCESS_PERMISSION = "FullAccess";

	public static final String GET_ALL_ROLE_WITH_APPLICATION = "/roleswithapplicaitons";
	public static final String GET_ALL_APPLICATION_AND_ENTITLEMENT_PERMISSION = "/applicationandentitlement";
	public static final String CREATE_ROLE = "/createrole";
	public static final String UPDATE_ROLE = "/updaterole";
	public static final String DELETE_OR_GET_ROLE = "/role/{roleId}";
	public static final String ROLE_NAME_CHECK = "/role/rolenamecheck/{applicationId}/{roleName}";
	public static final String ROLE_DISPLAY_NAME_CHECK = "/role/roledisplaynamecheck/{applicationId}/{roleDisplayName}";

	public static final String ROLE_VALIDATION = "/validaterole/{applicationId}/{role}/{from}";
	public static final String GET_ALL_ENTITLEMENTS = "/entitlements";
	public static final String ROLE_ID = "/{roleId}";

	public static final int SUCCESS_CODE_200 = HttpStatus.OK.value();
	public static final int SUCCESS_CODE_201 = HttpStatus.CREATED.value();
	public static final int ERROR_CODE_400 = HttpStatus.BAD_REQUEST.value();
	public static final int ERROR_CODE_404 = HttpStatus.NOT_FOUND.value();
	public static final int ERROR_CODE_500 = HttpStatus.INTERNAL_SERVER_ERROR.value();
	public static final int ERROR_CODE_409 = HttpStatus.CONFLICT.value();
	public static final int ERROR_CODE_417 = HttpStatus.EXPECTATION_FAILED.value();

	public static final String SUCCESS_MESSAGE_200 = "success.message";
	public static final String UNEXPECTED_ERROR = "error.unexpected";
	public static final String UNEXPECTED_DATA_ACCESS_ERROR = "error.unexpected.dataaccess";
	public static final String B2B_APPLICATION = "B2B";
	public static final String CHANGE_PASSWRD = "/changepassword";
	public static final String PROFILE_IMAGE_UPLOAD = "profileImageUploadAction";
}