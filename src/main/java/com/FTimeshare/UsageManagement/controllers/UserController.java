package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.dtos.UserDto;
import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userService;

//    @GetMapping("viewall")
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        List<UserEntity> users = userService.getAllUsers();
//        List<UserDto> userDtos = users.stream()
//                .map(user -> new UserDto(user.getUserID(), user.getUserName(), user.getUserPhone(), user.getUserEmail(),
//                        user.getUserPassword(), user.getUserBirthday(), user.getRoleID().getRoleID()))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok().body(userDtos);
//    }

    //Quý

//
//    @GetMapping("/role/owner")
//    @ResponseBody
//    public List<UserEntity> getUsersByRoleOwner() {
//        System.out.print(userService.getUsersByRole("owner"));
//        return userService.getUsersByRole("owner");
//    }
//
//    @GetMapping("/")
//    @ResponseBody
//    public String hello(){
//        return "hello";
//    }


}


//@Autowired
//private UserService userService;
//@GetMapping("{id}")
//public ResponseEntity<UserEntity> findUser(@PathVariable String id) {
//    Optional<UserEntity> userEntityOptional = userService.findUser(id);
//    return userEntityOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//}
//
//@GetMapping("/admin")
//public ResponseEntity<List<UserEntity>> findAll() {
//    return ResponseEntity.ok(userService.findAll());
//}
//
//@PostMapping("/admin/save")
//public ResponseEntity<UserEntity> save(@RequestBody UserEntity userEntity) {
//    return ResponseEntity.ok(userService.save(userEntity));
//}
//
//@PutMapping("/admin/update")
//public ResponseEntity<UserEntity> update(@RequestBody UserEntity userEntity) {
//    return ResponseEntity.ok(userService.update(userEntity));
//}
//
//@DeleteMapping("/admin/delete")
//public void delete(@RequestBody UserEntity userEntity) {
//    userService.delete(userEntity);
//}