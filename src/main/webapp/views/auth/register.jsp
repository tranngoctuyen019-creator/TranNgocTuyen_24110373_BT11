<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>
    <style>
        .page-wrap { max-width: 560px; margin: 60px auto; padding: 0 20px; }
        .page-header { text-align: center; margin-bottom: 24px; }
        .page-header h1 { font-size: 28px; font-weight: 700; margin: 0; }
        .page-header p { color: var(--muted); margin: 8px 0 0; font-size: 16px; }
        .form-card { background: var(--surface); border: 1px solid var(--border); border-radius: 8px; padding: 36px 38px; }
        .form-group { margin-bottom: 18px; }
        .form-group label { font-weight: 600; color: var(--ink); font-size: 15.5px; margin-bottom: 8px; display: block; }
        .form-control { border: 1px solid var(--border); border-radius: 5px; padding: 12px 15px; font-size: 16px; box-shadow: none; height: auto; }
        .form-control:focus { border-color: var(--accent); box-shadow: none; }
        .role-choice { display: flex; gap: 14px; }
        .role-option { flex: 1; position: relative; }
        .role-option input { position: absolute; opacity: 0; }
        .role-option label { display: flex; align-items: center; gap: 9px; border: 1px solid var(--border); border-radius: 5px; padding: 12px 15px; font-weight: 500; font-size: 15.5px; color: var(--ink); margin: 0; cursor: pointer; }
        .role-option input:checked + label { border-color: var(--accent); background: rgba(47,93,80,.06); color: var(--accent-dark); }
        .btn { border-radius: 5px; font-weight: 600; padding: 13px 24px; border: none; width: 100%; font-size: 16.5px; }
        .btn-accent { background: var(--accent); color: #fff; }
        .btn-accent:hover { background: var(--accent-dark); color: #fff; }
        .alert { border-radius: 5px; font-size: 15px; }
        .bottom-link { text-align: center; margin-top: 20px; font-size: 15px; color: var(--muted); }
        .bottom-link a { color: var(--ink); font-weight: 600; }
    </style>
</head>
<body>
    <div class="page-wrap">
        <div class="page-header">
            <h1>Đăng ký tài khoản</h1>
            <p>Tạo tài khoản mới để bắt đầu sử dụng BookStore</p>
        </div>

        <div class="form-card">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="<c:url value='/register'/>" method="post">
                <div class="form-group">
                    <label>Họ tên</label>
                    <input type="text" name="fullname" class="form-control" value="${fullname}" placeholder="Nhập họ tên">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" class="form-control" value="${email}" placeholder="Nhập email nhận OTP" required>
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="text" name="phone" class="form-control" value="${phone}" placeholder="Nhập số điện thoại">
                </div>
                <div class="form-group">
                    <label>Mật khẩu</label>
                    <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required>
                </div>
                <div class="form-group">
                    <label>Xác nhận mật khẩu</label>
                    <input type="password" name="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu" required>
                </div>
                <div class="form-group">
                    <label>Loại tài khoản</label>
                    <div class="role-choice">
                        <div class="role-option">
                            <input type="radio" id="role-user" name="role" value="user" ${role != 'admin' ? 'checked' : ''}>
                            <label for="role-user">Người dùng</label>
                        </div>
                        <div class="role-option">
                            <input type="radio" id="role-admin" name="role" value="admin" ${role == 'admin' ? 'checked' : ''}>
                            <label for="role-admin">Quản trị viên</label>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-accent" style="margin-top:10px;">Đăng ký</button>
            </form>

            <div class="bottom-link">
                Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a>
            </div>
        </div>
    </div>
</body>
</html>
