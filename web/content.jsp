<%-- 
    Document   : content
    Created on : 22/12/2013, 07:49:16 PM
    Author     : Pedro
--%>

<section id="sectContent">
        <% if(session.getAttribute("user")!=null) { 
            if( request.getAttribute("loadContent")!=null ){ 
                String filetoinc=(String)request.getAttribute("loadContent"); 
                if(filetoinc.equals("month.jsp")){ %>
                    <%@ include file="month.jsp" %>
                <% }else if(filetoinc.equals("eventForm.jsp")){ %>
                    <%@ include file="eventForm.jsp" %>
                <% }else if(filetoinc.equals("events.jsp")){ %>
                    <%@ include file="events.jsp" %>
                <% }
             } %>
        <% }else{ %>
        <p> Please sign in in the system or sign up to use the tiny agend functionality. </p>
        <form method="post" action="signup">
            <% if(request.getAttribute("trysignup")!=null && !(Boolean)request.getAttribute("trysignup")){ %>
                 <label class="error"><%= request.getAttribute("errorsignup") %></label> <br />
            <% }else if(request.getAttribute("trysignup")!=null && (Boolean)request.getAttribute("trysignup")){ %>
                 <label class="success">You area successfully signed up, now you can use your data to log in</label> <br />
            <% } %>
            <label>Enter the following data to sign up</label> <br />
            <label>User</label> <input type="text" name="usr" /> <br />
            <label>Password</label> <input type="password" name="pwd" /> <br />
            <label>Again</label> <input type="password" name="pwd2" /> <br />
            <input type="submit" value="Sign Up" />
        </form>
        <% } %>   
</section>
