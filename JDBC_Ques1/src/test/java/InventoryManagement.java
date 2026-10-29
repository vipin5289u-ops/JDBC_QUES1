
import java.sql.*;
import java.util.Scanner;

public class InventoryManagement {

    static final String URL =
            "jdbc:mysql://localhost:3306/retail_db";
    static final String USER = "root";
    static final String PASSWORD = "Vipin@5289";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("MySQL Connected Successfully!");

            while (true) {
                System.out.println("\n===== INVENTORY MANAGEMENT =====");
                System.out.println("1. Insert 3 Products");
                System.out.println("2. Search by Category");
                System.out.println("3. Update Stock");
                System.out.println("4. Update Product Price");
                System.out.println("5. Inventory Value and Delete");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1: insertProducts(); break;
                    case 2: searchCategory(); break;
                    case 3: updateStock(); break;
                    case 4: updatePrice(); break;
                    case 5: inventoryAndDelete(); break;
                    case 6:
                        con.close();
                        sc.close();
                        System.out.println("Program Closed!");
                        return;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (a) Insert three product records
    static void insertProducts() throws SQLException {
        String sql = "INSERT INTO Product VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 1; i <= 3; i++) {
                System.out.println("\nProduct " + i);

                System.out.print("Product ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Product Name: ");
                String name = sc.nextLine();

                System.out.print("Category: ");
                String category = sc.nextLine();

                System.out.print("Quantity: ");
                int qty = sc.nextInt();

                System.out.print("Price: ");
                double price = sc.nextDouble();

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, category);
                ps.setInt(4, qty);
                ps.setDouble(5, price);

                try {
                    ps.executeUpdate();
                    System.out.println("Product Inserted!");
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Duplicate Product ID!");
                }
            }
        }
    }

    // (b) Retrieve category products with quantity below 20
    static void searchCategory() throws SQLException {
        sc.nextLine();
        System.out.print("Enter category: ");
        String category = sc.nextLine();

        String sql = "SELECT * FROM Product " +
                "WHERE category = ? AND quantity < 20 " +
                "ORDER BY price DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("product_id") + " | " +
                                    rs.getString("product_name") + " | " +
                                    rs.getString("category") + " | " +
                                    rs.getInt("quantity") + " | " +
                                    rs.getDouble("price")
                    );
                }
            }
        }
    }

    // (c) Add quantity to existing stock
    static void updateStock() throws SQLException {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        System.out.print("Quantity to add: ");
        int qty = sc.nextInt();

        if (qty <= 0) {
            System.out.println("Quantity must be positive!");
            return;
        }

        String sql = "UPDATE Product SET quantity = quantity + ? " +
                "WHERE product_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ?
                    "Stock Updated!" : "Product Not Found!");
        }
    }

    // (d) Update price only if product exists and price is positive
    static void updatePrice() throws SQLException {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        System.out.print("Enter New Price: ");
        double price = sc.nextDouble();

        if (price <= 0) {
            System.out.println("Price must be greater than zero!");
            return;
        }

        String sql = "UPDATE Product SET price = ? WHERE product_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Product Not Found!");
            } else {
                System.out.println("Updated Product Details:");

                String query = "SELECT * FROM Product WHERE product_id = ?";
                try (PreparedStatement ps2 = con.prepareStatement(query)) {
                    ps2.setInt(1, id);

                    try (ResultSet rs = ps2.executeQuery()) {
                        if (rs.next()) {
                            System.out.println(
                                    rs.getInt("product_id") + " | " +
                                            rs.getString("product_name") + " | " +
                                            rs.getString("category") + " | " +
                                            rs.getInt("quantity") + " | " +
                                            rs.getDouble("price")
                            );
                        }
                    }
                }
            }
        }
    }

    // (e) Calculate inventory value and delete only zero-stock product
    static void inventoryAndDelete() throws SQLException {
        String sql = "SELECT SUM(quantity * price) AS total FROM Product";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println(
                        "Total Inventory Value: " + rs.getDouble("total"));
            }
        }

        System.out.print("Enter Product ID to delete: ");
        int id = sc.nextInt();

        String delete = "DELETE FROM Product " +
                "WHERE product_id = ? AND quantity = 0";

        try (PreparedStatement ps = con.prepareStatement(delete)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            System.out.println(rows > 0 ?
                    "Product Deleted!" :
                    "Cannot Delete: Product Not Found or Quantity is Not Zero!");
        }
    }
}
