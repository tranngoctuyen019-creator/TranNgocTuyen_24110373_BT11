<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm sách</title>
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
			<h1>Thêm sách mới</h1>
			<p>Tạo một cuốn sách mới cho hệ thống</p>
		</div>

		<div class="form-card">
			<c:if test="${not empty error}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<form action="<c:url value='/admin/book/add'/>" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label>Mã ISBN</label>
					<input type="number" name="isbn" class="form-control">
				</div>
				<div class="form-group">
					<label>Tiêu đề</label>
					<input type="text" name="title" class="form-control" required>
				</div>
				<div class="form-group">
					<label>Publisher</label>
					<input type="text" name="publisher" class="form-control">
				</div>
				<div class="form-group">
					<label>Giá</label>
					<input type="number" step="0.01" name="price" class="form-control">
				</div>
				<div class="form-group">
					<label>Mô tả</label>
					<textarea name="description" class="form-control" rows="3"></textarea>
				</div>
				<div class="form-group">
					<label>Ngày xuất bản</label>
					<input type="date" name="publishDate" class="form-control">
				</div>
				<div class="form-group">
					<label>Ảnh bìa (file PNG)</label>
					<input type="file" name="coverImageFile" class="form-control" accept="image/png,.png">
				</div>
				<div class="form-group">
					<label>Số lượng</label>
					<input type="number" name="quantity" class="form-control">
				</div>
				<div class="form-group">
					<label>Tác giả</label><br>
					<c:forEach items="${authors}" var="a">
						<label class="checkbox-inline">
							<input type="checkbox" name="authorIds" value="${a.authorId}"> ${a.authorName}
						</label>
					</c:forEach>
				</div>

				<div class="btn-group-actions">
					<button type="submit" class="btn btn-accent">Lưu</button>
					<a href="<c:url value='/admin/book/list'/>" class="btn btn-default">Hủy</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
