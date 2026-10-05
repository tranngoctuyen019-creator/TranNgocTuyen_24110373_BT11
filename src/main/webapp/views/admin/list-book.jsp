<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý sách</title>
<style>
	.page-wrap { width: 100%; max-width: none; margin: 0; padding: 0; box-sizing: border-box; }
	.topbar { display: flex; justify-content: space-between; align-items: flex-end; border-bottom: 1px solid var(--border); padding-bottom: 18px; margin-bottom: 26px; }
	.topbar h1 { font-size: 22px; font-weight: 600; margin: 0; }
	.topbar p { color: var(--muted); font-size: 14px; margin: 4px 0 0; }
	.btn-add { background: var(--accent); color: #fff; font-weight: 500; border: none; border-radius: 4px; padding: 11px 22px; font-size: 15px; display: inline-block; margin-bottom: 20px; text-decoration: none; }
	.btn-add:hover { background: var(--accent-dark); color: #fff; text-decoration: none; }
	table { margin-bottom: 0 !important; background: var(--surface); width: 100%; }
	.table > thead > tr > th { background: transparent; color: var(--muted); border-bottom: 1px solid var(--border) !important; border-top: none !important; font-weight: 600; font-size: 13px; text-transform: uppercase; letter-spacing: .03em; padding: 14px 16px; }
	.table > tbody > tr > td { border-top: 1px solid #eee !important; vertical-align: middle !important; padding: 14px 16px; font-size: 14.5px; }
	img.thumb { border-radius: 2px; border: 1px solid var(--border); object-fit: cover; }
	.btn-sm { border-radius: 4px; font-weight: 500; padding: 7px 14px; border: 1px solid transparent; font-size: 13.5px; }
	.btn-default { background: transparent; color: var(--ink); border-color: var(--border); text-decoration: none; }
	.btn-default:hover { background: #f2f2f2; color: var(--ink); }
	.btn-danger { background: transparent; color: var(--danger); border-color: var(--danger); text-decoration: none; }
	.btn-danger:hover { background: var(--danger); color: #fff; }
	.empty-row { padding: 30px !important; color: var(--muted); }
	.pagination-wrap { text-align: center; margin-top: 30px; }
	.pagination > li > a { color: var(--ink); border-radius: 4px !important; margin: 0 3px; border-color: var(--border); }
	.pagination > .active > a { background: var(--accent); border-color: var(--accent); color: #fff; }
</style>
</head>
<body>
	<div class="page-wrap">
		<div class="topbar">
			<div>
				<h1>Quản lý sách</h1>
				<p>Danh sách sách hiện có trong hệ thống</p>
			</div>
		</div>

		<a href="<c:url value='/admin/book/add'/>" class="btn-add">+ Thêm sách</a>

		<table class="table table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>Ảnh bìa</th>
					<th>Tiêu đề</th>
					<th>Tác giả</th>
					<th>Publisher</th>
					<th>Giá</th>
					<th>Số lượng</th>
					<th>Thao tác</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${books}" var="book">
					<tr>
						<td>${book.bookid}</td>
						<td>
							<img class="thumb" width="50" height="66" src="<c:url value='${not empty book.coverImage ? book.coverImage : "https://placehold.co/50x66?text=No+Cover"}'/>" alt="Ảnh bìa">
						</td>
						<td>${book.title}</td>
						<td>${book.authorNames}</td>
						<td>${book.publisher}</td>
						<td><fmt:formatNumber value="${book.price}" type="number"/></td>
						<td>${book.quantity}</td>
						<td>
							<a href="<c:url value='/admin/book/edit'><c:param name='id' value='${book.bookid}'/></c:url>" class="btn btn-default btn-sm">Sửa</a>
							<a href="<c:url value='/admin/book/delete'><c:param name='id' value='${book.bookid}'/></c:url>"
							   class="btn btn-danger btn-sm"
							   onclick="return confirm('Xóa sách này?');">Xóa</a>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty books}">
					<tr><td colspan="8" class="text-center empty-row">Chưa có sách nào.</td></tr>
				</c:if>
			</tbody>
		</table>

		<c:if test="${totalPages > 1}">
			<div class="pagination-wrap">
				<ul class="pagination">
					<c:if test="${currentPage > 1}">
						<li><a href="<c:url value='/admin/book/list'><c:param name='page' value='${currentPage - 1}'/></c:url>">&laquo; Trang trước</a></li>
					</c:if>
					<c:forEach begin="1" end="${totalPages}" var="p">
						<li class="${p == currentPage ? 'active' : ''}">
							<a href="<c:url value='/admin/book/list'><c:param name='page' value='${p}'/></c:url>">${p}</a>
						</li>
					</c:forEach>
					<c:if test="${currentPage < totalPages}">
						<li><a href="<c:url value='/admin/book/list'><c:param name='page' value='${currentPage + 1}'/></c:url>">Trang sau &raquo;</a></li>
					</c:if>
				</ul>
			</div>
		</c:if>
	</div>
</body>
</html>
