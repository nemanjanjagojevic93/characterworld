package com.njaga.characterworld.mapper;

import com.njaga.characterworld.entity.Race;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RaceRowMapper implements RowMapper<Race> {

    @Override
    public Race mapRow(ResultSet rs, int rowNum) throws SQLException {
        Race race = new Race();
        race.setId(rs.getLong("id"));
        race.setName(rs.getString("name"));
        race.setDescription(rs.getString("description"));
        race.setCreatedDate(rs.getDate("created_date"));
        race.setUpdatedDate(rs.getDate("updated_date"));
        return race;
    }
}
