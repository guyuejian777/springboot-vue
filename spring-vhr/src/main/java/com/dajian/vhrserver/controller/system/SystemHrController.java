package com.dajian.vhrserver.controller.system;

import com.dajian.vhrserver.pojo.Hr;
import com.dajian.vhrserver.pojo.PageResult;
import com.dajian.vhrserver.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/hr")
public class SystemHrController {

    @Autowired
    private HrService hrService;

    @GetMapping("{hrId}")
    public ResponseEntity<Hr> getHrById(@PathVariable Long hrId) {
        Hr hr = hrService.getHrById(hrId);
        if (hr == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hr);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteHrById(@PathVariable Long id){
        hrService.deleteHrById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取hrs的分页结果集
     * @param key
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResult<Hr>> queryHrsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows
    ) {
        PageResult<Hr> result = hrService.queryHrsByPage(key, page, rows);
        if (CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/roles")
    public ResponseEntity<Void> updateHrRoles(
            @RequestParam("hrId") Long hrId,
            @RequestParam("rids")List<Long> rids
    ){
        hrService.updateHrRoles(hrId, rids);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/switch")
    public ResponseEntity<Void> updateHrEnabled(Hr hr){
        hrService.updateHrEnabled(hr);
        return ResponseEntity.ok().build();
    }

}
