<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><sitemesh:write property="title"/> | BookStore</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style>
	:root { --ink: #1c1c1c; --muted: #6b6b6b; --border: #dcdcdc; --surface: #ffffff; --accent: #2f5d50; --accent-dark: #24463c; --danger: #a13d3d; }
	html, body { height: 100%; }
	body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif; background: #f6f6f4; color: var(--ink); margin: 0; display: flex; flex-direction: column; min-height: 100vh; font-size: 16px; line-height: 1.55; }
	.navbar-custom { background: #fff; border-bottom: 1px solid var(--border); border-radius: 0; box-shadow: none; margin-bottom: 0; flex: 0 0 auto; min-height: 64px; }
	.navbar-custom .navbar-brand { color: var(--ink) !important; font-weight: 700; font-size: 21px; height: auto; padding: 20px 20px; }
	.navbar-custom a { color: var(--muted) !important; font-weight: 500; font-size: 16px; }
	.navbar-custom .navbar-nav > li > a { padding-top: 22px; padding-bottom: 22px; }
	.navbar-custom a:hover, .navbar-custom li.active a { color: var(--ink) !important; }
	.site-main { flex: 1 1 auto; max-width: 1400px; width: 100%; margin: 0 auto; padding: 32px 28px 16px; }
	.site-footer { border-top: 1px solid var(--border); background: #fff; margin-top: 50px; flex: 0 0 auto; }
	.footer-inner { max-width: 1400px; margin: 0 auto; padding: 26px 28px; display: flex; justify-content: space-between; flex-wrap: wrap; gap: 6px; font-size: 15px; color: var(--muted); }
	.footer-muted { color: #9c9c9c; }
	.btn-accent { background: var(--accent); border-color: var(--accent); color: #fff; }
	.btn-accent:hover { background: var(--accent-dark); border-color: var(--accent-dark); color: #fff; }
	.card-book { background: #fff; border: 1px solid var(--border); border-radius: 8px; padding: 16px; height: 100%; }
	.card-book img { width: 100%; height: 260px; object-fit: cover; border-radius: 5px; margin-bottom: 12px; background: #eee; }
	.card-book h4 { font-size: 17px; margin: 6px 0; }
	.card-book h4 a { color: var(--ink); }
	.pagination-bar { text-align: center; margin: 34px 0; font-size: 16px; }
	.pagination-bar a, .pagination-bar span { margin: 0 8px; text-decoration: none; color: var(--muted); }
	.pagination-bar a.current { font-weight: 700; color: var(--ink); }
	.cart-badge { display: inline-block; min-width: 20px; padding: 1px 7px; border-radius: 10px; background: var(--accent); color: #fff; font-size: 12px; font-weight: 700; line-height: 18px; text-align: center; }
	.flash-msg { max-width: 1400px; margin: 0 auto 20px; border-radius: 4px; }
	.author-heading { font-size: 22px; font-weight: 600; margin: 10px 0 20px; padding-bottom: 10px; border-bottom: 2px solid var(--accent); }
</style>

<sitemesh:write property="head"/>

</head>
<body>

	<%@ include file="/commons/web/header.jsp"%>

	<div class="site-main">
		<c:if test="${not empty sessionScope.flashSuccess}">
			<div class="alert alert-success flash-msg"><c:out value="${sessionScope.flashSuccess}"/></div>
			<c:remove var="flashSuccess" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.flashError}">
			<div class="alert alert-danger flash-msg"><c:out value="${sessionScope.flashError}"/></div>
			<c:remove var="flashError" scope="session"/>
		</c:if>
		<sitemesh:write property="body"/>
	</div>

	<%@ include file="/commons/web/footer.jsp"%>

</body>
</html>
