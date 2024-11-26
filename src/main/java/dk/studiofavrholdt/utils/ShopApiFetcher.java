package dk.studiofavrholdt.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dk.studiofavrholdt.entities.Shop;
import dk.studiofavrholdt.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ShopApiFetcher {
    private static final Logger log = LoggerFactory.getLogger(ShopApiFetcher.class);
    private static final String BASE_URL = "https://shopapi.cphbusinessapps.dk/shops";

    public static List<Shop> fetchShops() throws ApiException {
        log.info("Fetching all shops");

        return fetchShopsFromApi(BASE_URL);
    }

    public static List<Shop> fetchShopsByCategory(String category) throws ApiException {
        log.info("Fetching shops for category: {}", category);

        return fetchShopsFromApi(BASE_URL + "/" + category.toUpperCase());
    }

    private static List<Shop> fetchShopsFromApi(String apiUrl) throws ApiException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Received response with status code: {}", response.statusCode());

            if (response.statusCode() != 200) {
                throw new ApiException(response.statusCode(), "Failed to fetch shops from " + apiUrl);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return objectMapper.readValue(response.body(), new TypeReference<List<Shop>>() {});
        } catch (Exception e) {
            log.error("Error fetching shops from API: {}", apiUrl, e);
            throw new ApiException(500, "Error fetching shops: " + e.getMessage());
        }
    }
}
