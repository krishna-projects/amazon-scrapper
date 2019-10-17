<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Amazon Web Scrapper">
	<meta name="author" content="Krishnandan Sharma">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">
	<title>Amazon Data Scrapper</title>

</head>

<body onload="enableCheck()">
	<script type="text/javascript">
		function enableCheck() {
			if ('${message}'.length > 0)
				alert('${message}');

			if ('${product.affUrl}'.length > 0)
				$('#save').removeAttr("disabled");
		}
	</script>
	<div class="container-fluid bg-dark text-danger top">
		<div class="container">
			<h3>Amazon Data Scrapper</h3>
		</div>
	</div>
	<div class="container mt-5">
		<form action="" method="POST">
			<input type="text" class="form-control" name="tag" placeholder="Enter tag (optional)">
			<div class="input-group mb-3">
				<input type="url" class="form-control" name="url" placeholder="Enter Amazon Product URL" required>
				<div class="input-group-append">
					<button class="btn btn-success" type="submit">Get Product
						Info</button>
				</div>
			</div>
		</form>
		<label class="text-primary">${product.affUrl}</label> <br> <label id="msg" class="text-danger"></label>
		<div class="row mt-5">
			<div class="col-md-6">
				<img src="${product.imageUrl }" alt="Product image">
			</div>
			<div class="col-md-6">
				<form action="save-to-database" method="post">
					<div class="form-group">
						<label>Product Name</label> <input type="text" value="${product.name }" class="form-control"
							name="name" placeholder="Product Name">
					</div>
					<div class="form-group">
						<label>MRP</label> <input type="text" class="form-control" name="MRP" value="${product.MRP }"
							placeholder="MRP">
					</div>
					<div class="form-group">
						<label for="Price">Current Price</label> <input type="text" value="${product.price }"
							class="form-control" name="price" placeholder="Current Price">
					</div>
					<div class="form-group">
						<label for="Customer Reviews">Number of customer reviews</label> <input
							value="${product.customerCount }" type="text" class="form-control" name="customerCount"
							placeholder="Number of customer reviews">
					</div>
					<div class="form-group">
						<label>Rating</label> <input type="text" class="form-control" value="${product.rating }"
							name="rating" placeholder="rating">
					</div>
					<div class="form-group">
						<label>description</label>
						<textarea name="description" class="form-control" cols="30" placeholder="Description"
							rows="10">${product.description}</textarea>
					</div>
					<div class="form-group">
						<label>ASIN Product Id</label> <input type="text" class="form-control" value="${product.pid}"
							name="pid" placeholder="Product Id">
					</div>
					<div class="form-group">
						<label class="text_danger">Select catagory</label>
						<select name="cat" class="form-control">
							<option>New Arrival</option>
							<option>Popular</option>
							<option>Gift For Him</option>
							<option>Gift For Her</option>
							<option>Never Seen</option>
						</select>
					</div>
					<div class="form-group">
						<button disabled="disabled" id="save" type="submit"
							class="btn btn-primary btn-lg btn-block mb-5">Save to
							Database</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- All script -->
	<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>

</html>