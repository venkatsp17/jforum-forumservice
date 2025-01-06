package com.example.forumservice.constant;

public class ValidationMessageConstants {

    public static final String FORUM_NAME_REQUIRED = "A forum must have a name.";
    public static final String FORUM_CATEGORY_REQUIRED = "A forum must be associated to a category.";
    public static final String FORUM_CANNOT_BE_NULL = "Forum cannot be null.";
    public static final String FORUM_NAME_MAX_LENGTH = "Forum name cannot exceed 255 characters.";
    public static final String FORUM_LIMITED_TIME_REQUIRED = "Limited time is required.";
    public static final String FORUM_LIMITED_TIME_MUST_BE_NON_NEGATIVE = "Limited time must be zero or a positive value.";
    public static final String DESCRIPTION_REQUIRED = "Description is required.";
    public static final String DESCRIPTION_MAX_LENGTH = "Description cannot exceed 1000 characters.";
    public static final String INVALID_POST_ID = "Invalid post ID.";
    public static final String AUTH_TOKEN_MISSING = "Authentication token is missing.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String MODERATION_LOG_REQUIRED = "Log message is required.";
}
