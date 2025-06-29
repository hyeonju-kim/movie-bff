package com.nicole.bff.client;

import com.nicole.bff.config.TimeoutConfigDto;

/**
 * ApiClientService
 * - 외부 API 호출용 인터페이스
 *
 * @author hyeonju
 * @created 2025/06/29
 */
public interface ApiClientService {
    void updateRestTemplate(TimeoutConfigDto timeoutConfigDto);
}
