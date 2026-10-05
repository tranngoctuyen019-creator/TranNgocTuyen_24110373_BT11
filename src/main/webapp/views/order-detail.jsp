<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đơn hàng #${order.orderId}</title>
<style>
	.page-wrap { max-width: 1000px; margin: 0 auto; padding: 24px 20px 60px; }
	.page-wrap h1 { font-size: 26px; font-weight: 700; margin: 0 0 20px; }
	.panel { background: var(--surface); border: 1px solid var(--border); padding: 24px 28px; margin-bottom: 22px; }
	.panel h2 { font-size: 18px; font-weight: 700; margin: 0 0 14px; padding-bottom: 10px; border-bottom: 1px solid var(--border); }
	.info-row { display: flex; padding: 6px 0; font-size: 16px; }
	.info-row .label { flex: 0 0 190px; color: var(--muted); }
	.items-table { width: 100%; border-collapse: collapse; }
	.items-table th { text-align: left; font-size: 14.5px; color: var(--muted); font-weight: 600; padding: 10px 8px; border-bottom: 1px solid var(--border); }
	.items-table td { padding: 12px 8px; border-bottom: 1px solid #eee; font-size: 16px; }
	.items-table .num { text-align: right; white-space: nowrap; }
	.items-table th.num { text-align: right; }
	.total-row { display: flex; justify-content: space-between; padding-top: 16px; font-size: 20px; font-weight: 700; }
	.success-box { background: #eef6f2; border: 1px solid #b9d6c9; color: var(--accent-dark); padding: 16px 20px; margin-bottom: 22px; border-radius: 4px; font-size: 16px; }
	.back-link { color: var(--muted); font-size: 15px; }
</style>
</head>
<body>
	<div class="page-wrap">
		<h1>Đơn hàng #${order.orderId}</h1>

		<c:if test="${param.success == '1'}">
			<div class="success-box">
				<b>Cảm ơn bạn đã đặt hàng!</b> Đơn hàng sẽ được giao đến địa chỉ bên dưới. Bạn thanh toán bằng tiền mặt khi nhận hàng (COD).
			</div>
		</c:if>

		<div class="panel">
			<h2>Thông tin đơn hàng</h2>
			<div class="info-row"><span class="label">Ngày đặt</span><span>${order.createdAtText}</span></div>
			<div class="info-row"><span class="label">Trạng thái</span><span>${order.statusText}</span></div>
			<div class="info-row"><span class="label">Thanh toán</span><span>${order.paymentMethodText}</span></div>
			<div class="info-row"><span class="label">Người nhận</span><span><c:out value="${order.receiverName}"/></span></div>
			<div class="info-row"><span class="label">Số điện thoại</span><span><c:out value="${order.phone}"/></span></div>
			<div class="info-row"><span class="label">Địa chỉ giao hàng</span><span><c:out value="${order.address}"/></span></div>
			<c:if test="${not empty order.note}">
				<div class="info-row"><span class="label">Ghi chú</span><span><c:out value="${order.note}"/></span></div>
			</c:if>
		</div>

		<div class="panel">
			<h2>Sản phẩm</h2>
			<table class="items-table">
				<thead>
					<tr><th>Sách</th><th class="num">Đơn giá</th><th class="num">SL</th><th class="num">Thành tiền</th></tr>
				</thead>
				<tbody>
					<c:forEach items="${order.items}" var="it">
						<tr>
							<td>
								<c:choose>
									<c:when test="${not empty it.bookid}">
										<a href="<c:url value='/book-detail'><c:param name='id' value='${it.bookid}'/></c:url>"><c:out value="${it.title}"/></a>
									</c:when>
									<c:otherwise><c:out value="${it.title}"/></c:otherwise>
								</c:choose>
							</td>
							<td class="num"><fmt:formatNumber value="${it.unitPrice}" type="number" groupingUsed="true"/> đ</td>
							<td class="num">${it.quantity}</td>
							<td class="num"><fmt:formatNumber value="${it.subtotal}" type="number" groupingUsed="true"/> đ</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="total-row">
				<span>Tổng cộng</span>
				<span><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> đ</span>
			</div>
		</div>

		<a class="back-link" href="<c:url value='/orders'/>">&larr; Danh sách đơn hàng</a>
		&nbsp;&nbsp;
		<a class="back-link" href="<c:url value='/products'/>">Tiếp tục mua sắm</a>
	</div>
</body>
</html>
