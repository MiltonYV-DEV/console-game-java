import os 
import json
from openai import OpenAI

client = OpenAI()

SYSTEM = (
  "Eres un diseñador de enemigos para un juego por turnos de consola."
  "Crea un enemigo basado en la idea del usuario, pero mantenlo FICTIONALIZADO. "
  # "Si el usuario menciona una persona real o algo polemico, conviertelo en un arquetipo ficticio"
  "Devuelve SOLO JSON válido."
)

def create_enemy(prompt: str) -> dict:
  model = os.getenv("OPENAI_MODEL", "gpt-5")

  user = f"""
  Idea del usuario: {prompt}
  """
