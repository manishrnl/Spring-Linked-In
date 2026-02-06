@echo off
setlocal EnableDelayedExpansion

set "ROOT_DIR=%~dp0"
set "VERSION=v2.1.0"
set "DOCKER_USER=manishrnl"


echo Launching 6 tabs in Windows Terminal...

wt ^
  -w 0 nt -d "%ROOT_DIR%api-gateway" --title "API Gateway" cmd /k "mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-api-gateway:%VERSION% . && docker tag %DOCKER_USER%/linked-in-app-api-gateway:%VERSION% %DOCKER_USER%/linked-in-app-api-gateway:latest && docker push %DOCKER_USER%/linked-in-app-api-gateway:%VERSION% && docker push %DOCKER_USER%/linked-in-app-api-gateway:latest" ; ^
  nt -d "%ROOT_DIR%connection-service" --title "Connection" cmd /k "mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-connection-service:%VERSION% . && docker tag %DOCKER_USER%/linked-in-app-connection-service:%VERSION% %DOCKER_USER%/linked-in-app-connection-service:latest && docker push %DOCKER_USER%/linked-in-app-connection-service:%VERSION% && docker push %DOCKER_USER%/linked-in-app-connection-service:latest" ; ^
  nt -d "%ROOT_DIR%discovery-service" --title "Discovery" cmd /k "mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-discovery-service:%VERSION% . && docker tag %DOCKER_USER%/linked-in-app-discovery-service:%VERSION% %DOCKER_USER%/linked-in-app-discovery-service:latest && docker push %DOCKER_USER%/linked-in-app-discovery-service:%VERSION% && docker push %DOCKER_USER%/linked-in-app-discovery-service:latest" ; ^
  nt -d "%ROOT_DIR%notification-service" --title "Notification" cmd /k "mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-notification-service:%VERSION% . && docker tag %DOCKER_USER%/linked-in-app-notification-service:%VERSION% %DOCKER_USER%/linked-in-app-notification-service:latest && docker push %DOCKER_USER%/linked-in-app-notification-service:%VERSION% && docker push %DOCKER_USER%/linked-in-app-notification-service:latest" ; ^
  nt -d "%ROOT_DIR%post-service" --title "Post" cmd /k "mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-post-service:%VERSION% . && docker tag %DOCKER_USER%/linked-in-app-post-service:%VERSION% %DOCKER_USER%/linked-in-app-post-service:latest && docker push %DOCKER_USER%/linked-in-app-post-service:%VERSION% && docker push %DOCKER_USER%/linked-in-app-post-service:latest" ; ^
  nt -d "%ROOT_DIR%user-service" --title "User" cmd /k "mvn clean package -DskipTests && docker build -t %DOCKER_USER%/linked-in-app-user-service:%VERSION% . && docker tag %DOCKER_USER%/linked-in-app-user-service:%VERSION% %DOCKER_USER%/linked-in-app-user-service:latest && docker push %DOCKER_USER%/linked-in-app-user-service:%VERSION% && docker push %DOCKER_USER%/linked-in-app-user-service:latest"

echo All Builds and push were successfully opened in new tabs . You can now close this particular tabs. Do not close others tab until fully completed
pause