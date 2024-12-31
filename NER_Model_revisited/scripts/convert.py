"""Convert entity annotation from spaCy v2 TRAIN_DATA format to spaCy v3
.spacy format."""
import srsly
from pathlib import Path
import spacy
from spacy.tokens import DocBin


def convert(lang: str, input_path: Path, output_path: Path):
    nlp = spacy.blank(lang)
    db = DocBin()

    total_entities = 0
    valid_entities = 0
    skipped_entities = 0

    for text, annot in srsly.read_json(input_path):
        doc = nlp.make_doc(text)
        ents = []

        for start, end, label in annot["entities"]:
            total_entities += 1
            span = doc.char_span(start, end, label=label)
            if span:
                valid_entities += 1
                ents.append(span)
            else:
                skipped_entities += 1

        doc.ents = ents
        db.add(doc)

    print(f"Total entities: {total_entities}")
    print(f"Valid entities: {valid_entities}")
    print(f"Skipped entities: {skipped_entities}")
    db.to_disk(output_path)


if __name__ == "__main__":
    import typer
    typer.run(convert)
