package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.PositionMapper;
import com.dajian.vhrserver.pojo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PositionService {

    @Autowired
    private PositionMapper positionMapper;

    public List<Position> getAllPosition() {
        return positionMapper.getAllPosition();
    }

    public void deleteById(List<Long> ids) {
        for (Long id : ids) {
            positionMapper.deleteById(id);
        }
    }

    public void savePosition(String name) {
        Position position = new Position();
        position.setName(name);
        position.setId(null);
        position.setCreateDate(new Date());
        positionMapper.addPosition(position);
    }

    public void updatePosition(Long id, String name) {
        positionMapper.updatePosition(id, name);
    }
}
