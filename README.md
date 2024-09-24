# Employee Management Application

This is a Spring Boot application that provides RESTful APIs for managing employee information.


## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Building the Application](#building-the-application)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Continuous Integration and Deployment](#continuous-integration-and-deployment)
- [Technologies Used](#technologies-used)
  
## Introduction

This is a Spring Boot application that provides RESTful APIs for managing employee information.

## Prerequisites

Make sure you have the following installed:


- Java 17
- Maven
- MySQL

## Getting Started

### Building the Application

mvn clean install

## Running the Application

1. Ensure you have JDK 17, Maven, and MySQL installed.
2. Clone this repository.
3. Configure the MySQL database settings in the application properties.
4. Run the application using mvn spring-boot:run.
5. Access the APIs via the defined endpoints to interact with the employee management.

### API Endpoints

| API | Description | Request | Response |
| --- | --- | --- | --- |
| `GET /api/v1/employees` | List all employees from the database | N/A | JSON data |
| `POST /api/v1/employees` | Add a new employee | JSON payload | Success or Failure response |
| `PUT /api/v1/employees/{id}` | Update an existing employee by ID | JSON payload | Success or Failure response |
| `DELETE /api/v1/employees/{id}` | Delete an employee by ID | JSON payload | Success or Failure response |

### Continuous Integration and Deployment

This project includes a GitHub Actions workflow for continuous integration and deployment. The workflow is triggered on pushes to the `feature-pratiksha` branch.

### CI Job (`build`)

This job performs the following tasks:

1. **Checkout code**: Fetch the latest code.
2. **Run Trivy vulnerability scanner**: Scan for vulnerabilities in the codebase.
3. **Set up JDK 17**: Configure Java development kit.
4. **Run unit tests using Maven**: Execute unit tests for the project.
5. **Generate code-coverage report**: Use Jacoco to produce a code-coverage report.
6. **Upload code-coverage report**: Store the code-coverage report as an artifact.
7. **SonarQube Scan**: Analyze code quality using SonarQube.
8. **Check SonarQube Quality Gate**: Ensure that the code meets quality standards.
9. **Build project and package JAR**: Package the application into a JAR file.
10. **Configure AWS credentials (OIDC)**: Set up AWS credentials using OIDC.
    - **Role-to-assume**: Specify the ARN of the IAM role you want to assume.
    - **Role-session-name**: Provide a session name for the assumed role.
    - **AWS region**: Specify the AWS region where your resources are located.
    - **OIDC Steps on AWS Side**:
        - **Create an OIDC Identity Provider (IdP)**:
          - Log in to AWS Management Console.
          - Navigate to the IAM service.
          - Choose "Identity providers" and create a new OIDC IdP.
        - **Create an IAM Role for OIDC Federation**:
          - Create a new IAM role with the necessary permissions.
          - Trust the OIDC IdP you created earlier.
11. **Authenticate AWS with Docker**: Log in to the AWS ECR registry.
12. **Build Docker Image**: Create a Docker image for the application.
13. **Push Docker image to ECR**: Store the Docker image in the AWS ECR repository.
14. **Run Trivy vulnerability scanner on Docker image**: Scan the Docker image for vulnerabilities.

### Deployment Job (`deploy`)

This job deploys the application to Amazon EKS:

1. **Checkout repository**: Fetch the latest code.
2. **Configure AWS Credentials (OIDC)**: Set up AWS credentials using OIDC for deployment.
    - **Role-to-assume**: Specify the ARN of the IAM role you want to assume.
    - **Role-session-name**: Provide a session name for the assumed role.
    - **AWS region**: Specify the AWS region where your resources are located.
3. **Update AWS CLI and kubectl**: Ensure the necessary tools are available.
4. **Replace the tag in deployment.yaml**: Update the Kubernetes deployment file with the new Docker image tag.
5. **Configure kubectl for EKS**: Set up kubectl for Amazon EKS.
6. **Check EKS Connectivity**: Verify connectivity to the EKS cluster.
7. **Set Kubernetes context**: Set the Kubernetes context for deployment.
8. **Deploy to EKS**: Apply the Kubernetes deployment manifest.

### Secrets
Ensure the following secrets are configured in your GitHub repository settings:

- SONAR_PROJECTKEY: SonarQube project key for code analysis.
- SONAR_HOST_URL: SonarQube server URL.
- SONAR_TOKEN: Token for authenticating with SonarQube.
- AWS_IAM_ARN: AWS IAM role ARN for deployment.
- AWS_REGION: AWS region for deployment.
- AWS_ACCOUNT_ID: AWS account ID for deployment.

### Technologies Used
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
  

