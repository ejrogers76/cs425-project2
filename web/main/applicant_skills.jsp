<%-- 
    Document   : applicant_skills
    Created on : Nov 15, 2019, 2:30:34 PM
    Author     : Ethan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="applicant" class="edu.jsu.mcis.cs425.project2.BeanApplicant" scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Applicant Skills Page</title>
    </head>
    <body>
        <form id="skillChecks" name ="skillForm" method="post" action="applicant_jobs.jsp">
            <fieldset>
                <legend>Select Your Skills Here</legend>
                <jsp:getProperty name="applicant" property="skillsList" />
                <input type="submit" value="Submit" />
            </fieldset>
        </form>
    </body>
</html>
