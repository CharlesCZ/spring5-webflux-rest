package org.czekalski.spring5webfluxrest.controllers;

import org.czekalski.spring5webfluxrest.domain.Vendor;
import org.czekalski.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;
    @Before
    public void setUp() throws Exception {
vendorRepository= Mockito.mock(VendorRepository.class);
vendorController= new VendorController(vendorRepository);
webTestClient=WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void listVendors() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Ven").lastName("Vski").build(),
                        Vendor.builder().firstName("Xen").lastName("Xski").build() ));

        webTestClient.get().uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getVendorById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("Ven").lastName("Vski").build()));

        webTestClient.get().uri("/api/v1/vendors/cos")
                .exchange()
                .expectBody(Vendor.class);


    }

    @Test
    public void createVendor(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono=Mono.just(Vendor.builder().lastName("aski").firstName("aa").build());

        webTestClient.post().uri("/api/v1/vendors/")
                .body(vendorToSaveMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();


    }

    @Test
    public void updateVendor(){
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono=Mono.just(Vendor.builder().lastName("aski").firstName("aa").build());


        webTestClient.put().uri("/api/v1/vendors/sadasd")
                .body(vendorToUpdateMono,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

    }



}