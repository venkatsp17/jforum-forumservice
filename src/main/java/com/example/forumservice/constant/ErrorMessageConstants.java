package com.example.forumservice.constant;

public class ErrorMessageConstants {

    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String FORUM_ID_MUST_BE_ZERO = "This appears to be an existing forum (id > 0). Please use update() instead.";
    public static final String FORUM_CANNOT_BE_NULL = "Forum cannot be null.";
    public static final String FORUM_NOT_FOUND = "Forum not found.";
    public static final String FORUM_ID_MUST_BE_VALID = "Forum must have an existing ID greater than zero.";
    public static final String FORUM_ALREADY_AT_TOP = "Forum is already at the top position.";
    public static final String FORUM_ALREADY_AT_BOTTOM = "Forum is already at the bottom position.";
    public static final String POST_NOT_FOUND = "Post not found.";
    public static final String INVALID_POST_ID = "Invalid post ID.";
    public static final String AUTH_TOKEN_MISSING = "Authentication token is missing.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String INVALID_PAGE_NUMBER = "Page number must be greater than zero.";
    public static final String INVALID_REPORT_ID = "Invalid report ID.";
    public static final String POST_REPORT_NOT_FOUND = "Post report not found.";
    public static final String USER_CANNOT_MANIPULATE_REPORT = "You do not have permission to manipulate this report.";
    public static final String MODERATION_LOGGING_DISABLED = "Moderation logging is disabled.";
}
