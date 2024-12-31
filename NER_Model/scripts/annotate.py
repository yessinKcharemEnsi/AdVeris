import spacy
import json
from spacy.pipeline import EntityRuler

# Load data
def load_data(file):
    with open(file, "r", encoding="UTF-8") as f:
        return json.load(f)

# Save data
def save_data(file, data):
    with open(file, "w", encoding="UTF-8") as f:
        json.dump(data, f, indent=4, ensure_ascii=False)

# Annotate text using the model
def test_model(model, text):
    doc = model(text)
    entities = [(ent.start_char, ent.end_char, ent.label_) for ent in doc.ents]
    if entities:
        return [text, {"entities": entities}]
    return None

# Initialize the pipeline
nlp = spacy.load("fr_core_news_sm", exclude=["ner"])
ruler = nlp.add_pipe("entity_ruler")

# Add patterns to the EntityRuler
patterns = [
    {"label": "NBRE_CHAMBRE", "pattern": [{'LEMMA': 'un', 'OP': '?'}, {'POS': "NUM", 'OP': '?'}, {'POS': 'ADJ', 'OP': '?'}, {'LEMMA': 'chambre'}]},
    {"label": "NBRE_BATHROOM", "pattern": [{'LEMMA': 'un', 'OP': '?'}, {'POS': "NUM", 'OP': '?'}, {'POS': 'ADJ', 'OP': '?'}, {"LEMMA": "salle"}, {"LEMMA": "de"}, {"LEMMA": "bain"}]},
    {"label": "NBRE_WC", "pattern": [{'LEMMA': 'un', 'OP': '?'}, {'POS': 'NUM', 'OP': '?'}, {"LEMMA": {"IN": ["wc", "toilette"]}}]},
    {"label": "TYPE_CUISINE", "pattern": [{'LEMMA': 'un', 'OP': '?'}, {'POS': 'NUM', 'OP': '?'}, {"LEMMA": "cuisine"}, {"TEXT": {"IN": ["super", "bien", "totalement", "completement", "full"]}, 'OP': '?'}, {'TEXT': {"IN": ["équipée", "ouverte", "séjour"]}}]}
]
ruler.add_patterns(patterns)

# Annotate data from raw files
def annotate_file(input_file, output_file, model):
    with open(input_file, "r", encoding="UTF-8") as f:
        text = f.read()

    data = []
    for line in text.split('\n'):
        result = test_model(model, line)
        if result:
            data.append(result)

    save_data(output_file, data)
    print(f"Annotated data saved in {output_file}")

# Annotate training and validation data
annotate_file("raw_data/train.txt", "assets/train.json", nlp)
annotate_file("raw_data/validate.txt", "assets/validate.json", nlp)
