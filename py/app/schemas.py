from pydantic import BaseModel, Field
from typing import Optional, List

class Attack(BaseModel):
  name: str = Field(..., min_length=1)
  damage: int = Field(..., ge=1, le=999)
  description: str = Field(..., min_length=1)

class EnemyInfo(BaseModel):
  name: str = Field(..., min_length=1)
  alias: str = Field(..., min_lenght=1)
  description: str = Field(..., min_length=1)
  opening_dialogue: str = Field(..., min_length=1)

class GenerateEnemyRequest(BaseModel):
  enemy_input: str = Field(..., min_length=1, max_length=120)
  min_damage: int = Field(10, ge=2, le=999)
  max_damage: int = Field(25, ge=2, le=999)
  force_super_strongest: bool = True

class EnemyPayload(BaseModel):
  enemy: EnemyInfo
  enemy_attacks: List[Attack] = Field(..., min_length=3, max_length=3)
  player_attacks: List[Attack] = Field(..., min_length=3, max_length=3)
