<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị</title>
    <style>
        .page-wrap { width: 100%; max-width: none; margin: 0; box-sizing: border-box; }
        .topbar { display: flex; justify-content: space-between; align-items: flex-end; border-bottom: 1px solid var(--border); padding-bottom: 18px; margin-bottom: 26px; }
        .topbar h1 { font-size: 22px; font-weight: 600; margin: 0; }
        .topbar p { margin: 4px 0 0; color: var(--muted); font-size: 14px; }
        .stat-grid { display: flex; flex-wrap: wrap; gap: 18px; margin-bottom: 20px; }
        .stat-card { flex: 1 1 220px; background: var(--surface); border: 1px solid var(--border); padding: 22px 24px; }
        .stat-card .stat-label { font-size: 13px; color: var(--muted); margin-bottom: 8px; }
        .stat-card .stat-value { font-size: 28px; font-weight: 700; color: var(--ink); }
        .stat-card a.stat-link { display: inline-block; margin-top: 12px; font-size: 13.5px; color: var(--accent); text-decoration: none; font-weight: 500; }
        .stat-card a.stat-link:hover { color: var(--accent-dark); text-decoration: underline; }
    </style>
</head>
<body>
    <div class="page-wrap">
        <div class="topbar">
            <div>
                <h1>Chào mừng đến trang quản trị</h1>
                <p>Đây là trang chủ khu vực quản trị.</p>
            </div>
        </div>

        <div class="stat-grid">
            <div class="stat-card">
                <div class="stat-label">Tổng số sách hiện có</div>
                <div class="stat-value">${totalBooks}</div>
                <a class="stat-link" href="<c:url value='/admin/book/list'/>">Quản lý sách &rarr;</a>
            </div>
        </div>
    </div>
</body>
</html>
