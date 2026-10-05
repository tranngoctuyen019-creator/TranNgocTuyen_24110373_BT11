<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sản phẩm</title>
<style>
	.page-wrap { max-width: 1400px; margin: 0 auto; padding: 40px 24px 70px; }
	.topbar { border-bottom: 1px solid var(--border); padding-bottom: 20px; margin-bottom: 24px; display: flex; justify-content: space-between; align-items: flex-end; flex-wrap: wrap; gap: 16px; }
	.topbar h1 { font-size: 26px; font-weight: 700; margin: 0; }
	.topbar p { color: var(--muted); font-size: 15px; margin: 6px 0 0; }
	.filter-bar { display: flex; align-items: center; gap: 10px; }
	.filter-bar label { font-weight: 600; font-size: 14.5px; }
	.filter-bar select { border: 1px solid var(--border); border-radius: 4px; padding: 9px 12px; font-size: 15px; min-width: 220px; background: #fff; color: var(--ink); cursor: pointer; }
	.filter-bar .reset-link { font-size: 14.5px; color: var(--muted); text-decoration: underline; }

	.author-block { margin-bottom: 46px; }
	.author-block-title { display: flex; align-items: center; gap: 10px; font-size: 22px; font-weight: 700; margin: 0 0 18px; }
	.author-badge { background: var(--ink); color: #fff; font-size: 13px; font-weight: 600; padding: 4px 11px; border-radius: 20px; }

	.product-grid { display: flex; flex-wrap: wrap; gap: 22px; }
	.product-card { background: var(--surface); border: 1px solid var(--border); border-radius: 6px; width: calc(25% - 16.5px); min-width: 230px; text-decoration: none; color: var(--ink); display: block; transition: box-shadow .15s ease, border-color .15s ease; }
	.product-card:hover { border-color: var(--accent); text-decoration: none; color: var(--ink); box-shadow: 0 4px 14px rgba(0,0,0,.08); }
	.product-card img { width: 100%; height: 260px; object-fit: cover; border-bottom: 1px solid var(--border); border-radius: 6px 6px 0 0; background: #eee; }
	.product-info { padding: 16px; }
	.product-info .p-name { font-weight: 700; font-size: 17px; margin-bottom: 10px; line-height: 1.3; }
	.product-info .p-name a { color: var(--ink); }
	.product-info .p-meta { font-size: 14px; color: var(--muted); margin-bottom: 4px; }
	.product-info .p-review { margin-top: 10px; font-size: 14.5px; }
	.product-info .p-review a { color: var(--accent); font-weight: 600; }
	.empty-box { background: var(--surface); border: 1px solid var(--border); padding: 50px; text-align: center; color: var(--muted); font-size: 16px; }
	.pagination-wrap { margin-top: 20px; }
	.pagination { margin: 0; }
	.pagination > li > a { color: var(--ink); border-radius: 4px !important; margin: 0 3px; border-color: var(--border); font-size: 15px; padding: 8px 14px; }
	.pagination > .active > a { background: var(--accent); border-color: var(--accent); color: #fff; }
	@media (max-width: 1100px) {
		.product-card { width: calc(33.333% - 15px); }
	}
	@media (max-width: 768px) {
		.product-card { width: calc(50% - 11px); }
	}
	@media (max-width: 480px) {
		.product-card { width: 100%; }
	}
</style>
</head>
<body>
	<div class="page-wrap">
		<div class="topbar">
			<div>
				<h1>Sản phẩm</h1>
				<p>Danh sách sách theo từng tác giả</p>
			</div>
			<form class="filter-bar" method="get" action="<c:url value='/products'/>" id="filterForm">
				<label for="authorSelect">Tác giả:</label>
				<select name="authorId" id="authorSelect" onchange="document.getElementById('filterForm').submit()">
					<option value="">-- Tất cả tác giả --</option>
					<c:forEach items="${authors}" var="a">
						<option value="${a.authorId}" ${not empty selectedAuthorId && selectedAuthorId == a.authorId ? 'selected' : ''}>${a.authorName}</option>
					</c:forEach>
				</select>
				<c:if test="${not empty selectedAuthorId}">
					<a class="reset-link" href="<c:url value='/products'/>">Bỏ lọc</a>
				</c:if>
			</form>
		</div>

		<c:choose>
			<c:when test="${empty sections}">
				<div class="empty-box">Không tìm thấy sách phù hợp.</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${sections}" var="section">
					<div class="author-block">
						<div class="author-block-title">
							${section.authorName}
							<span class="author-badge">${section.totalCount} sách</span>
						</div>

						<div class="product-grid">
							<c:forEach items="${section.books}" var="book">
								<a class="product-card" href="<c:url value='/book-detail'><c:param name='id' value='${book.bookid}'/></c:url>">
									<img src="<c:url value='${not empty book.coverImage ? book.coverImage : "https://placehold.co/300x400?text=No+Cover"}'/>" alt="${book.title}">
									<div class="product-info">
										<div class="p-name">${book.title}</div>
										<div class="p-meta">Mã ISBN: ${book.isbn}</div>
										<div class="p-meta">Tác giả: ${book.authorNames}</div>
										<div class="p-meta">Publisher: ${book.publisher}</div>
										<div class="p-meta">Publisher_date: ${book.publishDate.dayOfMonth}/${book.publishDate.monthValue}/${book.publishDate.year}</div>
										<div class="p-meta">Quantity: ${book.quantity}</div>
										<div class="p-review">Review (${book.reviewCount})</div>
									</div>
								</a>
							</c:forEach>
						</div>

						<c:if test="${section.totalPages > 1}">
							<div class="pagination-wrap">
								<ul class="pagination">
									<c:forEach begin="1" end="${section.totalPages}" var="p">
										<li class="${p == section.currentPage ? 'active' : ''}">
											<a href="<c:url value='/products'><c:param name='page_${section.authorId}' value='${p}'/><c:if test='${not empty selectedAuthorId}'><c:param name='authorId' value='${selectedAuthorId}'/></c:if></c:url>">${p}</a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
