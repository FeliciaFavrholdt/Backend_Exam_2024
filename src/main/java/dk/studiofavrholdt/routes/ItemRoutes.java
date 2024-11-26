//package dk.studiofavrholdt.routes;
//
//import dk.studiofavrholdt.controllers.ItemController;
//import dk.studiofavrholdt.security.enums.Role;
//import io.javalin.apibuilder.EndpointGroup;
//import static io.javalin.apibuilder.ApiBuilder.*;
//
//public class ItemRoutes {
//
//    private static final ItemController itemController = new ItemController();
//
//    public EndpointGroup getItemRoutes() {
//        return () -> {
//            // Get all items
//            get("/", itemController::getAll, Role.ANYONE, Role.USER, Role.ADMIN);
//
//            // Get an item by its ID
//            get("/{id}", itemController::getById, Role.ANYONE, Role.USER, Role.ADMIN);
//
//            // Add a new item
//            post("/", itemController::create, Role.ADMIN);
//
//            // Update an item by its ID
//            put("/{id}", itemController::update, Role.ADMIN);
//
//            // Delete an item by its ID
//            delete("/{id}", itemController::delete, Role.ADMIN);
//
//            // Assign an item to a student
//            post("/{itemId}/students/{studentId}", itemController::addItemToStudent, Role.USER, Role.ADMIN);
//
//            // Get all items for a specific student
//            get("/students/{studentId}/items", itemController::getItemsByStudent, Role.USER, Role.ADMIN);
//
//            // 6.4 Add an endpoint to get a list of all shops for a given category
//            get("/shops/{category}", itemController::getShopsForCategory, Role.ANYONE, Role.USER, Role.ADMIN);
//
//            // 5.1: Filter items by category
//            get("/items/filter", itemController::filterItemsByCategory, Role.ANYONE, Role.USER, Role.ADMIN);
//
//            // 5.2: Summarize total purchase price of items borrowed by each student
//            //get("/students/items/summary", itemController::summarizeTotalPurchasePrice, Role.ADMIN);
//        };
//    }
//}