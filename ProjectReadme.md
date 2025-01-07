# Real Estate Advertisement Scraping and Processing System

This project involves scraping real estate advertisements from various websites, processing the scraped data, and feeding it into a database that serves as the source of information for a web and mobile application. It integrates various technologies like web scraping, Named Entity Recognition (NER), and business process automation.

## Table of Contents
- [General Overview](#general-overview)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## General Overview

This project is designed to automate the process of scraping real estate advertisements, processing the data (including extracting key properties using NER), and storing the information in a structured database. The system integrates the following key components:
- **Web Scraping**: Using Parsehub to scrape real estate websites.
- **Data Processing**: Extracting named entities from the textual description of the advertisements using `spaCy` NER to enrich properties if the Ad.
- **Business Process Management**: Using Flowable to automate business processes that handle the scraped and processed data
- **Database Integration**: Saving processed data in a database.

## Technologies Used
- **Parsehub**: A web scraping tool used to extract real estate ads from websites.
- **spaCy**: A Python library for Natural Language Processing (NLP) used for Named Entity Recognition (NER) to extract property details.
- **Flowable**: A business process management engine that orchestrates tasks based on a BPMN model.
- **Spring Boot**: Used for building the backend APIs and services.
- **ActiveMQ**: A messaging broker that handles communication between different parts of the system.

## Features
- **Real Estate Ad Scraping**: Scrape data from specified websites and collect advertisements in a structured format (JSON) using Parsehub.
- **Data Processing**: Use spaCy for NER to extract details from textual description of the advertisement like the number of rooms, kitchen type, etc.
- **Data Merging**: Automatically check if an advertisement already exists in the database and merge data if necessary.
- **Scalability**: The system can easily be extended to scrape additional websites or incorporate more data properties in the NER model.

## System Architecture

The architecture of the system is divided into two main components:
1. **Scraping**:
    - Parsehub scrapes the real estate websites.
    - Data is collected and stored in a structured format (JSON) and pushed to an ActiveMQ queue.

2. **Processing**:
    - Spring Boot applications listens to the ActiveMQ queue.
    - Flowable orchestrates the processing of the data by running a series of tasks like calling the NER model, checking for duplicates, and saving the final data to the database.

### Diagram
*(Include a diagram of the system architecture here)*

## Setup and Installation

### Prerequisites
- Java 8 or higher
- Python 3.6+
- Apache ActiveMQ
- Spring Boot
- spaCy

### Install Dependencies
1. **Backend Setup**:
    - Clone the repository:
      ```bash
      git clone https://github.com/yourusername/real-estate-scraping.git
      ```
    - Navigate to the project directory:
      ```bash
      cd real-estate-scraping
      ```
    - Install Spring Boot dependencies:
      ```bash
      mvn clean install
      ```
2. **Web Scraping**:
    - Sign up for Parsehub and set up a scraping project.
    - Configure your project to scrape real estate ads and export data in JSON format.

3. **NER Model**:
    - Install `spaCy`:
      ```bash
      pip install spacy
      ```
    - Download the spaCy model:
      ```bash
      python -m spacy download en_core_web_sm
      ```


