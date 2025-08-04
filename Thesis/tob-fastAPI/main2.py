import torch.nn.functional as F
from fastapi import FastAPI
from pydantic import BaseModel
import torch
from torch import nn
import enchant

class LSTMWordPredictor(nn.Module):
    def __init__(self, input_size, hidden_size, output_size):
        super(LSTMWordPredictor, self).__init__()
        self.embedding = nn.Embedding(input_size, hidden_size)
        self.lstm = nn.LSTM(hidden_size, hidden_size, batch_first=True)
        self.fc = nn.Linear(hidden_size, output_size)
    
    def forward(self, x, hidden=None):
        x = self.embedding(x)
        out, hidden = self.lstm(x, hidden)
        out = self.fc(out)
        return out, hidden

class GenerateRequest(BaseModel):
    input_word: str
    previous_word: str

char_to_idx = torch.load("char_to_idx.pt",weights_only=True)
idx_to_char = torch.load("idx_to_char.pt",weights_only=True) 
max_len = 20 

model = LSTMWordPredictor(input_size=len(char_to_idx), hidden_size=128, output_size=len(char_to_idx))
model.load_state_dict(torch.load("lstm_word_predictor.pth", weights_only=True))
model.eval()

app = FastAPI()

def generate_word(model, start_char, char_to_idx, idx_to_char, max_len=20):
    model.eval()
    if start_char not in char_to_idx:
        return "Error: Invalid start character"
    
    input_seq = torch.tensor([[char_to_idx[start_char]]])
    generated_word = start_char
    hidden = None  
    
    with torch.no_grad():
        for _ in range(max_len):
            output, hidden = model(input_seq, hidden)

            # kimenetet konvertalasa valoszinusegekke
            probabilities = F.softmax(output[:, -1, :], dim=1)
            
            
            next_idx = torch.multinomial(probabilities, num_samples=1).item()
            next_char = idx_to_char[next_idx]
            
            if next_char == "<PAD>":
                break

            generated_word += next_char
            input_seq = torch.tensor([[next_idx]])  
    
    return generated_word

@app.post("/generate-v1")
def generate(request: GenerateRequest):
    dictionary = enchant.Dict("en_US")
    input_word = request.input_word.lower().strip()
    previous_word = request.previous_word.strip().lower()

    if not dictionary.check(input_word):
       return {"valid": False, "message": f"The word '{input_word}' is not valid."}
    
    if previous_word and previous_word[-1] != input_word[0]:
        return {"valid": False, "message": f"The word must start with '{previous_word[-1]}'."}

    start_char = input_word[-1]
    if start_char not in char_to_idx:
        return {"valid": False, "message": f"Cannot generate a word from character '{start_char}'."}

    # Szó generálás
    generated_word = generate_word(model, start_char, char_to_idx, idx_to_char, max_len)
    while not dictionary.check(generated_word):
        generated_word = generate_word(model, start_char, char_to_idx, idx_to_char, max_len)

    return {"valid": True, "generated_word": generated_word}
