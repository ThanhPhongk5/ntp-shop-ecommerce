const API_URL = "http://localhost:8080";
const CURRENT_USER_ID = 1;

async function getAllProducts() {
  try {
    const response = await fetch(`${API_URL}/products`);
    if (!response.ok) throw new Error("Không thể lấy dữ liệu từ server");

    const products = await response.json();
    const container = document.getElementById("product-list");

    if (!container) return;

    container.innerHTML = products
      .map((p) => {
        const imageUrl = p.image
          ? `${API_URL}/images/${p.image}`
          : "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&auto=format&fit=crop";

        return `
            <div class="product-card">
                <img src="${imageUrl}" 
                     alt="${p.name}" 
                     style="width:100%; height:180px; object-fit:cover; border-radius:8px; margin-bottom:12px;">
                <h3>${p.name}</h3>
                <p class="price">${p.price.toLocaleString("vi-VN")} VNĐ</p>
                <p class="stock">Kho: ${p.stock}</p>
                <button class="btn-add" onclick="addToCart(${p.id})">Thêm vào giỏ</button>
            </div>
        `;
      })
      .join("");
  } catch (error) {
    console.error("Lỗi khi lấy sản phẩm:", error);
    const container = document.getElementById("product-list");
    if (container)
      container.innerHTML = `<p style="color:red">Lỗi kết nối Backend. Hãy kiểm tra CORS hoặc chạy Server!</p>`;
  }
}

async function addToCart(productId) {
  const requestData = { productId: productId, quantity: 1 };
  try {
    const response = await fetch(`${API_URL}/cart/add/${CURRENT_USER_ID}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestData),
    });
    if (response.ok) {
      alert(" Đã thêm sản phẩm vào giỏ hàng!");
    } else {
      alert(" Không thể thêm sản phẩm. Vui lòng thử lại!");
    }
  } catch (error) {
    console.error("Lỗi:", error);
    alert("Lỗi kết nối tới Server!");
  }
}

async function renderCart() {
  const container = document.getElementById("cart-items");
  const checkoutBtn = document.getElementById("checkout-btn");

  if (!container) return;

  try {
    const response = await fetch(`${API_URL}/cart/${CURRENT_USER_ID}`);
    if (!response.ok) throw new Error("Lỗi lấy giỏ hàng");

    const cart = await response.json();

    if (!cart.items || cart.items.length === 0) {
      container.innerHTML = `<div style="text-align:center; padding:50px;">
                <p>Giỏ hàng của bạn đang trống.</p>
                <a href="index.html" style="color:var(--primary-color)">Quay lại mua sắm</a>
            </div>`;
      if (checkoutBtn) checkoutBtn.style.display = "none";
      return;
    }

    container.innerHTML = cart.items
      .map(
        (item) => `
            <div class="cart-item">
                <div class="item-info">
                    <h4 style="margin:0">${item.productName}</h4>
                    <small style="color:var(--text-muted)">Số lượng: ${item.quantity}</small>
                </div>
                <div class="item-price" style="text-align:right">
                    <div style="font-weight:bold; color:var(--primary-color)">
                        ${(item.price * item.quantity).toLocaleString("vi-VN")} VNĐ
                    </div>
                    <button onclick="deleteItem(${item.productId})" 
                            style="background:none; border:none; color:red; cursor:pointer; padding:5px; font-size:0.8rem">
                        Xóa khỏi giỏ
                    </button>
                </div>
            </div>
        `,
      )
      .join("");

    if (checkoutBtn) checkoutBtn.style.display = "block";
  } catch (error) {
    console.error("Lỗi:", error);
    container.innerHTML = `<p style="color:red">Không thể tải giỏ hàng.</p>`;
  }
}

async function deleteItem(productId) {
  if (!confirm("Bạn có chắc chắn muốn xóa sản phẩm này?")) return;
  try {
    const response = await fetch(
      `${API_URL}/cart/${CURRENT_USER_ID}/item/${productId}`,
      { method: "DELETE" },
    );
    if (response.ok) renderCart();
  } catch (error) {
    alert("Lỗi khi xóa sản phẩm!");
  }
}

async function checkout() {
  try {
    const response = await fetch(
      `${API_URL}/orders/create/${CURRENT_USER_ID}`,
      { method: "POST" },
    );
    if (response.ok) {
      alert("🎉 Đặt hàng thành công! Cảm ơn bạn đã mua sắm.");
      window.location.href = "index.html";
    } else {
      alert("Thanh toán thất bại. Vui lòng thử lại!");
    }
  } catch (error) {
    alert("Lỗi kết nối khi thanh toán!");
  }
}

async function createProduct(productData, file) {
  try {
    const response = await fetch(`${API_URL}/products`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(productData),
    });

    if (response.ok) {
      const createdProduct = await response.json();

      if (file && createdProduct.id) {
        const formData = new FormData();
        formData.append("file", file);

        const uploadResponse = await fetch(
          `${API_URL}/products/${createdProduct.id}/upload`,
          {
            method: "POST",
            body: formData,
          },
        );

        if (!uploadResponse.ok) {
          alert("Sản phẩm đã tạo nhưng tải ảnh lên bị lỗi!");
        }
      }

      alert("✨ Thành công! Sản phẩm đã được thêm vào cơ sở dữ liệu.");
      window.location.href = "index.html";
    } else {
      const errorMsg = await response.text();
      alert("Có lỗi xảy ra: " + errorMsg);
    }
  } catch (error) {
    console.error("Lỗi kết nối API:", error);
    alert("Không thể kết nối tới server Spring Boot!");
  }
}
