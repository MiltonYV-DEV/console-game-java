from pydatic import BaseModel, field
from typing import Optional

class EnemyCreateRequest(BaseModel):
  prompt: str = Field(..., min_length=1, max_length=300)
  # difficulty: str = Field(default="normal")

class EnemyCreateResponse(BaseModel):
  name: str
  description_in: str
  description_out: str
  dialogs: str[]
