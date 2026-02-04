@echo off
set "ROOT_DIR=%~dp0"
set "VERSION=v0.0.1"
set "DOCKER_USER=manishrnl"

echo Launching 6 tabs. Handling paths with spaces...

wt -w 0 nt --title "API Gateway" cmd /k "cd /d \"%ROOT_DIR%api-gateway\" && call mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-api-gateway:%VERSION% . && docker push %DOCKER_USER%/linked-in-app-api-gateway:%VERSION%" ; ^
new-tab --title "Connection Service" cmd /k "cd /d \"%ROOT_DIR%connection-service\" && call mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-connection-service:%VERSION% . && docker push %DOCKER_USER%/linked-in-app-connection-service:%VERSION%" ; ^
new-tab --title "Discovery Service" cmd /k "cd /d \"%ROOT_DIR%discovery-service\" && call mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-discovery-service:%VERSION% . && docker push %DOCKER_USER%/linked-in-app-discovery-service:%VERSION%" ; ^
new-tab --title "Notification Service" cmd /k "cd /d \"%ROOT_DIR%notification-service\" && call mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-notification-service:%VERSION% . && docker push %DOCKER_USER%/linked-in-app-notification-service:%VERSION%" ; ^
new-tab --title "Post Service" cmd /k "cd /d \"%ROOT_DIR%post-service\" && call mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-post-service:%VERSION% . && docker push %DOCKER_USER%/linked-in-app-post-service:%VERSION%" ; ^
new-tab --title "User Service" cmd /k "cd /d \"%ROOT_DIR%user-service\" && call mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-user-service:%VERSION% . && docker push %DOCKER_USER%/linked-in-app-user-service:%VERSION%"

pause