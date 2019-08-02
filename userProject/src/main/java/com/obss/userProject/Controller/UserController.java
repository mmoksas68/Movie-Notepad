package com.obss.userProject.Controller;

import com.obss.userProject.classes.MyUser;

import com.obss.userProject.payload.UserSummary;
import com.obss.userProject.security.CurrentUser;
import com.obss.userProject.security.UserPrincipal;
import com.obss.userProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<MyUser> allUsers(){
        Iterable<MyUser> list =  userService.users();
        return list;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public MyUser findUserUsername(@RequestParam("username") String username){
        return userService.findUserByUsername(username);
    }

    @GetMapping(value ="/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MyUser findUserById(@PathVariable Long id)
    {
        return userService.findUserById(id);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean updateUser(@PathVariable("id") Long id, @RequestBody MyUser user){
        return userService.updateUser(user, id);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

}
