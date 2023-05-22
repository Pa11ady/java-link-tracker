package ru.tinkoff.edu.java.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {
    private final ApplicationConfig appConfig;

    @Bean
    public Queue scrapperQueue() {
        String queueName = appConfig.rabbit().queue();
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", queueName.concat(".dlx"))
                .build();
    }

    @Bean
    public DirectExchange scrapperExchange() {
        return new DirectExchange(appConfig.rabbit().queue());
    }

    @Bean
    public Binding bindingScrapper(Queue scrapperQueue, DirectExchange scrapperExchange) {
        return BindingBuilder.bind(scrapperQueue).to(scrapperExchange).withQueueName();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        String queueName = appConfig.rabbit().queue();
        return new FanoutExchange(queueName.concat(".dlx"));
    }

    @Bean
    public Queue deadLetterQueue() {
        String queueName = appConfig.rabbit().queue();
        return QueueBuilder.durable(queueName.concat(".dlq")).build();
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.LinkUpdate", LinkUpdate.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
