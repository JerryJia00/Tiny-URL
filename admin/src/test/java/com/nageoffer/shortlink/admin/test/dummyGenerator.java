/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nageoffer.shortlink.admin.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dummyGenerator {


    private static WebClient.Builder webClientBuilder = WebClient.builder();;


    public static void main(String[] args) {
        WebClient webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000").build();

        // Generate dummy data
        Faker faker = new Faker();
        List<Map<String, String>> dummyDataList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Map<String, String> data = new HashMap<>();
            data.put("username", faker.name().username());
            data.put("password", faker.internet().password());
            data.put("realName", faker.name().fullName());
            data.put("phone", faker.phoneNumber().cellPhone());
            data.put("mail", faker.internet().emailAddress());
            dummyDataList.add(data);
        }

        // Post all dummy data
        for (Map<String, String> data : dummyDataList) {
            webClient.post()
                    .uri("/api/short-link/admin/v1/user")
                    .header("Content-Type", "application/json")
                    .header("Transfer-Encoding", "chunked")
                    .bodyValue(data)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> System.out.println("Response: " + response))
                    .doOnError(error -> System.err.println("Failed to insert data: " + error.getMessage()))
                    .subscribe();
        }
    }
}
