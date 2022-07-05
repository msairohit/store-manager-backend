package com.rohit.storemanager.services;

import com.rohit.storemanager.models.ApiResponse;
import com.rohit.storemanager.models.Medicine;

public interface MedicineService {
    ApiResponse addMedicine(Medicine medicine);

    ApiResponse findAll();

    ApiResponse updateMedicine(Medicine medicine);

    ApiResponse deleteMedicine(String medicineName);

    ApiResponse findByLikeName(String name);

    ApiResponse findByName(String name);

    ApiResponse findByFields(Medicine medicine);
}
