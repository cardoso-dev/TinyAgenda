<%-- 
    Document   : eventForm
    Created on : 26/12/2013, 09:18:54 PM
    Author     : Pedro
--%>
<% String year=request.getParameter("loadyear");
   String month=request.getParameter("loadmonth");
   String day=request.getParameter("loadday");
%>

<h1>  Add event on day <%= year+"-"+(Integer.parseInt(month)+1)+"-"+day%> </h1>

<form method="post" action="saveEvent" class="formEvent">
    <% if(request.getAttribute("trysvevent")!=null && !(Boolean)request.getAttribute("trysvevent")){ %>
       <p class="error"><%= request.getAttribute("errorsaving") %></p>
    <% } %>
   <label>Hour:</label> 
       <select name="hh">
           <% for(int cnt=0;cnt<24;cnt++){ %>
              <option value="<%=(cnt<=9?"0":"")+cnt%>"> <%=(cnt<=9?"0":"")+cnt%> </option>
           <% } %>
       </select> 
   <label>:</label> 
       <select name="mm">
           <% for(int cnt=0;cnt<60;cnt++){ %>
              <option value="<%=(cnt<=9?"0":"")+cnt%>"> <%=(cnt<=9?"0":"")+cnt%> </option>
           <% } %>
       </select> 
   <label>Event:</label> <input type="text" name="name" /> <br />
   <label>Info:</label> <textarea name="about"></textarea> <br />
   <input type="submit" value="Save" />
   <input type="hidden" name="loadday" value="<%=day%>" />
   <input type="hidden" name="loadmonth" value="<%=month%>" />
   <input type="hidden" name="loadyear" value="<%=year%>" />
</form>
<form action="month" method="get" class="formCenter">
    <input type="hidden" name="loadmonth" value="<%=month%>" />
    <input type="hidden" name="loadyear" value="<%=year%>" />
    <input type="submit" value="cancel" />
</form>