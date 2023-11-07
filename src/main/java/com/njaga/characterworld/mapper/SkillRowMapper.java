package com.njaga.characterworld.mapper;

import com.njaga.characterworld.entity.Skill;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SkillRowMapper implements RowMapper<Skill> {

    @Override
    public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Skill skill = new Skill();
        skill.setId(rs.getLong("id"));
        skill.setName(rs.getString("name"));
        skill.setDescription(rs.getString("description"));
        skill.setCreatedDate(rs.getDate("created_date"));
        skill.setUpdatedDate(rs.getDate("updated_date"));
        return skill;
    }
}
