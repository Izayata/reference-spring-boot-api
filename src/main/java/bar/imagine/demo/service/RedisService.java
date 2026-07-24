package bar.imagine.demo.service;

import java.time.Duration;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final DefaultRedisScript<Long> INCREMENT_WITH_TTL_SCRIPT = new DefaultRedisScript<>(
        "local current = redis.call('incr', KEYS[1])\n" +
        "if current == 1 then\n" +
        "    redis.call('expire', KEYS[1], ARGV[1])\n" +
        "end\n" +
        "return current",
        Long.class
    );

    public Long atomicIncrementWithTtlOnFirstWrite(String key, Duration ttl) {
        return redisTemplate.execute(INCREMENT_WITH_TTL_SCRIPT, List.of(key), String.valueOf(ttl.getSeconds()));
    }
}
