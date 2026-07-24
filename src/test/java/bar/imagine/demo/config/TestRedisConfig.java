package bar.imagine.demo.config;

import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;

// Providing our own RedisConnectionFactory bean makes Spring Boot's RedisAutoConfiguration back
// off (its own bean methods are @ConditionalOnMissingBean), so full-context tests never construct
// a real LettuceConnectionFactory and never attempt a live connection to Redis.
@TestConfiguration
public class TestRedisConfig {
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        return mock(RedisConnectionFactory.class);
    }
}
