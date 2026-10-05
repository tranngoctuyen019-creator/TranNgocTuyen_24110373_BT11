<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${book.title}</title>
<style>
	.page-wrap { max-width: 1180px; margin: 0 auto; padding: 40px 20px 60px; }
	.detail-card { background: var(--surface); border: 1px solid var(--border); padding: 40px; display: flex; gap: 48px; flex-wrap: wrap; }
	.detail-img { flex: 1 1 300px; max-width: 320px; }
	.detail-img img { width: 100%; border: 1px solid var(--border); object-fit: cover; max-height: 460px; display: block; }
	.detail-info { flex: 1.4 1 460px; display: flex; flex-direction: column; }
	.detail-info h1 { font-size: 32px; color: #000; font-weight: 600; margin: 0 0 18px; line-height: 1.3; }
	.meta-row { border-top: 1px solid var(--border); padding: 16px 0; font-size: 18px; color: #000; }
	.meta-row span.label { color: #000; font-weight: 600; display: inline-block; min-width: 170px; }
	.price-row { border-top: 1px solid var(--border); border-bottom: 1px solid var(--border); padding: 18px 0; margin-bottom: 6px; }
	.price-row .p-price { font-size: 30px; color: #000; font-weight: 600; }
	.desc-block { padding-top: 16px; margin-top: auto; }
	.desc-block h3 { font-size: 19px; font-weight: 700; color: #000; margin: 0 0 10px; }
	.detail-info .p-desc { font-size: 18px; line-height: 1.8; color: #000; }
	.buy-row { padding: 18px 0; }
	.buy-form { display: flex; align-items: center; gap: 12px; flex-wrap: wrap; }
	.buy-form label { font-weight: 600; font-size: 16px; margin: 0; }
	.buy-form .qty-input { width: 90px; text-align: center; }
	.buy-hint { color: var(--muted); font-size: 14.5px; }
	.out-of-stock { display: inline-block; padding: 8px 16px; border: 1px solid var(--danger); color: var(--danger); font-weight: 600; border-radius: 4px; }
	.back-link { display: inline-block; margin-top: 22px; color: var(--muted); font-size: 13.5px; text-decoration: none; }
	.back-link:hover { color: var(--ink); }

	.reviews-section { max-width: 1180px; margin: 40px auto 0; padding: 0 20px; }
	.section-title { font-weight: 600; font-size: 18px; color: var(--ink); margin: 0 0 16px; border-bottom: 1px solid var(--border); padding-bottom: 10px; }
	.review-item { padding: 14px 0; border-bottom: 1px solid #eee; font-size: 14.5px; }
	.review-item b { color: var(--ink); }
	.empty-box { background: var(--surface); border: 1px solid var(--border); padding: 24px; text-align: center; color: var(--muted); }

	.form-card { background: var(--surface); border: 1px solid var(--border); padding: 26px 30px; max-width: 560px; margin-top: 18px; }
	.form-group label { font-weight: 500; color: var(--ink); font-size: 15px; margin-bottom: 7px; }
	.form-control { border: 1px solid var(--border); border-radius: 4px; padding: 9px 12px; font-size: 15px; box-shadow: none; }
	.form-control:focus { border-color: var(--accent); box-shadow: none; }
	.btn-accent { background: var(--accent); border-color: var(--accent); color: #fff; border-radius: 4px; font-weight: 500; padding: 10px 22px; }
	.btn-accent:hover { background: var(--accent-dark); border-color: var(--accent-dark); color: #fff; }
</style>
</head>
<body>
	<div class="page-wrap">
		<div class="detail-card">
			<div class="detail-img">
				<img src="<c:url value='${not empty book.coverImage ? book.coverImage : "https://placehold.co/300x400?text=No+Cover"}'/>" alt="${book.title}">
			</div>
			<div class="detail-info">
				<h1>${book.title}</h1>

				<div class="meta-row"><span class="label">Mã isbn:</span> ${book.isbn}</div>
				<div class="meta-row"><span class="label">Tác giả:</span> ${book.authorNames}</div>
				<div class="meta-row"><span class="label">Publisher:</span> ${book.publisher}</div>
				<div class="meta-row"><span class="label">Publisher_date:</span> ${book.publishDate.dayOfMonth}/${book.publishDate.monthValue}/${book.publishDate.year}</div>
				<div class="meta-row"><span class="label">Quantity:</span> ${book.quantity}</div>

				<div class="price-row">
					<span class="p-price"><fmt:formatNumber value="${book.price}" type="number" groupingUsed="true"/> đ</span>
				</div>

				<c:if test="${empty sessionScope.user or not sessionScope.user.admin}">
					<div class="buy-row">
						<c:choose>
							<c:when test="${not empty book.quantity and book.quantity > 0}">
								<form action="<c:url value='/cart/add'/>" method="post" class="buy-form">
									<input type="hidden" name="bookid" value="${book.bookid}">
									<input type="hidden" name="returnUrl" value="/book-detail?id=${book.bookid}">
									<label for="qty">Số lượng</label>
									<input type="number" id="qty" name="quantity" value="1" min="1" max="${book.quantity}" class="form-control qty-input" required>
									<button type="submit" class="btn btn-accent">Thêm vào giỏ hàng</button>
									<span class="buy-hint">(còn ${book.quantity} cuốn)</span>
								</form>
							</c:when>
							<c:otherwise>
								<span class="out-of-stock">Hết hàng</span>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>

				<c:if test="${not empty book.description}">
					<div class="desc-block">
						<h3>Mô tả</h3>
						<div class="p-desc">${book.description}</div>
					</div>
				</c:if>
			</div>
		</div>
		<a class="back-link" href="<c:url value='/products'/>">&larr; Quay lại danh sách sản phẩm</a>
	</div>

	<div class="reviews-section">
		<div class="section-title">Reviews (${fn:length(reviews)})</div>

		<c:choose>
			<c:when test="${empty reviews}">
				<div class="empty-box">Chưa có đánh giá nào cho cuốn sách này.</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${reviews}" var="rv">
					<div class="review-item">
						<b>${rv.userFullname}</b>
						<c:if test="${not empty rv.rating}"> &mdash; ${rv.rating} / 5 sao</c:if>
						: ${rv.reviewText}
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<h4 style="margin-top:24px;">Form thêm review</h4>
		<c:choose>
			<c:when test="${not empty sessionScope.user}">
				<div class="form-card">
					<form action="<c:url value='/review/add'/>" method="post">
						<input type="hidden" name="bookid" value="${book.bookid}">
						<div class="form-group">
							<label>Đánh giá (1-5 sao)</label>
							<select name="rating" class="form-control">
								<option value="5">5</option>
								<option value="4">4</option>
								<option value="3">3</option>
								<option value="2">2</option>
								<option value="1">1</option>
							</select>
						</div>
						<div class="form-group">
							<label>Nội dung review</label>
							<textarea name="reviewText" class="form-control" rows="3" placeholder="Nhận xét của bạn..." required></textarea>
						</div>
						<button type="submit" class="btn btn-accent" style="margin-top:8px;">Submit</button>
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<p><a href="<c:url value='/login'/>">Đăng nhập</a> để gửi review.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
	