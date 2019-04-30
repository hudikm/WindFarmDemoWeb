package sk.fri.uniza.auth;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;

import java.util.List;

public class Role {
    public final static String USER_READ_ONLY = "USER_READ_ONLY";
    public final static String ADMIN = "ADMIN";
    public final static List<String> Roles = ImmutableList.of(USER_READ_ONLY, ADMIN);
    private static Role instance = null;

    public static List<String> getAllRoles() {
        return Roles;
    }

    public static String getUserReadOnly() {
        return USER_READ_ONLY;
    }

    public static String getADMIN() {
        return ADMIN;
    }

    public static Role getInstance() {
        if (null == instance) instance = new Role();
        return instance;
    }


}