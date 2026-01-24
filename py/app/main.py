import os
from fastapi import FastAPI, HTTPException, Request, Body
from pydantic import BaseModel, Field
from .schemas import EnemyPayload, GenerateEnemyRequest
from dotenv import load_dotenv

load_dotenv()

from openai import OpenAI

app = FastAPI(title="generador de enemigos con ChatGPT")

client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))


SYSTEM_RULES = (
  "Eres generador de un enemigo parodiado con temas controversiales que haya tenido, es para un minijuego por turno "
  "El tono es parodia ,gracioso y de tono familiar"
  "Todos los ataques tiene que ser solo daño, nada de efectos de stun, buffs o curacion etc."
  "Los ataques del enemigo deben ser relacionados con su vida y pueden ser frases, momentos, situaciones etc, tiene que ser algo graciosos"
  "los ataques del jugador tienen que ser momentos, situaciones graciosos del enemigo"
  "Retornaras el contenido en solamente en español, pero las llaves JSON en ingles"
)

ENEMY_JSON_SCHEMA = {
  "name": "turn_based_enemy",
  "schema": {
    "type": "object",
    "additionalProperties": False,
    "required": ["enemy", "enemy_attacks", "player_attacks"],
    "properties": {
      "enemy": {
        "type": "object",
        "additionalProperties": False,
        "required": ["name", "alias", "opening_dialogue", "description"],
        "properties": {
          "name": {"type": "string"},
          "alias": {"type": "string"},
          "opening_dialogue": {"type": "string"},
          "description": {"type": "string"}
        },
      },
      "enemy_attacks": {
        "type": "array",
        "minItems": 3,
        "maxItems": 3,
        "items": {
          "type": "object",
          "additionalProperties": False,
          "required": ["name", "damage", "description"],
          "properties": {
            "name": {"type": "string"},
            "damage": { "type": "integer"},
            "description": {"type": "string"}
          },
        },
      },
      "player_attacks": {
        "type": "array",
        "minItems": 3,
        "maxItems": 3,
        "items": {
            "type": "object",
          "additionalProperties": False,
          "required": ["name", "damage", "description"],
          "properties": {
            "name": {"type": "string"},
            "damage": {"type": "integer"},
            "description": {"type": "string"},
          },
        },
      },
    },
  },
}

@app.get("/")
def hello_world():
  return {"msj": "Hola desde el backend"}

@app.post("/generate-enemy", response_model=EnemyPayload)
def generate_enemy(req: GenerateEnemyRequest):
  if req.min_damage > req.max_damage:
    raise HTTPException(status_code=400, detail="min_damage no puede ser mayor a max_damage")

  try:
    res = client.responses.create(
      model="gpt-4o-mini",
      instructions=SYSTEM_RULES,
      input=[
        {
          "role": "user",
          "content": (
            f'Generar enemigo con las indicaciones que el usuario envio: {req.prompt_user}.'
            f"Rango del dano: [{30}, {45}]."
            "La descripcion de enemigo generado tiene que ser una narracion de como entra en combate con alguna de sus frases mas conocidas y un poco gracioso"
            "Retorna SOLO el JSON exacto del schema."
            "Ataques enemigos: exactamente 3, solo dano."
            "Ataques del jugador: exacamente 3, solo dano."
          ),
        }
      ],
      text={
        "format": {
          "type": "json_schema",
          "name": ENEMY_JSON_SCHEMA["name"],
          "strict": True,
          "schema": ENEMY_JSON_SCHEMA["schema"],
        }
      },
      store=False
    )

    raw_json_text = res.output_text
    payload = EnemyPayload.model_validate_json(raw_json_text)

    return payload

  except ValueError as e:
    raise HTTPException(status_code=422, detail=str(e))
  except Exception as e:
    raise HTTPException(status_code=500, detail=f"OpenAI error: {e}")



@app.post("/generate-enemy-test")
async def generate_enemy_raw(req: GenerateEnemyRequest):
  print(req)
  return {"msg": req}
