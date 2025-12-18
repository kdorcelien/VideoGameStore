package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {

    private UserDao userDao;
    private ProfileDao profileDao;

    @Autowired
    public ProfileController(UserDao userDao, ProfileDao profileDao) {
        this.userDao = userDao;
        this.profileDao = profileDao;
    }

    @GetMapping()
    public Profile getProfile(Principal principal){
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            Profile profile = profileDao.getProfile(user.getId());

            if (profile == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
            }

            return profile;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping
    public Profile updateProfile(Principal principal, @RequestBody Profile profile) {
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            profileDao.update(user.getId(), profile);


            return profileDao.getProfile(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
