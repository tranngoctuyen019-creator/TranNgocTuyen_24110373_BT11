<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="admin-topbar">
	<div class="admin-topbar-title">Trang quản trị</div>
	<c:if test="${not empty sessionScope.user}">
		<a href="<c:url value='/home'/>" class="admin-topbar-user">
			Xin chào, ${sessionScope.user.fullname}
		</a>
	</c:if>
</div>
