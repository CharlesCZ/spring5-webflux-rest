package org.czekalski.spring5webfluxrest.controllers;

import org.czekalski.spring5webfluxrest.domain.Vendor;
import org.czekalski.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
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
    Mono<Void>  create(@RequestBody Publisher<Vendor> vendorStream){

        return  vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    Mono<Vendor> update(@PathVariable String id,@RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/vendors/{id}")
    Mono<Vendor> patch(@PathVariable String id,@RequestBody Vendor vendor) {

     /*   Vendor foundVendor=vendorRepository.findById(id).block();

      if(vendor.getFirstName()!=null){
foundVendor.setFirstName(vendor.getFirstName());
      }


if(vendor.getLastName()!=null){
    foundVendor.setLastName(vendor.getLastName());
      }

            return vendorRepository.save(foundVendor);*/

        Mono<Vendor> foundVendor= vendorRepository.findById(id).map(vendor1 -> {
         if(vendor.getFirstName()!=null){
             vendor1.setFirstName(vendor.getFirstName());
         }

         if(vendor.getLastName()!=null){
             vendor1.setLastName(vendor.getLastName());
         }

      return vendor1;
     });


        return  vendorRepository.save(foundVendor.block());

    }


}
