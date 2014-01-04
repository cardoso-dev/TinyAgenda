<%-- 
    Document   : month
    Created on : 24/12/2013, 07:50:25 PM
    Author     : Pedro
--%>

<% if( request.getAttribute("mymonth")!=null ) { %>
<jsp:useBean id="mymonth" scope="request" class="com.tinyagenda.utils.AgendaMonth" />
   <ul id="lateralMenu">
       <li> <%= mymonth.getLink2Month(-2) %> </li>
       <li> <%= mymonth.getLink2Month(-1) %> </li>
       <li> <%= mymonth.getMonthname() %> </li>
       <li> <%= mymonth.getLink2Month(1) %> </li>
       <li> <%= mymonth.getLink2Month(2) %> </li>
       <li> <%= mymonth.getLink2Month(3) %> </li>
       <li> <%= mymonth.getLink2Month(4) %> </li>
       <li> <%= mymonth.getLink2Month(5) %> </li>
       <li> <%= mymonth.getLink2Month(6) %> </li>
       <li> <%= mymonth.getLink2Month(7) %> </li>
   </ul>
   <% if( request.getAttribute("trysvevent")!=null && (Boolean)request.getAttribute("trysvevent")) { %>
     <p class="success">The event has been saved</p>
   <% } %>
   <table class="calendar">
      <thead>
        <tr> 
            <td colspan="7">
                <jsp:getProperty name="mymonth" property="monthname" />
            </td>
        </tr>
        <tr> <td>Mon</td> <td>Tue</td> <td>Wed</td> <td>Thu</td> <td>Fri</td> <td>Sat</td> <td>Sun</td> </tr>
      </thead>
      <tbody>
        <% int currDay=1; %>
        <% int totalDays=mymonth.getDaysinmonth(); %>
        <% int posX=mymonth.getFirstDay(); %>
        <% do{ %>
            <tr> 
                <% for(int d=0;d<7;d++){ %>
                  <td> <%
                    if(d>=posX && currDay<=totalDays){ %>
                       <div class="monthday">
                           <%= currDay %>
                       </div>
                       <form method="post" action="dayAddEvent">
                           <input name="addEvent" type="image" src="img/add.png" alt="Add New Event" title="Add New Event" />
                           <input type="hidden" name="loadmonth" value="<%= mymonth.getMonth() %>" />
                           <input type="hidden" name="loadyear" value="<%= mymonth.getYear() %>" />
                           <input type="hidden" name="loadday" value="<%= currDay %>" />
                       </form>
                       <% String theHref="href=\"seeDayEvents?loadday="+currDay+"&loadmonth="+mymonth.getMonth()+"&loadyear="+mymonth.getYear()+"\""; 
                         int currEvents=mymonth.getEventsPerDay(currDay);
                       %>
                       <a <%=theHref%> class="dayevents <%if(currEvents==0){%> dayeventsempty<%}else{%> dayeventsnotempty<%}%>" title="See events for this day">
                           <%= currEvents %>
                       </a>
                    <% currDay++; } 
                  %> </td> 
                <% } %>
            </tr>
        <% posX=0;
        }while(currDay<=totalDays); %>
      </tbody>
   </table>
<% } %> 

