<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />


<div class="container p-4">
  <form class="form-horizontal" action="/reminder/edit" method="POST">
   <div class="form-group">
     <label class="control-label col-sm-2" for="id">ID:</label>
     <div class="col-sm-10">
       <input type="id" class="form-control" id="id" placeholder="Id" name="id" value="${reminder.id}" >
     </div>
   </div>
   <div class="form-group">
     <label class="control-label col-sm-2" for="pwd">Name:</label>
     <div class="col-sm-10">
       <input type="name" class="form-control" id="name" placeholder="Name" name="name" value="${reminder.name}">
     </div>
   </div>
   <div class="form-group">
    <label class="control-label col-sm-2" for="frequency">Frequency:</label>
    <div class="col-sm-10">
      <select id="frequency" name="frequency">
          <option value="">Select</option>
            <c:if test="${reminder.frequency=='Year'}">
                  <option value="Year" selected >Year</option>
            </c:if>
            <c:if test="${reminder.frequency!='Year' }">
                  <option value="Year">Year</option>
            </c:if>
            <c:if test="${reminder.frequency=='Month'}">
                <option value="Month" selected >Month</option>
            </c:if>
            <c:if test="${reminder.frequency!='Month' }">
                   <option value="Month">Month</option>
            </c:if>
            <option value="Week">Week</option>
            <option value="Day">Day</option>
            <option value="Hour">Hour</option>
      </select>
    </div>
   </div>
   <div class="form-group">
     <label class="control-label col-sm-2" for="unit">Unit:</label>
     <div class="col-sm-10">
       <input type="unit" class="form-control" id="unit" placeholder="Unit" name="unit" value="${reminder.unit}">
     </div>
   </div>
   <div class="form-group">
        <label class="control-label col-sm-2" for="renewDateString">Expires On:</label>
        <div class="col-sm-10">
          <input type="renewDateString" class="form-control" id="renewDateString" name="renewDateString" placeholder="renewDateString" value="${reminder.renewDate}">
        </div>
      </div>
   <div class="form-group">
     <label class="control-label col-sm-2">
         <label class="control-label col-sm-2" ></label>
        <div class="col-sm-10">
        <button class="btn btn-warning"  type="submit" class="btn btn-default" name="save" id="save" value="save" >Save</button>
        <button class="btn btn-warning"  type="submit" class="btn btn-default" name="cancel" id="cancel" value="cancel" >Cancel</button>
        </div>
     </label>
   </div>
 </form>
</div>
<jsp:include page="footer.jsp" />
