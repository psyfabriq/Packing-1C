
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../resources/app/bootstrap.css">
<style type="text/css">
     .tbln>tbody>tr>td{
      padding: 2px;
    }
     .tblh>tbody>tr>td{
      padding: 4px;
    }
</style>
<title>Insert title here</title>
</head>
<body>
 <div class="container-fluid">
 
 <table id="xdddd" class="table table-striped table-bordered dt-responsive nowrap tblh " style="margin-bottom: 0;">
  <thead>
  <tr height="0">
     <th  style="width: 45%"></th>
     <th  style="width: 60%"></th>
  </tr>
  </thead>
  <tbody>
     <tr>
      <td>Заказ №:</td>
      <td align="center">${order}</td>
     </tr>
      <tr>
      <td>Дата:</td>
      <td align="center">${data}</td>
     </tr>
      <tr>
      <td>Заказчик:</td>
      <td align="center">${z}</td>
     </tr>
     <tr>
      <td>Получатель:</td>
      <td align="center">${p}</td>
     </tr>
      <tr>
      <td>Порядковый номер груза:</td>
      <td align="center">${png}</td>
     </tr>
      <tr>
      <td>Масса грузового места (брутто, кг):</td>
      <td align="center">${mgm}</td>
     </tr> 
  </tbody>
</table>
 
 <table id="example" class="table table-striped table-bordered dt-responsive nowrap tbln" style="margin-bottom: 0;">
  <thead>
  
  <tr height="15px">
     <th  style="width: 2%" align="center">№</th>
     <th  style="width: 43%">Наименование</th>
     <th  style="width: 15%" align="center">Артикул</th>
     <th  style="width: 5%" align="center">ед.из</th>
     <th  style="width: 10%" align="center">кол-во</th>
     <th  style="width: 10%" align="center">вес ед</th>
  </tr>
  </thead>
  <tbody>
     <c:if test="${not empty lists}">
        
		
			<c:forEach var="listValue" varStatus="status" items="${lists}">
			    <tr>
				      <td>${status.count}</td>
				      <td>${listValue.name}</td>
				      <td align="center" >${listValue.article}</td>
				      <td align="center" >${listValue.ex}</td>
				      <td align="center" >${listValue.selectedcol}</td>
				      <td align="center" >${listValue.ves}</td>
				</tr>
			</c:forEach>
		

	</c:if>
  </tbody>
</table>

<table id="xdddd34" class="table table-striped table-bordered dt-responsive nowrap tblh " style="margin-bottom: 0;">
  
  <tbody>
     <tr>
      <td style="width: 79%">Кол-во шт. в грузовом месте</td>
      <td style="width: 14%" align="center">${allcount}</td>
      <td style="width: 10%" align="center"></td>
     </tr>
      
  </tbody>
</table>

<div class="row">
  <div class="col-md-4"></div>
  <div class="col-md-4 text-center"><img src="${barcode}"/></div>
  <div class="col-md-4"></div>
</div>

  
 
 </div>

</body>
</html>