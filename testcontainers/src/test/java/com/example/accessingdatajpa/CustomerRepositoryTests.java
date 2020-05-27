/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.accessingdatajpa;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.OracleContainer;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=update"})
@DataJpaTest
public class CustomerRepositoryTests {

    public static final String IMG_ORACLE = "oracleinanutshell/oracle-xe-11g:1.0.0";

    @ClassRule
    public static OracleContainer oracleContainer = new OracleContainer(IMG_ORACLE);

    @BeforeAll
    public static void startup() {
        oracleContainer.start();
    }

    @AfterAll
    public static void shutdown(){
        oracleContainer.stop();
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customers;

    @TestConfiguration
    static class OracleTestConfiguration {

        @Bean
        DataSource dataSource() {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(oracleContainer.getJdbcUrl());
            hikariConfig.setUsername(oracleContainer.getUsername());
            hikariConfig.setPassword(oracleContainer.getPassword());

            return new HikariDataSource(hikariConfig);
        }
    }

    @Test
    public void testFindByLastName() {
        Customer customer = new Customer("first", "last");
        entityManager.persist(customer);

        List<Customer> findByLastName = customers.findByLastName(customer.getLastName());

        assertThat(findByLastName).extracting(Customer::getLastName).containsOnly(customer.getLastName());
    }
}
