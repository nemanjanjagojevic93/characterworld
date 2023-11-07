CREATE TABLE IF NOT EXISTS Skill(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Race(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `Character`(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    bio TEXT,
    status VARCHAR(255),
    gender VARCHAR(255),
    race_id BIGSERIAL REFERENCES Race(id),
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Character_skills (
    character_id BIGSERIAL REFERENCES `Character`(id),
    skill_id BIGSERIAL REFERENCES Skill(id)
);