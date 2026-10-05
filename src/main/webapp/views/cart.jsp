<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Giỏ hàng</title>
<style>
	.page-wrap { max-width: 1180px; margin: 0 auto; padding: 24px 20px 60px; }
	.page-wrap h1 { font-size: 26px; font-weight: 700; margin: 0 0 20px; }
	.cart-layout { display: flex; gap: 28px; align-items: flex-start; flex-wrap: wrap; }
	.cart-main { flex: 1 1 700px; min-width: 0; }
	.cart-toolbar { display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px; background: var(--surface); border: 1px solid var(--border); border-bottom: none; padding: 12px 16px; }
	.check-all { margin: 0; font-weight: 600; font-size: 15.5px; cursor: pointer; display: flex; align-items: center; gap: 8px; }
	.cart-toolbar .btn { border-radius: 4px; font-weight: 500; }
	.cart-toolbar .btn[disabled] { opacity: .45; cursor: not-allowed; }
	.cart-table-wrap { background: var(--surface); border: 1px solid var(--border); overflow-x: auto; }
	.cart-table { width: 100%; border-collapse: collapse; }
	.cart-table th { text-align: left; font-size: 14.5px; color: var(--muted); font-weight: 600; padding: 14px 16px; border-bottom: 1px solid var(--border); white-space: nowrap; }
	.cart-table td { padding: 16px; border-bottom: 1px solid #eee; vertical-align: middle; font-size: 16px; }
	.cart-table tr:last-child td { border-bottom: none; }
	.cart-table .col-check { width: 44px; padding-right: 0; }
	.cart-table input[type=checkbox], .check-all input[type=checkbox] { width: 18px; height: 18px; margin: 0; cursor: pointer; accent-color: var(--accent); }
	.item-cell { display: flex; align-items: center; gap: 14px; min-width: 260px; }
	.item-cell img { width: 64px; height: 86px; object-fit: cover; border: 1px solid var(--border); background: #eee; }
	.item-title { font-weight: 600; color: var(--ink); line-height: 1.35; }
	.item-title:hover { color: var(--accent); text-decoration: none; }
	.item-stock { font-size: 13.5px; color: var(--muted); margin-top: 4px; }
	.item-stock.bad { color: var(--danger); font-weight: 600; }
	.qty-form { display: inline-flex; align-items: center; border: 1px solid var(--border); border-radius: 4px; overflow: hidden; background: #fff; }
	.qty-btn { border: none; background: #f2f2f0; width: 34px; height: 38px; font-size: 18px; cursor: pointer; color: var(--ink); }
	.qty-btn:hover:not([disabled]) { background: #e6e6e3; }
	.qty-btn[disabled] { color: #bbb; cursor: not-allowed; }
	.qty-input { width: 58px; height: 38px; border: none; border-left: 1px solid var(--border); border-right: 1px solid var(--border); text-align: center; font-size: 16px; -moz-appearance: textfield; }
	.qty-input::-webkit-outer-spin-button, .qty-input::-webkit-inner-spin-button { -webkit-appearance: none; margin: 0; }
	.btn-link-danger { background: none; border: none; color: var(--danger); font-size: 15px; cursor: pointer; padding: 0; }
	.btn-link-danger:hover { text-decoration: underline; }
	.summary { flex: 0 0 320px; background: var(--surface); border: 1px solid var(--border); padding: 24px; }
	.summary h2 { font-size: 19px; font-weight: 700; margin: 0 0 16px; }
	.summary-row { display: flex; justify-content: space-between; padding: 8px 0; font-size: 16px; }
	.summary-total { border-top: 1px solid var(--border); margin-top: 8px; padding-top: 16px; font-size: 20px; font-weight: 700; }
	.summary .btn { display: block; width: 100%; margin-top: 18px; padding: 12px; font-size: 16px; font-weight: 600; border-radius: 4px; }
	.btn-accent { background: var(--accent); border-color: var(--accent); color: #fff; }
	.btn-accent:hover { background: var(--accent-dark); border-color: var(--accent-dark); color: #fff; }
	.btn-accent[disabled] { opacity: .5; cursor: not-allowed; }
	.warn-box { display: none; background: #fbeeee; border: 1px solid #e6c4c4; color: var(--danger); padding: 10px 12px; font-size: 14.5px; margin-top: 14px; border-radius: 4px; }
	.cart-actions { margin-top: 16px; }
	.cart-actions a { color: var(--muted); font-size: 15px; }
	.empty-box { background: var(--surface); border: 1px solid var(--border); padding: 60px 20px; text-align: center; color: var(--muted); font-size: 17px; }
	.empty-box .btn { margin-top: 18px; }
</style>
</head>
<body>
	<div class="page-wrap">
		<h1>Giỏ hàng của bạn</h1>

		<c:choose>
			<c:when test="${empty cartItems}">
				<div class="empty-box">
					Giỏ hàng đang trống.<br>
					<a class="btn btn-accent" href="<c:url value='/products'/>">Xem sản phẩm</a>
				</div>
			</c:when>
			<c:otherwise>
				<form id="selForm" method="post" action="<c:url value='/cart/remove-selected'/>"></form>
				<span id="sepProbe" style="display:none"><fmt:formatNumber value="1000" type="number" groupingUsed="true"/></span>

				<div class="cart-layout">
					<div class="cart-main">
						<div class="cart-toolbar">
							<label class="check-all"><input type="checkbox" id="checkAll"> Chọn tất cả (<span id="selCount">0</span>/ ${fn:length(cartItems)} )</label>
							<div>
								<button type="submit" form="selForm" formaction="<c:url value='/cart/remove-selected'/>" formmethod="post" id="delSelBtn" class="btn btn-default btn-sm" onclick="return confirm('Xóa các sản phẩm đã chọn?');">Xóa đã chọn</button>
								<button type="submit" form="selForm" formaction="<c:url value='/cart/clear'/>" formmethod="post" class="btn btn-danger btn-sm" onclick="return confirm('Xóa tất cả sản phẩm trong giỏ hàng?');">Xóa tất cả</button>
							</div>
						</div>

						<div class="cart-table-wrap">
							<table class="cart-table">
								<thead>
									<tr>
										<th class="col-check"></th>
										<th>Sản phẩm</th>
										<th>Đơn giá</th>
										<th>Số lượng</th>
										<th>Thành tiền</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${cartItems}" var="item">
										<tr>
											<td class="col-check">
												<input type="checkbox" class="row-check" form="selForm" name="ids" value="${item.bookid}" data-subtotal="${item.subtotal}" data-over="${item.overStock}" ${item.overStock ? '' : 'checked'}>
											</td>
											<td>
												<div class="item-cell">
													<img src="<c:url value='${not empty item.coverImage ? item.coverImage : "https://placehold.co/64x86?text=No+Cover"}'/>" alt="<c:out value='${item.title}'/>">
													<div>
														<a class="item-title" href="<c:url value='/book-detail'><c:param name='id' value='${item.bookid}'/></c:url>"><c:out value="${item.title}"/></a>
														<c:choose>
															<c:when test="${item.overStock}">
																<div class="item-stock bad">
																	<c:choose>
																		<c:when test="${item.stock <= 0}">Đã hết hàng</c:when>
																		<c:otherwise>Chỉ còn ${item.stock} cuốn trong kho</c:otherwise>
																	</c:choose>
																	&mdash; vui lòng giảm số lượng hoặc xóa
																</div>
															</c:when>
															<c:otherwise>
																<div class="item-stock">Còn ${item.stock} cuốn</div>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</td>
											<td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> đ</td>
											<td>
												<form action="<c:url value='/cart/update'/>" method="post" class="qty-form">
													<input type="hidden" name="bookid" value="${item.bookid}">
													<button type="button" class="qty-btn" onclick="stepQty(this, -1)" ${item.quantity <= 1 ? 'disabled' : ''}>&minus;</button>
													<input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.stock}" class="qty-input" onchange="saveSel(); this.form.submit()">
													<button type="button" class="qty-btn" onclick="stepQty(this, 1)" ${item.quantity >= item.stock ? 'disabled' : ''}>+</button>
													<noscript><button type="submit">Cập nhật</button></noscript>
												</form>
											</td>
											<td><b><fmt:formatNumber value="${item.subtotal}" type="number" groupingUsed="true"/> đ</b></td>
											<td>
												<form action="<c:url value='/cart/remove'/>" method="post" onsubmit="if (!confirm('Xóa sách này khỏi giỏ hàng?')) return false; saveSel();">
													<input type="hidden" name="bookid" value="${item.bookid}">
													<button type="submit" class="btn-link-danger">Xóa</button>
												</form>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

						<div class="cart-actions">
							<a href="<c:url value='/products'/>">&larr; Tiếp tục mua sắm</a>
						</div>
					</div>

					<div class="summary">
						<h2>Tóm tắt đơn hàng</h2>
						<div class="summary-row"><span>Sản phẩm đã chọn</span><span id="selCount2">0</span></div>
						<div class="summary-row summary-total">
							<span>Tổng cộng</span>
							<span id="selTotal">0 đ</span>
						</div>
						<div class="warn-box" id="stockWarn">Có sách đã chọn vượt quá tồn kho. Hãy giảm số lượng hoặc bỏ chọn để tiếp tục thanh toán.</div>
						<button type="submit" form="selForm" formaction="<c:url value='/checkout'/>" formmethod="get" id="checkoutBtn" class="btn btn-accent">Thanh toán (COD)</button>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<script>
		var checks = Array.prototype.slice.call(document.querySelectorAll('.row-check'));

		function saveSel() {
			try {
				var unchecked = checks.filter(function (c) { return !c.checked; }).map(function (c) { return c.value; });
				sessionStorage.setItem('cartUnchecked', JSON.stringify(unchecked));
			} catch (e) {}
		}

		function stepQty(btn, delta) {
			var form = btn.form;
			var input = form.elements['quantity'];
			var current = parseInt(input.value, 10) || 1;
			var min = parseInt(input.min, 10) || 1;
			var max = parseInt(input.max, 10);
			var next = current + delta;
			if (next < min) next = min;
			if (!isNaN(max) && next > max) next = max;
			if (next === current) return;
			input.value = next;
			saveSel();
			form.submit();
		}

		(function () {
			if (!checks.length) return;
			var checkAll = document.getElementById('checkAll');
			var probe = document.getElementById('sepProbe').textContent.trim();
			var sep = probe.length >= 5 ? probe.charAt(1) : '';

			function fmt(n) {
				return String(Math.round(n)).replace(/\B(?=(\d{3})+(?!\d))/g, sep);
			}

			function refresh() {
				var count = 0, total = 0, over = false;
				checks.forEach(function (c) {
					if (c.checked) {
						count++;
						total += parseFloat(c.getAttribute('data-subtotal')) || 0;
						if (c.getAttribute('data-over') === 'true') over = true;
					}
				});
				checkAll.checked = count === checks.length;
				checkAll.indeterminate = count > 0 && count < checks.length;
				document.getElementById('selCount').textContent = count;
				document.getElementById('selCount2').textContent = count;
				document.getElementById('selTotal').textContent = fmt(total) + ' đ';
				document.getElementById('checkoutBtn').disabled = count === 0 || over;
				document.getElementById('delSelBtn').disabled = count === 0;
				document.getElementById('stockWarn').style.display = over ? 'block' : 'none';
			}

			try {
				var saved = JSON.parse(sessionStorage.getItem('cartUnchecked') || 'null');
				if (saved) {
					checks.forEach(function (c) { if (saved.indexOf(c.value) >= 0) c.checked = false; });
					sessionStorage.removeItem('cartUnchecked');
				}
			} catch (e) {}

			checkAll.addEventListener('change', function () {
				checks.forEach(function (c) { c.checked = checkAll.checked; });
				refresh();
			});
			checks.forEach(function (c) { c.addEventListener('change', refresh); });
			refresh();
		})();
	</script>
</body>
</html>
