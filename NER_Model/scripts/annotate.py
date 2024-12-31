"""annotates raw data to spacy v2 annotations and save it into assets/train.json and assests/validate.json"""
import spacy
import json
from spacy.pipeline import EntityRuler 
import re
import string

#load data
def load_data(file):
    with open(file, "r", encoding=" UTF-8") as f:
        data = json.load(f)
    return (data)


#save data
def save_data(file, data):
    with open (file, "w", encoding=" UTF-8") as f:
        json.dump(data, f, indent=4,ensure_ascii=False)



#annotate with ruler
def test_model(model, text):
    doc = nlp(text)
    results = []
    entities = []
    for ent in doc.ents:
        entities.append((ent.start_char, ent.end_char, ent.label_))
    if len(entities) > 0:
        results = [text, {"entities": entities}]
        return (results)






#create a pipeline that will be used for annotation (using spacy's EntityRuler)
nlp = spacy.load("fr_core_news_sm",exclude='ner')
TRAIN_DATA = []
VALIDATION_DATA = []

#create a ruler and add it to the pipeline
ruler = EntityRuler(nlp)
ruler = nlp.add_pipe("entity_ruler")


#the entity ruler will follow these patterns to match labels
#every time we want our model to be abel to detect new labels , we just add a pattern
patterns = [
            {"label":"NBRE_CHAMBRE", "pattern": [{'LEMMA': 'un','OP': '?'},{'POS': "NUM",'OP': '?'},{'POS': 'ADJ','OP': '?'},{'LEMMA': 'chambre'}]},
            {"label":"NBRE_BATHROOM", "pattern": [{'LEMMA': 'un','OP': '?'},{'POS': "NUM",'OP': '?'},{'POS': 'ADJ','OP': '?'},{"LEMMA" : "salle"} , {"LEMMA" : "de"}, {"LEMMA" : "bain" }]},
            {"label":"NBRE_WC" ,"pattern" : [{'LEMMA': 'un','OP': '?'},{'POS': 'NUM', 'OP': '?'},{"LEMMA": {"IN": ["wc", "toilette"]}}]},
            {"label":"TYPE_CUISINE" ,"pattern" : [{'LEMMA': 'un','OP': '?'},{'POS': 'NUM', 'OP': '?'},{"LEMMA": "cuisine"},{"TEXT": {"IN": ["super", "bien","totalement","completement","full"]}, 'OP': '?'},{'TEXT': {"IN": ["équipée", "ouverte","séjour"]}}]}
               
]



ruler.add_patterns(patterns)

#open file
with open (R"raw_data/train.txt", "r",encoding=" UTF-8")as f:
    text = f.read()

lignes = text.split('\n')
for ligne in lignes :
    results = test_model(nlp, ligne)
    if results != None:
        TRAIN_DATA.append(results)




print("annotated data saved in assets/train.json")
save_data(R"assets/train.json",TRAIN_DATA)



#open file
with open (R"raw_data/validate.txt", "r",encoding=" UTF-8")as f:
    text = f.read()

lignes = text.split('\n')
for ligne in lignes :
    results = test_model(nlp, ligne)
    if results != None:
        VALIDATION_DATA.append(results)




print("annotated data saved in assets/validate.json")
save_data(R"assets/validate.json",VALIDATION_DATA)



