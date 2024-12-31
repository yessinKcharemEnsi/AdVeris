# filepath: /C:/Users/ASUS/Desktop/projet_stage/projet_stage/NER_Model_revisited/scripts/app.py
from flask import Flask, request, jsonify
import spacy

app = Flask(__name__)

# Load the trained spaCy model
model = spacy.load("output/model-best")

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    text = data.get('text', '')
    doc = model(text)
    entities = [(ent.text, ent.start_char, ent.end_char, ent.label_) for ent in doc.ents]
    return jsonify(entities)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)