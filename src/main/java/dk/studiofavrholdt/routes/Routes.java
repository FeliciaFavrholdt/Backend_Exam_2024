//package dk.studiofavrholdt.routes;
//
//import io.javalin.apibuilder.EndpointGroup;
//import static io.javalin.apibuilder.ApiBuilder.path;
//
///**
// * 8.1 Implement JWT-based authentication for the REST API.
// * 8.2 Protect endpoints with roles.
// * 8.3 Describe how to fix failing tests caused by role-based security or implement fixes in code.
// */
//
//public class Routes {
//
//    public EndpointGroup getRoutes() {
//        return () -> {
//            path("/items", new ItemRoutes().getItemRoutes());
//        };
//    }
//}
