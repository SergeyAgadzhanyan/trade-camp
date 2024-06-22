package com.tradecamp.web.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tradecamp.models.util.RabbitVar.*;

@Configuration
public class RabbitConf {

    @Bean
    public Declarables declarables() {
        Queue userFind = new Queue(USER_FIND_QUEUE);
        Queue userCreate = new Queue(USER_CREATE_QUEUE);
        Queue userDelete = new Queue(USER_DELETE_QUEUE);
        Queue stockRandom = new Queue(STOCK_RANDOM_QUEUE);

        Exchange user_ex = ExchangeBuilder.directExchange(USER_EXCHANGE).build();
        Exchange stock_ex = ExchangeBuilder.directExchange(STOCK_EXCHANGE).build();


        return new Declarables(
                userFind,
                userCreate,
                userDelete,
                stockRandom,
                user_ex,
                stock_ex,
                BindingBuilder
                        .bind(userFind)
                        .to(user_ex)
                        .with(USER_ROUTING_KEY_FIND).noargs(),
                BindingBuilder
                        .bind(userDelete)
                        .to(user_ex)
                        .with(USER_ROUTING_KEY_DELETE).noargs(),
                BindingBuilder
                        .bind(userCreate)
                        .to(user_ex)
                        .with(USER_ROUTING_KEY_CREATE).noargs(),
                BindingBuilder
                        .bind(stockRandom)
                        .to(stock_ex)
                        .with(STOCK_ROUTING_KEY_RANDOM).noargs()
        );

    }
}
