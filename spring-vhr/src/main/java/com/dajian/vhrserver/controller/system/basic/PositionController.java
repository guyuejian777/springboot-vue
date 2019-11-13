package com.dajian.vhrserver.controller.system.basic;

import com.dajian.vhrserver.pojo.Position;
import com.dajian.vhrserver.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/basicMsg/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/all")
    public ResponseEntity<List<Position>> getAllPosition(){
        List<Position> positions = positionService.getAllPosition();
        if (CollectionUtils.isEmpty(positions)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(positions);
    }

    @DeleteMapping("{ids}")
    public ResponseEntity<Void> deleteById(@PathVariable("ids") List<Long> ids){
        positionService.deleteById(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> savePosition(@RequestParam("name") String name){
        List<String> names = queryNames(name);
        if ( names.contains(name)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        positionService.savePosition(name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePosition(@RequestParam("id") Long id,@RequestParam("name") String name){
        List<String> names = queryNames(name);
        if ( names.contains(name)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        positionService.updatePosition(id,name);
        return ResponseEntity.noContent().build();
    }

    private List<String> queryNames(String name){
        List<Position> allPosition = positionService.getAllPosition();
        List<String> names = new ArrayList<>();
        allPosition.forEach( position -> {
            names.add(position.getName());
        });
        return names;
    }
}
