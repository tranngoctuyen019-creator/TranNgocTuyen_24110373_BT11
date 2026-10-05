<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
<style>
	.home-wrap { max-width: 900px; margin: 0 auto; padding: 30px 24px 70px; }
	.home-wrap h1 { font-size: 30px; font-weight: 700; margin: 0 0 14px; }
	.home-wrap p { font-size: 16px; color: var(--ink); line-height: 1.7; margin: 0 0 16px; }
	.home-wrap .lead { font-size: 18px; color: var(--muted); }
	.home-wrap h2 { font-size: 20px; font-weight: 700; margin: 32px 0 12px; padding-bottom: 8px; border-bottom: 2px solid var(--accent); }
	.home-wrap ul { padding-left: 22px; margin: 0 0 16px; }
	.home-wrap ul li { font-size: 16px; margin-bottom: 8px; line-height: 1.6; }
	.home-cta { margin-top: 28px; }
	.home-cta a { display: inline-block; background: var(--accent); color: #fff; text-decoration: none; font-weight: 600; padding: 12px 22px; border-radius: 5px; }
	.home-cta a:hover { background: var(--accent-dark); text-decoration: none; color: #fff; }
</style>
</head>
<body>
	<div class="home-wrap">
		<h1>Chào mừng đến với BookStore</h1>
		<p class="lead">Hiệu sách trực tuyến với đa dạng đầu sách đến từ nhiều tác giả trong và ngoài nước.</p>

		<p>
			Trần Ngọc Tuyên - 24110373 - Đề 02: BookStore
		</p>

		<div class="home-cta">
			<a href="<c:url value='/products'/>">Xem sản phẩm ngay</a>
		</div>
	</div>
</body>
</html>
