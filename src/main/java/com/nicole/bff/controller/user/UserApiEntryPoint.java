package com.nicole.bff.controller.user;

import com.nicole.bff.client.ApiClientService;
import com.nicole.bff.client.UserApiClient;
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
 * UserApiEntryPoint
 * - User Controller
 *
 * @author hyeonju
 * @created 2025/06/29
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiEntryPoint {
    @Qualifier("userApiClient")
    @Autowired
    private ApiClientService apiClientService;

    private final RestTemplate restTemplate;

    @Value("${user.url}")
    private String externalUrl;

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(@ModelAttribute TimeoutConfigDto timeoutConfigDto) {
        apiClientService.updateRestTemplate(timeoutConfigDto);
        return ResponseEntity.ok("userApiClient 변경 완료");
    }

    /**
     * 유저 프로젝트의 특정 사용자 정보 조회
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String url = externalUrl + "/test";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.info("[test] > [response] === {}", response);

        return ResponseEntity.ok(response.getBody());
    }
}
