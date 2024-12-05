package org.severinu.demoapi.api.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DemoApiConstants {

    public static final String SCHEMA_HEADER = "schema";
    public static final String VIEW_HEADER = "view";
    public static final String ROLES_HEADER = "x-auth-roles";

}
