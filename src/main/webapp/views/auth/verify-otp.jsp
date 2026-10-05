<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác thực OTP</title>
    <style>
        .page-wrap { max-width: 480px; margin: 60px auto; padding: 0 20px; }
        .page-header { text-align: center; margin-bottom: 22px; }
        .page-header h1 { font-size: 26px; font-weight: 600; margin: 0; }
        .page-header p { color: var(--muted); margin: 6px 0 0; font-size: 15px; }
        .form-card { background: var(--surface); border: 1px solid var(--border); padding: 32px 34px; }
        .form-group label { font-weight: 500; color: var(--ink); font-size: 15px; margin-bottom: 7px; }
        .form-control { border: 1px solid var(--border); border-radius: 4px; padding: 11px 14px; font-size: 24px; text-align: center; letter-spacing: 6px; }
        .btn { border-radius: 4px; font-weight: 500; padding: 12px 24px; border: none; width: 100%; font-size: 16px; }
        .btn-accent { background: var(--accent); color: #fff; }
        .btn-accent:hover { background: var(--accent-dark); color: #fff; }
        .alert { border-radius: 4px; }
        .bottom-link { text-align: center; margin-top: 18px; font-size: 14.5px; color: var(--muted); }
        .bottom-link a { color: var(--ink); font-weight: 500; }
    </style>
</head>
<body>
    <div class="page-wrap">
        <div class="page-header">
            <h1>Xác thực OTP</h1>
            <p>Một mã OTP đã được gửi tới email <b>${email}</b>. Vui lòng kiểm tra hộp thư (và cả mục spam).</p>
        </div>

        <div class="form-card">
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert alert-success">${message}</div>
            </c:if>

            <form action="<c:url value='/verify-otp'/>" method="post">
                <div class="form-group">
                    <label>Mã OTP</label>
                    <input type="text" name="otp" class="form-control" maxlength="6" placeholder="------" required autofocus>
                </div>
                <button type="submit" class="btn btn-accent" style="margin-top:10px;">Xác thực &amp; hoàn tất đăng ký</button>
            </form>

            <div class="bottom-link">
                Không nhận được mã? <a href="<c:url value='/resend-otp'/>">Gửi lại mã OTP</a>
            </div>
        </div>
    </div>
</body>
</html>
