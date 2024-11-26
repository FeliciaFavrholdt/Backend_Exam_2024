//package dk.studiofavrholdt.routes;
//
//import dk.studiofavrholdt.config.HibernateConfig;
//import io.javalin.Javalin;
//import io.restassured.RestAssured;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//
//class ItemRoutesTest {
//
//    private static Javalin app;
//
//    @BeforeAll
//    static void setUp() {
//        // Set test environment for Hibernate
//        HibernateConfig.setTest(true);
//
//        // Start Javalin server
//        app = Javalin.create().start(7000);
//
//        // Register routes from ItemRoutes
//
//        app.routes(new ItemRoutes().getItemRoutes());
//
//        // Configure Rest Assured
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = 7000;
//    }
//
//
//    @AfterAll
//    static void tearDown() {
//        if (app != null) {
//            app.stop();
//        }
//    }
//
//    @Test
//    void testGetAllItems() {
//        given()
//                .when()
//                .get("/items")
//                .then()
//                .statusCode(200)
//                .body("$", not(empty())); // Expecting a non-empty response
//    }
//
//    @Test
//    void testGetItemById() {
//        given()
//                .when()
//                .get("/items/1")
//                .then()
//                .statusCode(200)
//                .body("id", equalTo(1));
//    }
//
//    @Test
//    void testCreateItem() {
//        given()
//                .contentType("application/json")
//                .body("{\"name\": \"New Item\", \"category\": \"Test\"}")
//                .when()
//                .post("/items")
//                .then()
//                .statusCode(201)
//                .body("name", equalTo("New Item"));
//    }
//
//    @Test
//    void testUpdateItemById() {
//        given()
//                .contentType("application/json")
//                .body("{\"name\": \"Updated Item\"}")
//                .when()
//                .put("/items/1")
//                .then()
//                .statusCode(204); // No content expected on successful update
//    }
//
//    @Test
//    void testDeleteItemById() {
//        given()
//                .when()
//                .delete("/items/1")
//                .then()
//                .statusCode(204); // No content expected on successful deletion
//    }
//
//    @Test
//    void testAssignItemToStudent() {
//        given()
//                .when()
//                .post("/items/1/students/2")
//                .then()
//                .statusCode(201);
//    }
//
//    @Test
//    void testGetItemsByStudent() {
//        given()
//                .when()
//                .get("/students/2/items")
//                .then()
//                .statusCode(200)
//                .body("$", not(empty()));
//    }
//
//    @Test
//    void testGetShopsByCategory() {
//        given()
//                .when()
//                .get("/shops/electronics")
//                .then()
//                .statusCode(200)
//                .body("$", not(empty()));
//    }
//
//    @Test
//    void testFilterItemsByCategory() {
//        given()
//                .queryParam("category", "Test")
//                .when()
//                .get("/items/filter")
//                .then()
//                .statusCode(200)
//                .body("$", not(empty()));
//    }
//
//    @Test
//    void testSummarizeTotalPurchasePrice() {
//        given()
//                .when()
//                .get("/students/items/summary")
//                .then()
//                .statusCode(200)
//                .body("total", greaterThan(0)); // Validate total is calculated
//    }
//
//    @Test
//    void testShopDataIncludedWhenFetchingItemById() {
//        given()
//                .when()
//                .get("/items/1")
//                .then()
//                .statusCode(200)
//                .body("shopData", notNullValue()); // Expect shop data in response
//    }
//}