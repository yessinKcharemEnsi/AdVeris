# NER Model Project

This project is focused on training a Named Entity Recognition (NER) model using spaCy. The project includes scripts for data preparation, model training, and exposing the trained model via an API for predictions.

## Project Structure

- `project.yml`: Defines the data assets, commands, and workflows for the project.
- `scripts/`: Contains various scripts for data preparation, conversion, and prediction.
  - `annotate.py`: Annotates raw data.
  - `convert.py`: Converts annotated data to spaCy's binary format.
  - `predict.py`: Provides a function to make predictions using the trained model.
  - `app.py`: Exposes the trained model via a Flask API.
- `raw_data/`: Contains raw data files.
- `assets/`: Contains annotated data files.
- `corpus/`: Contains converted data files in spaCy's binary format.
- `output/`: Contains the trained model.

## Commands

The following commands are defined in the `project.yml` file. They can be executed using `spacy project run [name]`. Commands are only re-run if their inputs have changed.

| Command  | Description                                 |
| -------- | ------------------------------------------- |
| `convert`| Convert the data to spaCy's binary format   |
| `train`  | Train a named entity recognition model      |

## Workflows

Workflows are sequences of commands executed in order. You can run them via `spacy project run [workflow]`.

| Workflow | Description                                 |
| -------- | ------------------------------------------- |
| `all`    | Runs the `convert` and `train` commands     |

## Setup

1. **Install Dependencies**:
   ```sh
   pip install -r requirements.txt