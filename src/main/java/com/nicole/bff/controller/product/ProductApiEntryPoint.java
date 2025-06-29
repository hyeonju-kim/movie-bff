package com.nicole.bff.controller.product;

import com.nicole.bff.client.ApiClientService;
import com.nicole.bff.config.TimeoutConfigDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * ProductApiEntryPoint
 * - Product Controller
 *
 * @author hyeonju
 * @created 2025/06/29
 */

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductApiEntryPoint {
    @Qualifier("productApiClient")
    @Autowired
    private ApiClientService apiClientService;

    private final RestTemplate restTemplate;

    @Value("${product.url}")
    private String externalUrl;

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(@ModelAttribute TimeoutConfigDto timeoutConfigDto) {
        apiClientService.updateRestTemplate(timeoutConfigDto);
        return ResponseEntity.ok("productApiClient 변경 완료");
    }

    /**
     * 상품 프로젝트의 영화 정보 조회
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String url = externalUrl + "/movie/movies";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info("[test] > [response] === {}", response);

        return ResponseEntity.ok(response.getBody());
    }
}
