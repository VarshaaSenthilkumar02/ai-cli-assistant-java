package com.varshaa.aiassistant.role;

public class RoleFactory {
    public static Role getRole(String role) {
        if(role.equalsIgnoreCase("teacher")) return new TeacherRole();
        if(role.equalsIgnoreCase("interviewer")) return new InterviewerRole();
        if(role.equalsIgnoreCase("mentor")) return new MentorRole();
        return new DefaultRole();
    }
}
