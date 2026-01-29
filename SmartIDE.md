# Step 1 -> Run below command in Termux to run postgresql
```
    pg_ctl -D $PREFIX/var/lib/postgresql start

```
# Step 2 -> Now paste below command inside Smart IDE inside root terminal only

```
    # Linked In App
    
    #!/bin/bash
    
    # 1. Kill all existing Java processes to free up ports and RAM
    echo "🧹 Cleaning up existing Java processes..."
    pkill -f java
    sleep 2
    
    # Updated Services List for your second project (Hyphenated naming)
    # Order: Discovery -> Gateway -> Business Services
    SERVICES=("Discovery-Service" "Api-Gateway" "User-Service" "Post-Service" "Connection-Service")
    
    # 2. Run mvn install for all services
    echo "📦 Building all projects (mvn install)..."
    for service in "${SERVICES[@]}"; do
    if [ -d "$service" ]; then
    echo "🛠️ Installing $service..."
    (cd "$service" && mvn install -DskipTests)
    if [ $? -ne 0 ]; then
    echo "❌ Build failed for $service. Exiting."
    exit 1
    fi
    else
    echo "⚠️ Directory $service not found. Skipping build."
    fi
    done
    
    echo "🚀 Starting Services in Order..."
    
    # 3. Discovery Service (Eureka)
    echo "📡 Launching Discovery-Service..."
    (cd Discovery-Service && mvn spring-boot:run) &
    sleep 45
    
    # 4. API Gateway
    echo "🛣️ Launching Api-Gateway..."
    (cd Api-Gateway && mvn spring-boot:run) &
    sleep 15
    
    # 5. Business Microservices
    echo "👤 Launching User-Service..."
    (cd User-Service && mvn spring-boot:run) &
    
    echo "📝 Launching Post-Service..."
    (cd Post-Service && mvn spring-boot:run) &
    
    echo "🔗 Launching Connection-Service..."
    (cd Connection-Service && mvn spring-boot:run) &
    
    echo "✅ All Linked-In services are starting up!"
    echo "Check Discovery Dashboard at: http://localhost:8761"
    wait


```