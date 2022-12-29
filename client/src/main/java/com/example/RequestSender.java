package com.example;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class RequestSender {
        private final RestTemplate restTemplate = new RestTemplate();
    {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
    }

        public  void sendRequests(){
            List<Long> ids = LongStream.rangeClosed(1,39).boxed().collect(Collectors.toList());
            int threadCount = 5;
            int readQuota = 50;
            int writeQuota = 50;
            List<Long> readIdList = List.copyOf(ids);
            List<Long> writeIdList = List.copyOf(ids);
            for(int i = 0; i < threadCount; i++){
                Thread thread =  new Thread (() -> {
                    System.out.println("Current thread: " + Thread.currentThread().getId());
                    while (true) {
                        double readProbability = (double)readQuota/(double)(readQuota+writeQuota);
                        if (ThreadLocalRandom.current().nextDouble() < readProbability) {
                            getBalance(randomFromList(readIdList));
                        } else {
                            changeBalance(randomFromList(writeIdList), 1L);
                        }
                    }
                });
                thread.start();
            }
        }

        private  Long randomFromList(List<Long> longs){
            return longs
                    .get(ThreadLocalRandom.current().nextInt(longs.size()));
        }
        public  void getBalance(Long id){
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/balance")
                    .queryParam("id", id);
            try {
                ResponseEntity<Long> response = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        entity,
                        Long.class);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        public  void changeBalance(Long id, Long amount){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> entity = new HttpEntity<>(Map.of("id", id, "amount", amount),headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/balance");
            try {
                ResponseEntity<Long> response = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.PUT,
                        entity,
                        Long.class);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
}
