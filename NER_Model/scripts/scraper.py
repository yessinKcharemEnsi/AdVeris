import requests
import time
import json
from pathlib import Path
from clean_text import clean_text

def load_config(config_path="/configs/parsehub_config.json"):
    """Load configuration from an external JSON file."""
    with open(config_path, "r") as file:
        return json.load(file)

def run_parsehub_project(api_key, project_token, start_template, send_email):
    params = {
        "api_key": api_key,
        "start_template": start_template,
        "send_email": "1" if send_email else "0"
    }
    response = requests.post(
        f"https://www.parsehub.com/api/v2/projects/{project_token}/run", data=params
    )
    response.raise_for_status()
    return response.json()["run_token"]

def check_data_ready(api_key, run_token, poll_interval):
    while True:
        response = requests.get(
            f"https://www.parsehub.com/api/v2/runs/{run_token}",
            params={"api_key": api_key}
        )
        response.raise_for_status()
        data_ready = response.json().get("data_ready", False)
        print("Data ready:", data_ready)
        if data_ready:
            break
        time.sleep(poll_interval)

def fetch_scraped_data(api_key, run_token):
    response = requests.get(
        f"https://www.parsehub.com/api/v2/runs/{run_token}/data",
        params={"api_key": api_key, "format": "json"}
    )
    response.raise_for_status()
    return response.json()

def save_to_file(file_path, lines):
    with open(file_path, "a", encoding="UTF-8") as f:
        for line in lines:
            f.write(line + "\n")

def process_and_save_data(data, output_dir, train_file, validate_file, train_split):
    Path(output_dir).mkdir(parents=True, exist_ok=True)
    train_path = Path(output_dir) / train_file
    validate_path = Path(output_dir) / validate_file

    annonces = data.get("annonce", [])
    total = len(annonces)
    train_count = int(total * train_split)

    train_lines = []
    validate_lines = []

    for i, annonce in enumerate(annonces):
        if not annonce or "description" not in annonce:
            continue

        line = annonce["description"].replace("\n", " ")
        line = clean_text(line)

        if i < train_count:
            train_lines.append(line)
        else:
            validate_lines.append(line)

    save_to_file(train_path, train_lines)
    save_to_file(validate_path, validate_lines)

    print(f"Saved {len(train_lines)} lines to {train_path}")
    print(f"Saved {len(validate_lines)} lines to {validate_path}")

def main():
    # Load configuration from external file
    config = load_config()

    run_token = run_parsehub_project(
        config["api_key"],
        config["project_token"],
        config["start_template"],
        config["send_email"]
    )
    print(f"Parsehub scraper with token: {run_token} is running...")

    check_data_ready(config["api_key"], run_token, config["poll_interval"])

    data = fetch_scraped_data(config["api_key"], run_token)
    process_and_save_data(
        data,
        config["output_dir"],
        config["train_file"],
        config["validate_file"],
        config["train_split"]
    )

if __name__ == "__main__":
    main()
