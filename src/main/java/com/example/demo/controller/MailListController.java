package com.example.demo.controller;

import com.example.demo.model.MailList;
import com.example.demo.model.MyResponse;
import com.example.demo.model.PagenatedData;
import com.example.demo.model.Product;
import com.example.demo.repository.MailListRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
@RestController
@RequestMapping("subscription")
public class MailListController {
    @Autowired
    MailListRepository repository;

    @GetMapping("/subscriptions")
    @Operation(summary = "query products by sorting and pagination and filter by name.")
    public MyResponse getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false, defaultValue = "true") boolean isDesc
    ) {
        pageIndex--;
        Pageable paging;
        if(orderBy!=null){
            if(isDesc){
                paging = PageRequest.of(pageIndex, pageSize, Sort.by(orderBy).descending());
            }else{
                paging = PageRequest.of(pageIndex, pageSize, Sort.by(orderBy).ascending());
            }
        }else{
            paging = PageRequest.of(pageIndex, pageSize);
        }
        Page<MailList> pageTuts;
        if (search == null)
            pageTuts = repository.findAll(paging);
        else
            pageTuts = repository.findByEmailContaining(search, paging);

        PagenatedData<MailList> result = new PagenatedData<>(pageTuts.getContent(),pageTuts.getNumber()+1, pageSize,pageTuts.getTotalElements(),pageTuts.getTotalPages());

        return MyResponse.ok(result);

    }



    @GetMapping("{id}")
    @Operation(summary = "Retrieve one subscription")
    public MyResponse getProduct(@Parameter(description = "ID of subscription to be retrieved", required = true)
                                 @PathVariable int id) {
        MailList mailList = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("subscription id not found - " + id));
        return MyResponse.ok(mailList);
    }

    @PostMapping("")
    @Operation(summary = "add a new subscription")
    public MyResponse addSubscription(@RequestBody MailList subscription) {
        subscription.setId(0);
        Instant now = Instant.now();
        subscription.setCreateDate(now);
        return MyResponse.ok(repository.save(subscription));
    }




    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "404", description = "email not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    @Operation(summary = "delete a subscription via id")
    public MyResponse deleteProduct(@PathVariable int id) {
        MailList mailList = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("subscription id not found - " + id));
        repository.delete(mailList);
        return MyResponse.ok("Deleted subscription with id: " + id);
    }
}
