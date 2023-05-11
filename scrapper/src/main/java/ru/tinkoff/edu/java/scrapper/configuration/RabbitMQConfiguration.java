package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {
    private final ApplicationProperties properties;

    @Bean
    public DirectExchange scrapperExchange() {
        return new DirectExchange(properties.rabbit().exchange());
    }

    @Bean
    public Queue scrapperQueue() {
        String queueName = properties.rabbit().queue();
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", queueName.concat(".dlx"))
                .build();
    }

    @Bean
    public Binding bindingScrapper(Queue scrapperQueue, DirectExchange scrapperExchange) {
        return BindingBuilder.bind(scrapperQueue).to(scrapperExchange).withQueueName();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        String queueName = properties.rabbit().queue();
        return new FanoutExchange(queueName.concat(".dlx"));
    }

    @Bean
    public Queue deadLetterQueue() {
        String queueName = properties.rabbit().queue();
        return QueueBuilder.durable(queueName.concat(".dlq")).build();
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
