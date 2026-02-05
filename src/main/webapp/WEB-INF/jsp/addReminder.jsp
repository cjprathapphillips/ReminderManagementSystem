<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />
<script>
    function updateDefaultValues(){
        var reminderType = document.getElementById("reminderType");
        var frequency=document.getElementById("frequency");
        var unit=document.getElementById("unit");
        var frequencyTypeCode=document.getElementById("frequencyTypeCode");
        if(reminderType.value.split(",").length==3){
            frequency.value=reminderType.value.split(",")[0];
            unit.value=reminderType.value.split(",")[1];
            frequencyTypeCode.value=reminderType.value.split(",")[2];
        }
    }

    function updateAndValidate(){
        var reminderType=document.getElementById("reminderType");
        var frequencyTypeCode=document.getElementById("frequencyTypeCode");
        document.getElementById("reminderType").value=document.getElementById("frequency").value;
    }

</script>
<div class="container p-4">
  <form class="form-horizontal" action="/reminder/saveReminder" method="POST" onSubmit="updateAndValidate()">
   <div class="form-group">
     <label class="control-label col-sm-2" for="id">ID:</label>
     <div class="col-sm-10">
       <input type="id" class="form-control" id="id" placeholder="Id" readonly >
     </div>
   </div>
   <div class="form-group">
     <label class="control-label col-sm-2" for="name">Name:</label>
     <div class="col-sm-10">
       <input type="name" class="form-control" id="name" name="name" placeholder="Name">
     </div>
   </div>
   <div class="form-group">
       <label class="control-label col-sm-2" for="frequencyType">Frequency Type:</label>
       <div class="col-sm-10">
         <select id="reminderType" name="reminderType" onChange="updateDefaultValues()">
                 	<option value="">Select</option>
                 	<c:forEach items="${reminderTypeList}" var="reminderType">
                 		<option value="${reminderType.defaultFrequency},${reminderType.defaultUnit},${reminderType.id} ">${reminderType.name}</option>
                 	</c:forEach>
          </select>
       </div>
      </div>
   <div class="form-group">
    <label class="control-label col-sm-2" for="frequency">Frequency:</label>
    <div class="col-sm-10">
    <input type="hidden" id="frequencyTypeCode" name="frequencyTypeCode" value="0">
      <select id="frequency" id="frequency" name="frequency">
        <option value="">Select</option>
        <option value="Year">Year</option>
        <option value="Month">Month</option>
        <option value="Week">Week</option>
        <option value="Day">Day</option>
        <option value="Hour">Hour</option>
      </select>
    </div>
   </div>
   <div class="form-group">
     <label class="control-label col-sm-2" for="unit">Unit:</label>
     <div class="col-sm-10">
       <input type="unit" class="form-control" id="unit" name="unit" placeholder="Unit" >
     </div>
   </div>
   <div class="form-group">
        <label class="control-label col-sm-2" for="renewDate">Expires On:</label>
        <div class="col-sm-10">
          <input type="renewDateString" class="form-control" id="renewDateString" name="renewDateString" placeholder="renewDateString" value="2026-01-01" >
        </div>
      </div>
   <div class="form-group">
     <label class="control-label col-sm-2" ></label>
     <div class="col-sm-offset-2 col-sm-10">
     <div class="col-sm-10">
       <button class="btn btn-warning"  type="submit" name="save" id="save" value="save" class="btn btn-default">Submit</button>
       <button class="btn btn-warning"  type="submit" name="cancel" id="cancel" value="cancel" class="btn btn-default">Cancel</button>
     </div>
     </div>
   </div>
 </form>
</div>
<jsp:include page="footer.jsp" />
