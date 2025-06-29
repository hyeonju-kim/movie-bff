package com.nicole.bff.client;

import com.nicole.bff.config.TimeoutConfigDto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * UserApiClient
 * - 외부 User API 호출용
 *   애플리케이션 구동 시 설정된 타임아웃으로 RestTemplate을 생성하고,
 *   런타임 중 동적으로 타임아웃 설정을 변경하여 RestTemplate을 재생성해주는 컴포넌트
 *
 * @author hyeonju
 * @created 2025/06/29
 */
@Getter
@Component("userApiClient")
@Slf4j
public class UserApiClient implements ApiClientService{
    @Value("${cnf.rest-template.connect-timeout}")
    private int connectTimeout;

    @Value("${cnf.rest-template.response-timeout}")
    private int responseTimeout;
    private RestTemplate userRestTemplate;

    @PostConstruct
    public void init() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(responseTimeout);

        this.userRestTemplate = new RestTemplate();
        userRestTemplate.setRequestFactory(factory);

        log.info("UserApiClient 설정: connectionTimeout={}ms, responseTimeout={}ms",
                connectTimeout, responseTimeout);
    }

    // 동적 변경
    @Override
    public void updateRestTemplate(TimeoutConfigDto timeoutConfigDto) {
        // RestTemplate용 새 팩토리 생성
        SimpleClientHttpRequestFactory newFactory = new SimpleClientHttpRequestFactory();

        // 동적으로 설정값 가져오기
        this.connectTimeout = timeoutConfigDto.getConnectionTimeout();
        this.responseTimeout = timeoutConfigDto.getReadTimeout();

        newFactory.setConnectTimeout(connectTimeout);
        newFactory.setReadTimeout(responseTimeout);

        // 새 RestTemplate에 새 팩토리 세팅
        RestTemplate newRestTemplate = new RestTemplate();
        newRestTemplate.setRequestFactory(newFactory);

        // 기존 userRestTemplate에 newRestTemplate 대입
        this.userRestTemplate = newRestTemplate;

        log.info("UserApiClient 타임아웃 설정 변경됨: connectionTimeout={}ms, responseTimeout={}ms",
                connectTimeout, responseTimeout);
    }
}
