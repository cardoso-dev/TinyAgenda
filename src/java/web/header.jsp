<%-- 
    Document   : header.jsp
    Created on : 22/12/2013, 07:45:20 PM
    Author     : Pedro
--%>

<%@page import="com.tinyagenda.model.Person"%>
<section id="sectHeader">
    <h1>Tiny Agenda</h1>
    <div id="log_io">
        <% if(session.getAttribute("user")!=null) { %>
            <form method="post" action="dologout">
                <label>Welcome: <em><%= ((Person)session.getAttribute("user")).getName() %> </em></label>
                <input type="submit" value="logout" />
            </form>
        <% }else{ %>
            <form method="post" action="dologin">
                <label>User</label> <input type="text" name="usr" />
                <label>Password</label> <input type="password" name="pwd" />
                <input type="submit" value="LogIn" />
                <% if(request.getAttribute("trylogin")!=null && !(Boolean)request.getAttribute("trylogin")){ %>
                     <br /><label class="error">Failed to login, please confirm your data and try again</label> 
                <% } %>
            </form>
        <% } %>        
    </div>
</section>