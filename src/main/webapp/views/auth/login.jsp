<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <style>
        .page-wrap { max-width: 480px; margin: 60px auto; padding: 0 20px; }
        .page-header { text-align: center; margin-bottom: 22px; }
        .page-header h1 { font-size: 26px; font-weight: 600; margin: 0; }
        .page-header p { color: var(--muted); margin: 6px 0 0; font-size: 15px; }
        .form-card { background: var(--surface); border: 1px solid var(--border); padding: 28px 30px; }
        .form-group label { font-weight: 500; color: var(--ink); font-size: 15px; margin-bottom: 7px; }
        .form-control { border: 1px solid var(--border); border-radius: 4px; padding: 11px 14px; font-size: 16px; box-shadow: none; }
        .form-control:focus { border-color: var(--accent); box-shadow: none; }
        .btn { border-radius: 4px; font-weight: 500; padding: 12px 24px; border: none; width: 100%; font-size: 16px; }
        .btn-accent { background: var(--accent); color: #fff; }
        .btn-accent:hover { background: var(--accent-dark); color: #fff; }
        .alert { border-radius: 4px; }
        .bottom-link { text-align: center; margin-top: 16px; font-size: 14.5px; color: var(--muted); }
        .bottom-link a { color: var(--ink); font-weight: 500; }
    </style>
</head>
<body>
    <div class="page-wrap">
        <div class="page-header">
            <h1>Đăng nhập</h1>
            <p>Đăng nhập để tiếp tục sử dụng BookStore</p>
        </div>

        <div class="form-card">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${param.error == 'forbidden'}">
                <div class="alert alert-warning">Bạn không có quyền truy cập trang đó.</div>
            </c:if>

            <form action="<c:url value='/login'/>" method="post">
                <input type="hidden" name="redirect" value="${param.redirect}">
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" class="form-control" value="${email}" required>
                </div>
                <div class="form-group">
                    <label>Mật khẩu</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-accent">Đăng nhập</button>
            </form>

            <div class="bottom-link">
                Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký</a>
            </div>
        </div>
    </div>
</body>
</html>
