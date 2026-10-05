<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="uri" value="${pageContext.request.requestURI}" />
<div class="admin-sidebar">
	<div class="admin-brand">BookStore <span>Admin</span></div>

	<ul class="admin-nav">
		<li><a href="<c:url value='/admin/home'/>" class="${fn:contains(uri, '/admin/home') ? 'active' : ''}">Trang chủ quản trị</a></li>
		<li><a href="<c:url value='/admin/book/list'/>" class="${fn:contains(uri, '/admin/book') ? 'active' : ''}">Quản lý sách</a></li>
	</ul>

	<div class="admin-sidebar-bottom">
		<a href="<c:url value='/products'/>">Xem trang bán hàng</a>
		<a href="<c:url value='/logout'/>">Đăng xuất</a>
	</div>
</div>
