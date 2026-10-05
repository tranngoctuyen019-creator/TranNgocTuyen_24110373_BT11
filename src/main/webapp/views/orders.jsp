<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đơn hàng của tôi</title>
<style>
	.page-wrap { max-width: 1180px; margin: 0 auto; padding: 24px 20px 60px; }
	.page-wrap h1 { font-size: 26px; font-weight: 700; margin: 0 0 20px; }
	.table-wrap { background: var(--surface); border: 1px solid var(--border); overflow-x: auto; }
	.orders-table { width: 100%; border-collapse: collapse; }
	.orders-table th { text-align: left; font-size: 14.5px; color: var(--muted); font-weight: 600; padding: 14px 16px; border-bottom: 1px solid var(--border); white-space: nowrap; }
	.orders-table td { padding: 14px 16px; border-bottom: 1px solid #eee; font-size: 16px; }
	.orders-table tr:last-child td { border-bottom: none; }
	.orders-table a { color: var(--accent); font-weight: 600; }
	.empty-box { background: var(--surface); border: 1px solid var(--border); padding: 50px 20px; text-align: center; color: var(--muted); font-size: 17px; }
</style>
</head>
<body>
	<div class="page-wrap">
		<h1>Đơn hàng của tôi</h1>
		<c:choose>
			<c:when test="${empty orders}">
				<div class="empty-box">Bạn chưa có đơn hàng nào. <a href="<c:url value='/products'/>">Mua sắm ngay</a></div>
			</c:when>
			<c:otherwise>
				<div class="table-wrap">
					<table class="orders-table">
						<thead>
							<tr><th>Mã đơn</th><th>Ngày đặt</th><th>Tổng tiền</th><th>Thanh toán</th><th>Trạng thái</th><th></th></tr>
						</thead>
						<tbody>
							<c:forEach items="${orders}" var="o">
								<tr>
									<td>#${o.orderId}</td>
									<td>${o.createdAtText}</td>
									<td><b><fmt:formatNumber value="${o.totalAmount}" type="number" groupingUsed="true"/> đ</b></td>
									<td>${o.paymentMethodText}</td>
									<td>${o.statusText}</td>
									<td><a href="<c:url value='/order/detail'><c:param name='id' value='${o.orderId}'/></c:url>">Chi tiết</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
