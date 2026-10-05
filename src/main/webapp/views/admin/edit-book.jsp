<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sửa sách</title>
<style>
	.page-wrap { max-width: 860px; margin: 0 auto; padding: 0; }
	.topbar { border-bottom: 1px solid var(--border); padding-bottom: 18px; margin-bottom: 26px; }
	.topbar h1 { font-size: 22px; font-weight: 600; margin: 0; }
	.topbar p { color: var(--muted); font-size: 14px; margin: 4px 0 0; }
	.form-card { background: var(--surface); border: 1px solid var(--border); padding: 32px 36px; }
	.form-group { margin-bottom: 18px; }
	.form-group label { display: block; font-weight: 500; color: var(--ink); font-size: 15px; margin-bottom: 7px; }
	.form-control { display: block; width: 100%; box-sizing: border-box; border: 1px solid var(--border); border-radius: 4px; padding: 9px 14px; font-size: 15px; background: #fff; color: var(--ink); box-shadow: none; }
	.form-control:focus { outline: none; border-color: var(--accent); box-shadow: none; }
	textarea.form-control { height: auto; min-height: 90px; resize: vertical; padding: 10px 14px; line-height: 1.5; }
	.current-image-box { width: 100%; border: 1px solid var(--border); padding: 14px; text-align: center; margin-bottom: 16px; }
	.current-image-box img { display: block; width: 120px; height: auto; margin: 0 auto; border: 1px solid var(--border); }
	.current-image-box .img-label { display: block; font-size: 13px; color: var(--muted); margin-bottom: 10px; }
	.checkbox-inline { font-weight: 400; margin-right: 14px; }
	.btn-group-actions { margin-top: 22px; }
	.btn { display: inline-block; border-radius: 4px; font-weight: 500; padding: 11px 24px; border: 1px solid transparent; font-size: 15px; text-decoration: none; cursor: pointer; line-height: 1.2; }
	.btn-accent { background: var(--accent); color: #fff; }
	.btn-accent:hover { background: var(--accent-dark); color: #fff; }
	.btn-default { background: #fff; color: var(--muted); border-color: var(--border); }
	.btn-default:hover { background: #f2f2f2; color: var(--ink); text-decoration: none; }
	.alert { border-radius: 4px; }
</style>
</head>
<body>
	<div class="page-wrap">
		<div class="topbar">
			<h1>Sửa sách #${book.bookid}</h1>
			<p>Cập nhật thông tin sách</p>
		</div>

		<div class="form-card">
			<c:if test="${not empty error}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<c:set var="selectedIds">,<c:forEach items="${book.authors}" var="ba">${ba.authorId},</c:forEach></c:set>

			<form action="<c:url value='/admin/book/edit'/>" method="post" enctype="multipart/form-data">
				<input type="hidden" name="bookid" value="${book.bookid}">

				<div class="form-group">
					<label>Mã ISBN</label>
					<input type="number" name="isbn" class="form-control" value="${book.isbn}">
				</div>
				<div class="form-group">
					<label>Tiêu đề</label>
					<input type="text" name="title" class="form-control" value="${book.title}" required>
				</div>
				<div class="form-group">
					<label>Publisher</label>
					<input type="text" name="publisher" class="form-control" value="${book.publisher}">
				</div>
				<div class="form-group">
					<label>Giá</label>
					<input type="number" step="0.01" name="price" class="form-control" value="${book.price}">
				</div>
				<div class="form-group">
					<label>Mô tả</label>
					<textarea name="description" class="form-control" rows="3">${book.description}</textarea>
				</div>
				<div class="form-group">
					<label>Ngày xuất bản</label>
					<input type="date" name="publishDate" class="form-control" value="${book.publishDate}">
				</div>
				<div class="form-group">
					<label>Ảnh bìa</label>
					<div class="current-image-box">
						<span class="img-label">Ảnh hiện tại</span>
						<img src="<c:url value='${not empty book.coverImage ? book.coverImage : "https://placehold.co/120x160?text=No+Cover"}'/>" alt="Ảnh hiện tại">
					</div>
					<label>Đổi ảnh bìa (chọn file PNG, để trống nếu giữ nguyên)</label>
					<input type="file" name="coverImageFile" class="form-control" accept="image/png,.png">
				</div>
				<div class="form-group">
					<label>Số lượng</label>
					<input type="number" name="quantity" class="form-control" value="${book.quantity}">
				</div>
				<div class="form-group">
					<label>Tác giả</label><br>
					<c:forEach items="${authors}" var="a">
						<c:set var="needle" value=",${a.authorId}," />
						<label class="checkbox-inline">
							<input type="checkbox" name="authorIds" value="${a.authorId}"
								${fn:contains(selectedIds, needle) ? 'checked' : ''}> ${a.authorName}
						</label>
					</c:forEach>
				</div>

				<div class="btn-group-actions">
					<button type="submit" class="btn btn-accent">Cập nhật</button>
					<a href="<c:url value='/admin/book/list'/>" class="btn btn-default">Hủy</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
