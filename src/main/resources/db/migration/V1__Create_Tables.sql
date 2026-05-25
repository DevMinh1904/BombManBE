-- V1__Create_Tables.sql
-- Database Migrations for BoomBros3D game backend architecture

-- Create Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create Game Sessions table
CREATE TABLE IF NOT EXISTS game_sessions (
    id BIGSERIAL PRIMARY KEY,
    player_ids TEXT NOT NULL,
    winner_id BIGINT,
    duration_seconds INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create Leaderboard table
CREATE TABLE IF NOT EXISTS leaderboards (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    total_wins INT NOT NULL DEFAULT 0,
    total_games INT NOT NULL DEFAULT 0,
    avg_survival_time DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    CONSTRAINT fk_leaderboard_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Indexes for performance optimization (frequently queried columns)
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_leaderboard_wins ON leaderboards(total_wins DESC);
