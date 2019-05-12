package org.czekalski.spring5webfluxrest.controllers;

import org.czekalski.spring5webfluxrest.domain.Vendor;
import org.czekalski.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController
{
    private final VendorRepository vendorRepository;


    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }



    @GetMapping("/api/v1/vendors")
    Flux<Vendor> listVendors(){

        return vendorRepository.findAll();
    }


    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getVendorById(@PathVariable String id){

        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    Mono<Void>  create(@RequestBody Flux<Vendor> vendorStream){

        return  vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    Mono<Vendor> update(@PathVariable String id,@RequestBody Vendor vendor){

        return vendorRepository.save(vendor);
    }

}
