<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />
<script>

</script>
<div class="container p-4">
  <form class="form-horizontal" action="/user/save" method="POST">
   <div class="form-group">
     <label class="control-label col-sm-2" for="id">ID:</label>
     <div class="col-sm-10"><input type="hidden" id="userid" value="${user.userId}">
       <input type="userId" class="form-control" id="userId" placeholder="userId" readonly value="${user.userId}">
     </div>
   </div>
   <div class="form-group">
     <label class="control-label col-sm-2" for="name">Name:</label>
     <div class="col-sm-10">
       <input type="username" class="form-control" id="username" name="username" placeholder="username" value="${user.username}">
     </div>
   </div>
   <div class="form-group">
        <label class="control-label col-sm-2" for="name">Password:</label>
        <div class="col-sm-10">
          <input type="passwordShow" class="form-control" id="password" name="password" placeholder="password" value="${user.password}">
        </div>
   </div>
   <div class="form-group">
           <label class="control-label col-sm-2" for="name">Authorities:</label>
           <div class="col-sm-10">
             <input type="authoritiesShow" class="form-control" id="authorities" name="authorities" placeholder="authorities" value="${user.authorities}">
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
