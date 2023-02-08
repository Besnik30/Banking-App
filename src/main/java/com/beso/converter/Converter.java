package com.beso.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public interface Converter <R, E>{

     @Bean
     E toEntity(R resource) ;
     @Bean
     R fromEntity(E entity);
}
