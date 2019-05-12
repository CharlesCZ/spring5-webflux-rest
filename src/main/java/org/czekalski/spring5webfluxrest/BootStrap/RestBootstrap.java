package org.czekalski.spring5webfluxrest.BootStrap;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.czekalski.spring5webfluxrest.domain.Category;
import org.czekalski.spring5webfluxrest.domain.Vendor;
import org.czekalski.spring5webfluxrest.repositories.CategoryRepository;
import org.czekalski.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class RestBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final VendorRepository vendorRepository;
private final CategoryRepository categoryRepository;
    public RestBootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //load data
        log.info("#### LOADING DATA ON BOOTSTRAP #####");
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(),"rest");
        mongoOps.dropCollection("vendor");
        mongoOps.dropCollection("category");


        loadingCategories(mongoOps);
        loadingVendors(mongoOps);


    }

    private void loadingVendors(MongoOperations mongoOps) {

           vendorRepository.save(Vendor.builder()
                   .firstName("Pan Bucik")
                   .lastName("Bucikowski")
                   .build()).block();

           vendorRepository.save(Vendor.builder()
                   .firstName("Pan Samochodzik")
                   .lastName("Samochodzikowski")
                   .build()).block();

           log.info("Loaded Vendors: "+vendorRepository.count().block());


    }

    private void loadingCategories(MongoOperations mongoOps) {

            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Breads").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Eggs").build()).block();

            log.info("Loaded Categories: " + categoryRepository.count().block());

    }
}
