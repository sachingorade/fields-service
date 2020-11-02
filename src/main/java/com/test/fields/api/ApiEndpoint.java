package com.test.fields.api;

public final class ApiEndpoint {
    public static final String FIELD_ID = "fieldId";
    public static final String FIELDS = "/fields";
    public static final String FIELDS_WITH_FIELD_ID = FIELDS + "/{" + FIELD_ID + "}";
    public static final String FIELD_WEATHER = FIELDS_WITH_FIELD_ID + "/weather";
}
