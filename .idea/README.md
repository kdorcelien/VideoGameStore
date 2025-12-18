This project supports authentication, product management, categories, profiles,
and a persistent shopping cart for logged-in users.

---

Features

Authentication & Security
- JWT-based authentication
- Role-based access control (`ROLE_USER`, `ROLE_ADMIN`)
- Secure login and registration endpoints

Products & Categories
- View all products and categories
- View product/category by ID
- Admin-only category creation and deletion

User Profiles
- Automatic profile creation on user registration
- Retrieve and update user profile information

Shopping Cart (Phase 3)
- Persistent shopping cart stored in the database
- Cart is retained between login sessions
- Add, update, and remove items from cart
- Cart total calculated dynamically

---

Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security (JWT)**
- **JDBC / MySQL**
- **Apache DBCP**
- **Maven**
- **Insomnia (API testing)**

---

##  Database

This project uses a MySQL database.  
The database schema includes:
- users
- profiles
- products
- categories
- shopping_cart

The shopping cart table persists cart items per user.

---

Authentication

Login

Register

---

Response format:
json
{
"items": {
"12": {
"product": {
"productId": 12,
"name": "External Hard Drive",
"price": 129.99,
"categoryId": 1,
"description": "Expand your storage capacity",
"subCategory": "Gray",
"stock": 25,
"imageUrl": "external-hard-drive.jpg",
"featured": true
},
"quantity": 1,
"discountPercent": 0,
"lineTotal": 129.99
}
},
"total": 129.99
}