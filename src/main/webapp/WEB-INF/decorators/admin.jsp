<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><sitemesh:write property="title"/> | Quản trị - BookStore</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style>
	:root { --ink: #1c1c1c; --muted: #6b6b6b; --border: #dcdcdc; --surface: #ffffff; --accent: #2f5d50; --accent-dark: #24463c; --danger: #a13d3d; }
	* { box-sizing: border-box; }
	body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif; background: #f6f6f4; color: var(--ink); margin: 0; font-size: 16px; line-height: 1.55; }
	.admin-layout { min-height: 100vh; display: flex; }
	.admin-sidebar { flex: 0 0 250px; max-width: 250px; background: var(--accent-dark); color: #fff; min-height: 100vh; padding: 24px 0; }
	.admin-brand { font-size: 19px; font-weight: 700; padding: 0 22px 22px; color: #fff; }
	.admin-brand span { font-weight: 400; opacity: .75; }
	.admin-nav { list-style: none; margin: 0 0 20px; padding: 0; }
	.admin-nav li a { display: block; padding: 13px 22px; color: #dfe8e5; text-decoration: none; font-size: 16px; border-left: 3px solid transparent; }
	.admin-nav li a:hover { background: rgba(255,255,255,.06); color: #fff; text-decoration: none; }
	.admin-nav li a.active { background: rgba(255,255,255,.1); border-left-color: #fff; color: #fff; }
	.admin-sidebar-bottom { padding: 16px 22px 0; border-top: 1px solid rgba(255,255,255,.12); margin-top: 10px; display: flex; flex-direction: column; gap: 10px; }
	.admin-sidebar-bottom a { color: #cfe0da; font-size: 15px; text-decoration: none; }
	.admin-sidebar-bottom a:hover { color: #fff; text-decoration: none; }
	.admin-main { min-width: 0; flex: 1 1 auto; display: flex; flex-direction: column; min-height: 100vh; }
	.admin-topbar { background: #fff; border-bottom: 1px solid var(--border); padding: 16px 30px; display: flex; justify-content: space-between; align-items: center; }
	.admin-topbar-title { font-weight: 600; font-size: 16px; color: var(--muted); }
	.admin-topbar-user { color: var(--ink) !important; font-size: 16px; text-decoration: none; }
	.admin-content { padding: 34px 30px 50px; flex: 1 1 auto; font-size: 16px; }
	.admin-footer { padding: 18px 30px; color: var(--muted); font-size: 14.5px; border-top: 1px solid var(--border); flex: 0 0 auto; }
	@media (max-width: 767px) {
		.admin-layout { flex-direction: column; }
		.admin-sidebar { max-width: 100%; min-height: auto; }
	}
</style>

<sitemesh:write property="head"/>

</head>
<body>
	<div class="admin-layout">
		<%@ include file="/commons/admin/left.jsp"%>
		<div class="admin-main">
			<%@ include file="/commons/admin/header.jsp"%>
			<div class="admin-content">
				<sitemesh:write property="body"/>
			</div>
			<%@ include file="/commons/admin/footer.jsp"%>
		</div>
	</div>
</body>
</html>
