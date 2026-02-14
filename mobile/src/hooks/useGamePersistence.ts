import * as SQLite from "expo-sqlite";
import { GameState } from "../models/types";

const DB_NAME = "raspberrylanes.db";

let db: SQLite.SQLiteDatabase | null = null;

async function getDb(): Promise<SQLite.SQLiteDatabase> {
  if (!db) {
    db = await SQLite.openDatabaseAsync(DB_NAME);
    await db.execAsync(
      `CREATE TABLE IF NOT EXISTS saves (
        id INTEGER PRIMARY KEY NOT NULL DEFAULT 1,
        data TEXT NOT NULL
      );`
    );
  }
  return db;
}

export async function saveGame(state: GameState): Promise<void> {
  const database = await getDb();
  const json = JSON.stringify(state);
  await database.runAsync(
    `INSERT OR REPLACE INTO saves (id, data) VALUES (1, ?);`,
    json
  );
}

export async function loadGame(): Promise<GameState | null> {
  const database = await getDb();
  const row = await database.getFirstAsync<{ data: string }>(
    `SELECT data FROM saves WHERE id = 1;`
  );
  if (row) {
    return JSON.parse(row.data) as GameState;
  }
  return null;
}

export async function hasSavedGame(): Promise<boolean> {
  const database = await getDb();
  const row = await database.getFirstAsync<{ count: number }>(
    `SELECT COUNT(*) as count FROM saves WHERE id = 1;`
  );
  return (row?.count ?? 0) > 0;
}

export async function deleteSave(): Promise<void> {
  const database = await getDb();
  await database.runAsync(`DELETE FROM saves WHERE id = 1;`);
}
