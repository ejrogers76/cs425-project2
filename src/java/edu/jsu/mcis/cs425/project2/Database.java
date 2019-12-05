package edu.jsu.mcis.cs425.project2;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public class Database {
    
    String getSkillsListAsHTML(int userid) {
        String query = "select skills.*,a.userid " +
        "from cs425_p2.skills as skills " +
        "left join (SELECT * FROM cs425_p2.applicants_to_skills where userid = ?) as a " +
        "on skills.id = a.skillsid ORDER BY description;";
        StringBuilder results = new StringBuilder();
        String skills;
        
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, Integer.toString(userid));
            boolean hasResults = statement.execute();
            ResultSet resultset = statement.getResultSet();
            
            while (resultset.next()) {
                String description = resultset.getString("description");
                int id = resultset.getInt("id");
                int user = resultset.getInt("userid");
                results.append("<input type=\"checkbox\" name=\"skills\" value=\"");
                results.append(id);
                results.append("\" id=\"skills_").append(id).append("\" ");
                //If checkbox checked add "checked"
                
                if (user != 0) {
                    results.append("checked");
                }
                
                results.append(">");
                //Description
                results.append("<label for=\"skills_").append(id).append("\">").append(description).append("</label><br /><br />");
            }
            resultset.close();
            statement.close();
        }
        catch (Exception SQLException) {}
        skills = results.toString();
        
        return skills;
    }
    
    public void setSkillsList(int userid, String[] skills) {
        String clear = "DELETE FROM cs425_p2.applicants_to_skills WHERE userid = ?;";
        String query = "INSERT INTO cs425_p2.applicants_to_skills (userid, skillsid) " +
                        "VALUES (?, ?);";
        
        
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(clear);
            statement.setString(1, Integer.toString(userid));
            int rowsChanged = statement.executeUpdate();
            
            
                statement = conn.prepareStatement(query);
                for (int i = 0; i < skills.length; i++) {
                    statement = conn.prepareStatement(query);
                    statement.setString(1, Integer.toString(userid));
                    statement.setString(2, skills[i]);
                    statement.execute();             
                }
        }
        catch (Exception SQLException) {}
    }
    
    String getJobsListAsHTML(int userid, String[] skills) {
        String query = "SELECT jobs.id, jobs.name, a.userid FROM " +
        "jobs LEFT JOIN (SELECT * FROM applicants_to_jobs WHERE userid= ?) AS a " +
        "ON jobs.id = a.jobsid " +
        "WHERE jobs.id IN " +
        "(SELECT jobsid AS id FROM " +
        "(applicants_to_skills JOIN skills_to_jobs " +
        "ON applicants_to_skills.skillsid = skills_to_jobs.skillsid) " +
        "WHERE applicants_to_skills.userid = ?) " +
        "ORDER BY jobs.name;";
        
        System.out.println(query);
        StringBuilder results = new StringBuilder();
        String[] jobsids;
        
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, Integer.toString(userid));
                statement.setString(2, Integer.toString(userid));
                statement.execute();
                ResultSet resultset = statement.getResultSet();
                while (resultset.next()) {
                    int jobid = resultset.getInt("id");
                    String jobname = resultset.getString("name");
                    int isChecked = resultset.getInt("userid");
                    results.append("<input type=\"checkbox\" name=\"jobs\" value=\"");
                    results.append(jobid);
                    results.append("\" id=\"job_").append(jobid).append("\" ");
                    //If checkbox checked add "checked"
                
                    if (isChecked != 0) {
                        results.append("checked");
                    }
                
                    results.append(">");
                    //Description
                    results.append("<label for=\"job_").append(jobid).append("\">").append(jobname).append("</label><br /><br />");
                }
        }
        catch(Exception SQLException){}
        return results.toString();
    }
    
    void setJobsList(int userid, String[] jobs) {
        String clear = "DELETE FROM cs425_p2.applicants_to_jobs WHERE userid = ?;";
        String query = "INSERT INTO cs425_p2.applicants_to_jobs (userid, jobsid)\n" +
                        "VALUES (?, ?);";
        
        
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(clear);
            statement.setString(1, Integer.toString(userid));
            int rowsChanged = statement.executeUpdate();
            
            
                statement = conn.prepareStatement(query);
                for (int i = 0; i < jobs.length; i++) {
                    statement = conn.prepareStatement(query);
                    statement.setString(1, Integer.toString(userid));
                    statement.setString(2, jobs[i]);
                    statement.execute();             
                }
        }
        catch (Exception SQLException) {}
    }
    
    public HashMap getUserInfo(String username) {
        
         String query = "SELECT * FROM user WHERE username = ?";
         HashMap<String, String> results = new HashMap<String, String>();
         int tempID;
         
        
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            boolean hasResults = statement.execute();
            
            if (hasResults) {
                ResultSet resultset = statement.getResultSet();
                if (resultset.next()) {
                    tempID = resultset.getInt("id");
                    results.put("userid", String.valueOf(tempID));
                    results.put("displayname", resultset.getString("displayname"));
                }
                resultset.close();
            }
            statement.close();
        }
        catch (Exception SQLException) {}
        return results;
    }
    
    public Connection getConnection() throws SQLException, NamingException {
        
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }
        
        catch (SQLException | NamingException e) {}
        return conn;
    }
    
}
