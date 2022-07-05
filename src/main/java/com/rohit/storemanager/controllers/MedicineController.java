package com.rohit.storemanager.controllers;

import com.rohit.storemanager.models.ApiResponse;
import com.rohit.storemanager.models.Medicine;
import com.rohit.storemanager.services.MedicineService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {

    //TODO: Add field specific search using ExampleOf

    @Autowired
    private MedicineService medicineService;

    @PostMapping(consumes = {"multipart/form-data"}/*consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}*/)
    @CrossOrigin(origins = "http://localhost:4200")
    public ApiResponse add(@RequestBody Medicine medicine/*, @RequestParam("image") MultipartFile image*/) throws IOException {
        MultipartFile image = medicine.getPhoto();
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        medicine.setPhotoName(fileName);

        ApiResponse apiResponse = medicineService.addMedicine(medicine);

        String uploadDir = "user-photos/" + ((Medicine) apiResponse.getResult()).getId();
        this.saveFile(uploadDir, fileName, image);

        return apiResponse;
    }

    @GetMapping
//    @CrossOrigin
    //FIXME: Show Out of stock for 0 quantity.
    //TODO: Can use AOP
    public ApiResponse findAll() {
        //TODO: add pagination
        //TODO: Add field specific search.
        return medicineService.findAll();
    }


    @GetMapping("like/{name}")
    public ApiResponse findAllByLikeName(@PathVariable(value = "name") String name) {
        return medicineService.findByLikeName(name);
    }

    @GetMapping("/{name}")
    public ApiResponse findAllByName(@PathVariable(value = "name") String name) throws IOException {
        ApiResponse byName = medicineService.findByName(name);
        Medicine medicine = ((List<Medicine>) byName.getResult()).get(0);
        String uploadDir = "user-photos/" + medicine.getId();
        String[] list = new File(uploadDir).list();
        File file = null;
        if (list != null) {
            file = new File(uploadDir + "/" + list[0]);
        }
        if (file != null) {
            String name1 = file.getName();
            medicine.setPhotoName(name1);
            FileInputStream input = new FileInputStream(file);
            /*MultipartFile multipartFile = new MockMultipartFile("file",
                    file.getName(), "text/plain", IOUtils.toByteArray(input));*/
            /*MultipartFile multipartFile = new MockMultipartFile("file", name1, "text/plain", input*//*IOUtils.toByteArray(input)*//*);
            medicine.setPhoto(multipartFile);*/
        }
        return byName;
    }

    @PutMapping
    public ApiResponse update(@RequestBody Medicine medicine) {
        //FIXME: handle already added cart items if we update price or any other details of the product. like show a message or something in cart mentioning that the data is changed for the product u added in the cart.
        return medicineService.updateMedicine(medicine);
    }
    @DeleteMapping("/{medicineName}")
    public ApiResponse delete(@PathVariable("medicineName") String medicineName) {
        return medicineService.deleteMedicine(medicineName);
    }

    @PostMapping("/fieldSearch")
    public ApiResponse findAllWithFieldSpecificSearch(@RequestBody Medicine medicine) {
        return medicineService.findByFields(medicine);
    }

    @PostMapping("/file-upload")
    public ApiResponse uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
/*        user.setPhotos(fileName);

        User savedUser = repo.save(user);*/

        String uploadDir = "user-photos/" + 1/*savedUser.getId()*/;

        this.saveFile(uploadDir, fileName, file);
        return null;
    }

    public void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
