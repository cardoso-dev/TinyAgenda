<%-- 
    Document   : events
    Created on : 30/12/2013, 03:32:05 PM
    Author     : Pedro
--%>

<%@page import="com.tinyagenda.model.Event"%>
<%@page import="java.util.ArrayList"%>
<% int month=Integer.parseInt(request.getAttribute("loadmonth").toString());
   int year=Integer.parseInt(request.getAttribute("loadyear").toString());
   String theDate=(String)request.getAttribute("stringdate");
%>

<h1>  Your events on day <%=theDate%> </h1>

<% 
if( request.getAttribute("loadingempty")!=null && (Boolean)request.getAttribute("loadingempty") ){ %>
    <p class="error">You have no events recorded for this day</p>
<% }else{
       ArrayList events=(ArrayList)request.getAttribute("myevents");
       Event evt;
       for(Object objEvt: events){
           evt=(Event)objEvt; %>
           <div class="singleEvent">
               <p class="seHead"><%= evt.getName() %> @ <%= evt.getAnhour() %> </p>
               <p class="seBody">Info: <%= evt.getDescription() %> </p>
           </div>
       <% }
}
%>
<form action="month" method="get" class="formCenter">
    <input type="hidden" name="loadmonth" value="<%=month%>" />
    <input type="hidden" name="loadyear" value="<%=year%>" />
    <input type="submit" value="Go back" />
</form>
    
    