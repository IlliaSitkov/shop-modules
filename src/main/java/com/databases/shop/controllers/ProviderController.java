package com.databases.shop.controllers;

import com.databases.shop.exceptions.provider.NoProviderWithSuchEdrpou;
import com.databases.shop.exceptions.provider.ProviderIllegalArgumentException;
import com.databases.shop.models.Provider;
import com.databases.shop.services.implementations.ProviderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    @Autowired
    private ProviderServiceImpl providerService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Iterable<Provider> getProviders(){
        return providerService.getAll();
    }

    @GetMapping("/{edrpou}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SALESMAN') or hasRole('CUSTOMER')")
    public Provider getProvider(@PathVariable("edrpou") Long edrpou) {
        return providerService.getProviderByEdrpou(edrpou);
    }

    @DeleteMapping("/delete/{edrpou}")
    //@PreAuthorize("hasRole('ADMIN')")
    public void deleteProvider(@PathVariable("edrpou") Long edrpou) {
        providerService.deleteProvider(edrpou);
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public Provider addProvider(@Valid @RequestBody Provider provider){
        return providerService.addProvider(provider);
    }

    @PutMapping("/{edrpou}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Provider updateProvider(@PathVariable("edrpou") Long edrpou, @Valid @RequestBody Provider provider) {
        provider.setEdrpou(edrpou);
        return providerService.updateProvider(provider);
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
