/**
 * 
 */

//hàm xử lý add 1 sách vào giỏ hàng (jquery ajax)
(function($) {
	function addQuantity(FruitId) {
		$.get(path + "/addItem/" + FruitId, function() {
			$('#basket_total').html(data);
			window.scrollTo({ top: 0, behavior: 'smooth' });
		});
	}
	window.updateQuantity = function(FruitId, value) {
		console.log("Updating quantity:", FruitId, value);
		$.get("/updateQuantity/" + FruitId + "/" + value, function() {
			window.location.reload();
		}).fail(function() {
			alert("Có lỗi xảy ra khi cập nhật số lượng sản phẩm vui lòng load lại trang để tự cập nhật.");
		});
	};
	//hàm xử lý xóa 1 sách trong giỏ hàng (jquery ajax)
	window.removeItem = function removeItem(FruitId) {
		if (confirm('Bạn có muốn xóa không?')) {
			$.get("/removeItem/" + FruitId, function() {
				window.location.reload();
			});
		}
	}
	
	
})(jQuery);