package edu.jsu.mcis.cs425.project2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;

public class BeanApplicant {
    
    private String username;
    private String displayName;
    private int id;
    
    private String[] skills;
    private String[] jobs;
    
    public String[] getSkills() {
        return skills;
    }
    
    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getSkillsList() {
        Database db = new Database();
        return ( db.getSkillsListAsHTML(id));
    }

    public void setSkillsList() {
        Database db = new Database();
        db.setSkillsList(id, skills);
    }
    
    public void setUserInfo() {
        Database db = new Database();
        HashMap<String, String> userinfo = db.getUserInfo(username);
        id = Integer.parseInt(userinfo.get("userid"));
        displayName = userinfo.get("displayname");
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayname) {
        this.displayName = displayname;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int userid) {
        this.id = userid;
    }
    
    public String[] getJobs() {
        return jobs;
    }
    
    public void setJobs(String[] jobs) {
        this.jobs = jobs;
    }

    public String getJobsList() {
        Database db = new Database();
        return db.getJobsListAsHTML(id, skills);
    }

    public void setJobsList() {
        Database db = new Database();
        db.setJobsList(id, jobs);
    }
    
    
    
}