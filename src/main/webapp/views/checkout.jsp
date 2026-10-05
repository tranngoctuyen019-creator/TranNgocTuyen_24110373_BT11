```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>

    <style>
        .page-wrap {
            max-width: 1180px;
            margin: 0 auto;
            padding: 24px 20px 60px;
        }

        .page-wrap h1 {
            font-size: 26px;
            font-weight: 700;
            margin: 0 0 20px;
        }

        .checkout-layout {
            display: flex;
            gap: 28px;
            align-items: flex-start;
            flex-wrap: wrap;
        }

        .panel {
            background: var(--surface);
            border: 1px solid var(--border);
            padding: 26px 28px;
        }

        .panel h2 {
            font-size: 19px;
            font-weight: 700;
            margin: 0 0 18px;
            padding-bottom: 10px;
            border-bottom: 1px solid var(--border);
        }

        .form-panel {
            flex: 1 1 520px;
        }

        .summary-panel {
            flex: 0 0 380px;
        }

        .form-group label {
            font-weight: 500;
            font-size: 15px;
            margin-bottom: 7px;
        }

        .form-control {
            border: 1px solid var(--border);
            border-radius: 4px;
            padding: 10px 12px;
            font-size: 15px;
            box-shadow: none;
            height: auto;
        }

        .form-control:focus {
            border-color: var(--accent);
            box-shadow: none;
        }

        .pay-method {
            display: flex;
            align-items: center;
            gap: 12px;
            border: 1px solid var(--accent);
            background: #f1f6f4;
            border-radius: 4px;
            padding: 14px 16px;
        }

        .pay-method b {
            display: block;
        }

        .pay-method small {
            color: var(--muted);
        }

        .sum-item {
            display: flex;
            justify-content: space-between;
            gap: 12px;
            padding: 9px 0;
            border-bottom: 1px solid #eee;
            font-size: 15px;
        }

        .sum-item .name {
            flex: 1;
        }

        .sum-item .qty {
            color: var(--muted);
        }

        .sum-total {
            display: flex;
            justify-content: space-between;
            padding-top: 16px;
            font-size: 20px;
            font-weight: 700;
        }

        .checkout-button {
            width: 100%;
            margin-top: 20px;
        }

        .btn-accent {
            background: var(--accent);
            border-color: var(--accent);
            color: #fff;
            border-radius: 4px;
            font-weight: 600;
            padding: 12px 22px;
            font-size: 16px;
        }

        .btn-accent:hover {
            background: var(--accent-dark);
            border-color: var(--accent-dark);
            color: #fff;
        }

        .btn-accent[disabled] {
            opacity: .5;
        }

        .note-small {
            color: var(--muted);
            font-size: 14px;
            margin-top: 12px;
        }

        .back-link {
            display: block;
            color: var(--muted);
            font-size: 15px;
            margin-top: 14px;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="page-wrap">

    <h1>Thanh toán đơn hàng</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            <c:out value="${error}"/>
        </div>
    </c:if>

    <c:if test="${hasStockProblem}">
        <div class="alert alert-warning">
            Có sách trong giỏ vượt quá tồn kho. Vui lòng
            <a href="<c:url value='/cart'/>">quay lại giỏ hàng</a>
            để điều chỉnh.
        </div>
    </c:if>

    <div class="checkout-layout">

        <div class="panel form-panel">

            <h2>Thông tin giao hàng</h2>

            <form id="checkoutForm"
                  action="<c:url value='/checkout'/>"
                  method="post">

                <c:forEach items="${cartItems}" var="sel">
                    <input type="hidden"
                           name="ids"
                           value="${sel.bookid}">
                </c:forEach>

                <div class="form-group">
                    <label for="receiverName">Họ tên người nhận *</label>
                    <input
                        type="text"
                        id="receiverName"
                        name="receiverName"
                        class="form-control"
                        maxlength="100"
                        value="<c:out value='${sessionScope.user.fullname}'/>"
                        required>
                </div>

                <div class="form-group">
                    <label for="phone">Số điện thoại *</label>
                    <input
                        type="tel"
                        id="phone"
                        name="phone"
                        class="form-control"
                        maxlength="20"
                        value="0<c:out value='${sessionScope.user.phone}'/>"
                        required>
                </div>

                <div class="form-group">
                    <label for="address">Địa chỉ giao hàng *</label>
                    <textarea
                        id="address"
                        name="address"
                        class="form-control"
                        rows="3"
                        maxlength="255"
                        placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố"
                        required><c:out value="${address}"/></textarea>
                </div>

                <div class="form-group">
                    <label for="note">Ghi chú (không bắt buộc)</label>
                    <textarea
                        id="note"
                        name="note"
                        class="form-control"
                        rows="2"
                        maxlength="500"><c:out value="${note}"/></textarea>
                </div>

                <h2 style="margin-top:26px;">
                    Phương thức thanh toán
                </h2>

                <div class="pay-method">
                    <input type="radio" checked disabled>

                    <div>
                        <b>Thanh toán khi nhận hàng (COD)</b>
                    </div>
                </div>

                <div style="margin-top:24px;">
                    <a class="back-link"
                       href="<c:url value='/cart'/>">
                        &larr; Quay lại giỏ hàng
                    </a>
                </div>

            </form>

        </div>

        <div class="panel summary-panel">

            <h2>Đơn hàng của bạn</h2>

            <c:forEach items="${cartItems}" var="item">
                <div class="sum-item">

                    <span class="name">
                        <c:out value="${item.title}"/>
                        <span class="qty">
                            &times; ${item.quantity}
                        </span>
                    </span>

                    <span>
                        <fmt:formatNumber
                            value="${item.subtotal}"
                            type="number"
                            groupingUsed="true"/> đ
                    </span>

                </div>
            </c:forEach>

            <div class="sum-total">
                <span>Tổng cộng</span>

                <span>
                    <fmt:formatNumber
                        value="${cartTotal}"
                        type="number"
                        groupingUsed="true"/> đ
                </span>
            </div>

            <button
                type="submit"
                form="checkoutForm"
                class="btn btn-accent checkout-button"
                ${hasStockProblem ? 'disabled' : ''}>
                Đặt hàng
            </button>
        </div>

    </div>

</div>

</body>
</html>
```
