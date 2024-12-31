"""Convert entity annotation from spaCy v2 TRAIN_DATA format to spaCy v3
.spacy format."""
import srsly
import typer
import warnings
from pathlib import Path
import pandas as pd

import spacy
from spacy.tokens import DocBin


def convert(lang: str, input_path: Path, output_path: Path):
    nlp = spacy.blank(lang)
    db = DocBin()
    i = 0
    j = 0
    all = 0
    for text, annot in srsly.read_json(input_path):
        doc = nlp.make_doc(text)
        ents = []

        for start, end, label in annot["entities"]:
            all = all+1
            span = doc.char_span(start, end, label=label)
            if span is None:
                i = i + 1
                #msg = f"Skipping entity [{start}, {end}, {label}] in the following text because the character span '{doc.text[start:end]}' does not align with token boundaries:\n\n{repr(text)}\n"
                #warnings.warn(msg)
            else:
                j = j + 1 
                ents.append(span)
        doc.ents = ents
        db.add(doc)
        
    
    print ( "nombre of all = ",all)
    print ( "nombre of entities = ",j)
    print ( "nombre of escaped entities = ",i)
    db.to_disk(output_path)


if __name__ == "__main__":
    typer.run(convert)