# Data Processor Service

The Data Processor Service is a key component of the real estate advertisement pipeline. It processes the structured data collected by the Scraper Service, transforming and enriching it to meet business requirements. The service ensures that the data is cleansed, validated, and stored in the database for future analysis and retrieval. Additionally, the service leverages **Flowable**, a powerful opensource BPMN (Business Process Model and Notation) engine, to orchestrate and manage the data processing workflows.

## Key Features

- **Workflow Orchestration with Flowable:** Uses Flowable to define, execute, and monitor data processing workflows.
- **Data Transformation:** Converts raw structured data from the Scraper Service into a standardized format.
- **Data Validation:** Implements business rules to ensure the accuracy, completeness, and integrity of the data.
- **Database Integration:** Stores the processed data in the system's database for future use.
- **Error Handling:** Identifies and logs invalid or incomplete data for troubleshooting and resolution.

## Components

### 1. Workflow Management (Powered by Flowable)
- Orchestrates the data processing logic using BPMN workflows.
- Ensures modularity and flexibility in adding or modifying processing steps.

### 2. Data Validation
- Ensures that incoming data complies with predefined business rules.
- Handles missing fields, malformed entries, and duplicate records.

### 3. Data Transformation
- Converts fields, formats, and structures into a standardized schema.
- Applies enrichment to extracted fields (e.g., geolocation from addresses, categorization based on property details).

### 4. Database Storage
- Persists validated and transformed data in a relational database.
- Ensures data indexing for efficient querying and retrieval.

### 5. Integration with Messaging Queue
- Subscribes to the **ActiveMQ** messaging queue to receive scraped data.
- Publishes success or failure acknowledgments to the queue for monitoring.

## Benefits

- **High Data Quality:** Ensures that only valid, clean, and enriched data is stored for downstream processes.
- **Workflow Visibility:** Provides a clear view of data processing steps and their statuses through Flowable.
- **Scalability:** Can handle large volumes of data from multiple scraping jobs simultaneously.
- **Seamless Integration:** Works efficiently with upstream (Scraper Service) and downstream components (e.g., search APIs, analytics tools).

## Configuration

- **Flowable Workflows:** Configurable BPMN workflows to define and manage processing logic.
- **Database Connection:** Configurable database credentials and settings for storage.
- **Validation Rules:** Predefined and extensible validation rules to adapt to changing business needs.
- **Queue Settings:** Integration with ActiveMQ for message consumption and communication.

## Technologies Used

- **Java:** For backend development.
- **Spring Boot:** To implement the service logic and manage database interactions.
- **Hibernate:** For object-relational mapping (ORM) and database operations.
- **ActiveMQ:** For subscribing to messages from the Scraper Service and communicating results.
- **Flowable:** For workflow orchestration and process management.
- **PostgreSQL (or other RDBMS):** As the storage layer for processed data.

## How It Works

1. The service listens to the **ActiveMQ** queue for incoming messages containing scraped data.
2. Upon receiving a message, the data undergoes a Flowable-managed workflow that includes:
   - **Validation:** Ensures it adheres to business rules.
   - **Transformation:** Converts and enriches the data to match the target schema.
3. The processed data is stored in the database.
4. If any issues occur, the service logs errors and publishes failure acknowledgments to the queue for monitoring and retries.

## Processing the data using Flowable
- The follwing is the BPMN diagram that desribes what our data processor will perform with the help of the Flowable Engine:
![Workflow](images/diagram.svg)
- the first class will pass the textual description of the fetched ad to a spaCy NER model to extract details from a textual description of the AD and enrich its feilds (properties of the real estate ad)
- The second class will look if any similarities are detected between the fetched ad, and the already existing ads in the database.
- if any similarities are detected, another class will merge entries between the fetched Ad and the similar Ad detected.
- based on the Database will be updated with the new Ad.


## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- PostgreSQL 12 or higher

## Setup

### Database
1. Install PostgreSQL and create a database:
    ```sql
    CREATE DATABASE futureproof_db;
    ```

2. Update the `application.properties` file with your PostgreSQL configuration:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/futureproof_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```

### Build and Run
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/futureproof.git
    cd futureproof
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Flowable Integration
This project uses Flowable for managing workflows. The following classes are part of the Flowable integration (some of them are stilll not fully implemented)

- `MergeEntries`: Implements the logic for merging new ads with existing ads in the database.
- `UpdateDB`: Updates the database with the merged ad.
- `AddToDB`: Adds a new ad to the database.

### Example Flowable Task
```java
public class MergeEntries implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeEntries.class);
    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("merge entries executing...");
        // Implement merging logic here
    }
}
```
## Future Enhancements

- Expand Flowable workflows to support human machine task to decide of the Ad should be added to DB below a certain threshold.