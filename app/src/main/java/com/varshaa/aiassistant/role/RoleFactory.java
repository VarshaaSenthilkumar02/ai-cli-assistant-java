package com.varshaa.aiassistant.role;

import java.util.HashMap;
import java.util.Map;

public class RoleFactory {
    public static final Map<String, Role> roleRegistry = new HashMap<>();

    static {
        roleRegistry.put("teacher", new TeacherRole());
        roleRegistry.put("mentor", new MentorRole());
        roleRegistry.put("interviewer", new InterviewerRole());
        roleRegistry.put("default", new DefaultRole());
    }

    public static Role getRole(String role) {
        return roleRegistry.getOrDefault(role.toLowerCase(), new DefaultRole());
    }
}
