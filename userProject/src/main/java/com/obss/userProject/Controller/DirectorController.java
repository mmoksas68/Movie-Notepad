package com.obss.userProject.Controller;

import com.obss.userProject.classes.Director;
import com.obss.userProject.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    DirectorService directorService;


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Iterable<Director> listDirectors(){
        return directorService.getDirectors();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public Iterable<Director> searchDirectors(@RequestParam String name)
    {
        return directorService.getDirectorsByName(name);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Director getDirector(@PathVariable("id") Long id){
        return directorService.getDirectorById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean updateDirector(@PathVariable("id") Long id, @RequestBody Director director){
        return directorService.updateDirector(director, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDirector(@PathVariable("id") Long id)
    {
        directorService.delete(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addDirector(@RequestBody Director director){
        if(directorService.addDirector(director) != null)
            return new ResponseEntity<>("Director is added to list", HttpStatus.OK);
        else
            return new ResponseEntity<>("Couldn't add the director", HttpStatus.BAD_REQUEST);

    }

}
