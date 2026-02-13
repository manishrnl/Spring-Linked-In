# Linked-In Clone in Spring Boot 🚀

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-Distributed-black?style=flat-square&logo=apachekafka)
![Docker](https://img.shields.io/badge/Docker-Container-blue?style=flat-square&logo=docker)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Orchestration-blue?style=flat-square&logo=kubernetes)

A high-performance, distributed LinkedIn clone built using a **Microservices Architecture**.
This project demonstrates the integration of event-driven communication, graph-based
networking, and advanced observability in a modern Spring Boot ecosystem.

---

## 🏗️ Architecture Overview

The system is decomposed into independent services, ensuring scalability and fault tolerance.
Communication is handled **asynchronously** via Kafka for event-driven tasks and **synchronously** via REST for real-time operations.

### ✨ Key Features
* **Microservices Architecture:** Fully decoupled services including User, Post, Connection, and Notification.
* **Graph Networking (Neo4j):** Utilizes **Neo4j** for the Connection Service to handle complex social graphing and friend-of-friend relationship queries.
* **Event-Driven Messaging:** Using **Apache Kafka** to handle notifications and real-time updates across services.
* **Distributed Tracing:** Integrated **Zipkin** and **Sleuth/Micrometer** to monitor request flows and identify bottlenecks.
* **Caching Layer:** Optimized performance using **Redis** to reduce database load for frequently accessed profile data.
* **Containerization & Orchestration:** Fully Dockerized environment with production-ready **Kubernetes** manifests.

---

## 🛠️ Tech Stack

| Category          | Technology                                                              |
|:------------------|:------------------------------------------------------------------------|
| **Backend** | Java 21, Spring Boot 3.x, Spring Cloud (Gateway, Eureka, Config Server) |
| **Messaging** | Apache Kafka                                                            |
| **Databases** | PostgreSQL (User/Post data), Neo4j (Graph relations)                    |
| **Caching** | Redis                                                                   |
| **Observability** | Zipkin, Spring Boot Actuator                                            |
| **DevOps** | Docker, Docker Compose, Kubernetes (K8s)                                |
| **Build Tool** | Maven                                                                   |

---

## 📂 Project Structure

```text
📁 Spring-Linked-In
    ├── 🚪 Api-Gateway           # Entry point for all client requests (Spring Cloud Gateway)
    ├── 🕸️ Connection-Service    # Networking and graph logic (Powered by Neo4j)
    ├── 🔍 Discovery-Service     # Service Registry (Netflix Eureka Server)
    ├── ☸️ k8s/                  # Kubernetes Deployment & Service YAMLs
    ├── 🔔 notification-service  # Real-time alerts (Kafka Consumer / WebSockets)
    ├── 📝 Post-Service          # Handling posts, likes, and comments (PostgreSQL)
    ├── 👤 User-Service          # Profile and Identity management (PostgreSQL)
    └── 🐳 docker-compose.yml    # Multi-container orchestration for local dev
```
# 🚀 Getting Started

### Prerequisites

* **Java 21+**
* **Docker & Docker Desktop**
* **Maven**
* **Kubernetes Cluster** (Minikube or Kind for local testing)

### 1. Clone the repository

```bash
    git clone https://github.com/manishrnl/Spring-Linked-In.git
    cd Spring-Linked-In

```

### 2. Build the services
```bash
   mvn clean package -DskipTests
```   
### 3. Run with Docker Compose
   - Spin up the infrastructure (Kafka, Zipkin, Redis, Postgres, Neo4j) and the microservices:

```bash
    docker-compose up -d
```
### 4. Deploy to Kubernetes
   - Apply the configurations to your cluster:

```bash
    kubectl apply -f k8s/
```
# 📊 Monitoring & Observability
* **Zipkin UI:**  Access at http://localhost:9411 to visualize latency and trace requests.

* **Service Discovery:** View registered services at the Eureka Dashboard: 
  http://localhost:8761.

* **Health Checks:** Monitor service status via Spring Boot Actuator endpoints: 
  /actuator/health.

# 🤝 Contributing
Contributions are welcome! If you have ideas for improving the graph algorithms in Neo4j or 
enhancing the Kafka stream processing, feel free to submit a Pull Request.

# Maintained by Manish