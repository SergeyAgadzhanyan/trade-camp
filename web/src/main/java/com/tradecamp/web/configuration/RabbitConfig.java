package com.tradecamp.web.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tradecamp.models.util.RabbitVar.*;


@Configuration
public class RabbitConfig {

    @Bean
    public Declarables declarables() {
        Queue userQueue = new Queue(USER_QUEUE);
        Queue stockQueue = new Queue(STOCK_QUEUE);

        Exchange user_ex = ExchangeBuilder.directExchange(USER_EXCHANGE).build();
        Exchange stock_ex = ExchangeBuilder.directExchange(STOCK_EXCHANGE).build();


        return new Declarables(
                userQueue,
                stockQueue,
                user_ex,
                stock_ex,
                BindingBuilder
                        .bind(userQueue)
                        .to(user_ex)
                        .with(USER_ROUTING_KEY).noargs(),
                BindingBuilder
                        .bind(stockQueue)
                        .to(stock_ex)
                        .with(STOCK_ROUTING_KEY).noargs()
        );

    }
}
