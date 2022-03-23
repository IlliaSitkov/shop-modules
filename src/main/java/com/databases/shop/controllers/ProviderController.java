package com.databases.shop.controllers;

import com.databases.shop.exceptions.provider.NoProviderWithSuchEdrpou;
import com.databases.shop.exceptions.provider.ProviderIllegalArgumentException;
import com.databases.shop.mapstruct.dtos.provider.ProviderGetDto;
import com.databases.shop.mapstruct.dtos.provider.ProviderPostDto;
import com.databases.shop.mapstruct.dtos.provider.ProviderPutDto;
import com.databases.shop.mapstruct.dtos.provider.ProviderSlimGetDto;
import com.databases.shop.mapstruct.mappers.ProviderMapper;
import com.databases.shop.models.Provider;
import com.databases.shop.repositories.queryinterfaces.MinMaxProductsQuantity;
import com.databases.shop.services.interfaces.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@Validated
@RequestMapping("/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProviderMapper providerMapper;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<ProviderGetDto> getProviders(){
        return providerMapper.providersToProvidersGetDto(providerService.getAll());
    }

    @GetMapping("slim")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<ProviderSlimGetDto> getSlimProviders(){
        return providerMapper.providersToProvidersSlimGetDto(providerService.getAll());
    }

    @GetMapping("/{edrpou}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public ProviderGetDto getProvider(@PathVariable("edrpou") Long edrpou) {
        return providerMapper.providerToProviderGetDto(providerService.getProviderByEdrpou(edrpou));
    }

    @DeleteMapping("/{edrpou}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteProvider(@PathVariable("edrpou") Long edrpou) {
        providerService.deleteProvider(edrpou);
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ProviderGetDto addProvider(@Valid @RequestBody ProviderPostDto providerPostDto){
        return providerMapper.providerToProviderGetDto(providerService.addProvider(providerMapper.providerPostDtoToProvider(providerPostDto)));
    }

    @PutMapping("/{edrpou}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ProviderGetDto updateProvider(@PathVariable("edrpou") Long edrpou, @Valid @RequestBody ProviderPutDto providerPutDto) {
        Provider provider = providerMapper.providerPutDtoToProvider(providerPutDto);
        provider.setEdrpou(edrpou);
        return providerMapper.providerToProviderGetDto(providerService.updateProvider(provider));
    }

    @GetMapping("/filterProductsQuantity/{quantity}")
    public Iterable<ProviderGetDto> getProvidersFilteredByProductsQuantity(@PathVariable("quantity") int quantity){
        System.out.println("getProvidersFilteredByProductsQuantity: " + quantity);
        return providerMapper.providersToProvidersGetDto(providerService.getProvidersFilteredByProductsQuantity(quantity));
    }

    @GetMapping("/filterProductsQuantityAndAllSalesmen/{quantity}/{providerName}")
    public Iterable<ProviderGetDto> getProvidersFilteredByProductsQuantityAndAllSalesmenOfProvider(@PathVariable("quantity") Integer quantity, @PathVariable("providerName") String providerName){
        return providerMapper.providersToProvidersGetDto(providerService.getProvidersFilteredByProductsQuantityAndAllSalesmenOfProvider(quantity, providerName));
    }

    @GetMapping("/findName/{providerName}")
    public Iterable<ProviderGetDto> findName(@PathVariable("providerName") String providerName){
        return providerMapper.providersToProvidersGetDto(providerService.findName(providerName));
    }

    @GetMapping("/filterBounds")
    public MinMaxProductsQuantity getFilterBounds() {
        return providerService.getMinMaxProductsQuantity();
    }

    @ExceptionHandler(NoProviderWithSuchEdrpou.class)
    public ResponseEntity<Map<String,String>> handleException(NoProviderWithSuchEdrpou ex){
        Map<String, String> result = new HashMap<>();
        result.put("found", "false");
        result.put("error", ex.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ProviderIllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleOtherExceptions(ProviderIllegalArgumentException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        map.put("error",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

}
