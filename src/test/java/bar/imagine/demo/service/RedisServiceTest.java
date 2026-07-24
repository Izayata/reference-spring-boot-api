package bar.imagine.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @InjectMocks
    private RedisService redisService;

    @Test
    void atomicIncrementWithTtlOnFirstWrite_delegatesToRedisTemplateExecute() {
        when(redisTemplate.execute(any(RedisScript.class), eq(List.of("some_key")), eq("3600")))
            .thenReturn(1L);

        Long result = redisService.atomicIncrementWithTtlOnFirstWrite("some_key", Duration.ofHours(1));

        assertEquals(1L, result);
        verify(redisTemplate).execute(any(RedisScript.class), eq(List.of("some_key")), eq("3600"));
    }
}
