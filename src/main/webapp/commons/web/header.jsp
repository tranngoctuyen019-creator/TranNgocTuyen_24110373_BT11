<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	int cartCount = 0;
	Object sessionUser = session.getAttribute("user");
	if (sessionUser instanceof vn.iotstar.models.User_24110373
			&& !((vn.iotstar.models.User_24110373) sessionUser).isAdmin()) {
		try {
			cartCount = new vn.iotstar.service.impl.CartServiceImpl_24110373()
					.countItems(((vn.iotstar.models.User_24110373) sessionUser).getId());
		} catch (Exception ignored) {
		}
	}
	request.setAttribute("cartCount", cartCount);
%>
<nav class="navbar navbar-custom">
	<div class="container-fluid" style="max-width:1180px; margin:0 auto;">
		<div class="navbar-header">
			<a class="navbar-brand" href="<c:url value='/home'/>">📚 BookStore</a>
		</div>
		<ul class="nav navbar-nav">
			<li><a href="<c:url value='/home'/>">Trang Chủ</a></li>
			<li><a href="<c:url value='/products'/>">Sản phẩm</a></li>
			<c:if test="${not empty sessionScope.user && sessionScope.user.admin}">
				<li><a href="<c:url value='/admin/home'/>">Trang quản trị</a></li>
			</c:if>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<c:choose>
				<c:when test="${not empty sessionScope.user}">
					<c:if test="${not sessionScope.user.admin}">
						<li><a href="<c:url value='/cart'/>">Giỏ hàng<c:if test="${cartCount > 0}"> <span class="cart-badge">${cartCount}</span></c:if></a></li>
						<li><a href="<c:url value='/orders'/>">Đơn hàng của tôi</a></li>
					</c:if>
					<li><a href="#">Xin chào, ${sessionScope.user.fullname}</a></li>
					<li><a href="<c:url value='/logout'/>">Đăng xuất</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="<c:url value='/login'/>">Đăng nhập</a></li>
					<li><a href="<c:url value='/register'/>">Đăng ký</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</nav>
