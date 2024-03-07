package org.example.demobusinessapp.service.workday;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.YearMonth;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkDayService {
    private final WebClient webClient;
    private static final ParameterizedTypeReference<Map<String, Object>> OPENAPI_RESPONSE_TYPE_MAP = new ParameterizedTypeReference<>() {
    };

    public int getBusinessDaysOfYearMonth(YearMonth yearMonth) {
        int weekDays = 0;
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
                .queryParam("")
                .queryParam("serviceKey", "Q%2BITShZZXUlCF0K8Wv8v9Af0CjNS%2Bvkc7%2FzVh9CaI64WQDAY5igagwfqejA%2BBbvBEssnhSccJe3Cd1ROG7y5Xg%3D%3D")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 50)
                .queryParam("solYear", yearMonth.getYear())
                .queryParam("solMonth", String.format("%02d", yearMonth.getMonthValue()))
                .build();
        Map<String, Object> openApiResponse = webClient.get()
                .uri(uriComponents.toUri())
                .retrieve()
                .bodyToMono(OPENAPI_RESPONSE_TYPE_MAP).block();

        Map<String, Object> response = (Map<String, Object>) openApiResponse.get("response");
        Map<String, String> header = (Map<String, String>) response.get("header");


        return weekDays;
    }
}
