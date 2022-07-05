package com.rohit.storemanager.services.impl;

import com.rohit.storemanager.models.ApiResponse;
import com.rohit.storemanager.models.Cart;
import com.rohit.storemanager.models.Medicine;
import com.rohit.storemanager.models.ResponseStatus;
import com.rohit.storemanager.repositories.CartRepository;
import com.rohit.storemanager.repositories.MedicineRepository;
import com.rohit.storemanager.services.MedicineService;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public ApiResponse addMedicine(Medicine medicine) {
        log.info("inside addMedicine()");
        return ApiResponse
                .builder()
                .responseStatus(ResponseStatus.Success)
                .result(medicineRepository.save(medicine))
                .build();
    }

    @Override
    public ApiResponse findAll() {
        List<Medicine> all = medicineRepository.findAll();
        addCartDetailsToProduct(all);
        return ApiResponse
                .builder()
                .responseStatus(ResponseStatus.Success)
                .result(all)
                .build();
    }

    private void addCartDetailsToProduct(List<Medicine> all) {
        List<Cart> userCartDetails = cartRepository.findAllByUserName("SYSTEM");//FIXME
        List<String> cartItems = userCartDetails.stream().map(Cart::getProductName).collect(Collectors.toList());
        all.forEach(product -> {
            if(cartItems.contains(product.getName())) {
                product.setAddedToCart(true);
                product.setCartQuantity(userCartDetails.stream().filter(cart -> cart.getProductName().equalsIgnoreCase(product.getName())).map(Cart::getQuantity).findFirst().orElse(null));
            }
        });
    }

    @Override
    public ApiResponse updateMedicine(Medicine medicine) {
        Optional<Medicine> byName = medicineRepository.findByName(medicine.getName());
        byName.ifPresent(med -> {
            medicine.setId(med.getId());
            addMedicine(medicine);
        });
        ResponseStatus responseStatus;
        responseStatus = byName.isPresent() ? ResponseStatus.Success : ResponseStatus.Failure;

        return ApiResponse
                .builder()
                .responseStatus(responseStatus)
                .message((ResponseStatus.Success == responseStatus) ? "Updated" : String.format("Medicine %s not found", medicine.getName()))
                .build();
    }

    @Override
    @Transactional
    public ApiResponse deleteMedicine(String medicineName) {
        Long deletedItemsCount = medicineRepository.deleteByName(medicineName);
        return ApiResponse
                .builder()
                .responseStatus(deletedItemsCount > 0 ? ResponseStatus.Success : ResponseStatus.Failure)
                .message((deletedItemsCount > 0) ? "Deleted" : String.format("Medicine %s not found", medicineName))
                .build();
    }

    @Override
    public ApiResponse findByLikeName(String name) {
        List<Medicine> allByName = medicineRepository.findAllByNameContainingIgnoreCase(name);
        return ApiResponse
                .builder()
                .responseStatus(ResponseStatus.Success)
                .result(allByName)
                .build();
    }

    @Override
    public ApiResponse findByName(String name) {
        Optional<Medicine> allByName = medicineRepository.findByName(name);
        List<Medicine> result = new ArrayList<>();
        if(allByName.isPresent()) {
           result = Collections.singletonList(allByName.get());
           addCartDetailsToProduct(result);
        }
        return ApiResponse
                .builder()
                .responseStatus(ResponseStatus.Success)
                .result(result)
                .build();
    }

    @Override
    public ApiResponse findByFields(Medicine medicine) {
        List<Medicine> medicines = medicineRepository.findAll(Example.of(medicine));
        return ApiResponse
                .builder()
                .responseStatus(ResponseStatus.Success)
                .result(medicines)
                .build();
    }
}
