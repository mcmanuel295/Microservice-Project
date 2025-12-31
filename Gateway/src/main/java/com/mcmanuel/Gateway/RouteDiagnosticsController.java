package com.mcmanuel.Gateway;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.util.Map;

@RestController
public class RouteDiagnosticsController {

    private final RouteDefinitionLocator routeDefinitionLocator;

    public RouteDiagnosticsController(RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @GetMapping("/routes")
    public Flux<Map<String, String>> getRoutes() {
        return routeDefinitionLocator.getRouteDefinitions()
                .map(route -> Map.of(
                        "id", route.getId(),
                        "uri", route.getUri().toString(),
                        "predicates", route.getPredicates().toString()
                ));
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "Gateway is running");
    }
}