package com.nicole.bff.config;

import lombok.Getter;
import lombok.Setter;

/**
 * TimeoutConfigDto
 * - /refresh 호출 시 타임아웃 설정 DTO
 *
 * @author hyeonju
 * @created 2025/06/29
 */

@Getter
@Setter
public class TimeoutConfigDto {
    private Integer connectionTimeout;
    private Integer readTimeout;
}
