@echo off
set "ROOT_PATH=%~dp0"
set "DOCKER_USER=manishrnl"
set "VERSION=v2.1.0"

echo Starting Docker Build and Push process...
echo Ensure you are logged in via 'docker login'

:: Discovery Service
wt --title "Discovery" -d "%ROOT_PATH%Discovery-Service" cmd /k "docker build -t %DOCKER_USER%/discovery-service:%VERSION% -t %DOCKER_USER%/discovery-service:latest . && docker push %DOCKER_USER%/discovery-service --all-tags" ^
 ; nt --title "Gateway" -d "%ROOT_PATH%Api-Gateway" cmd /k "docker build -t %DOCKER_USER%/api-gateway:%VERSION% -t %DOCKER_USER%/api-gateway:latest . && docker push %DOCKER_USER%/api-gateway --all-tags" ^
 ; nt --title "User" -d "%ROOT_PATH%User-Service" cmd /k "docker build -t %DOCKER_USER%/user-service:%VERSION% -t %DOCKER_USER%/user-service:latest . && docker push %DOCKER_USER%/user-service --all-tags" ^
 ; nt --title "Post" -d "%ROOT_PATH%Post-Service" cmd /k "docker build -t %DOCKER_USER%/post-service:%VERSION% -t %DOCKER_USER%/post-service:latest . && docker push %DOCKER_USER%/post-service --all-tags" ^
 ; nt --title "Notification" -d "%ROOT_PATH%notification-service" cmd /k "docker build -t %DOCKER_USER%/notification-service:%VERSION% -t %DOCKER_USER%/notification-service:latest . && docker push %DOCKER_USER%/notification-service --all-tags" ^
 ; nt --title "Connection" -d "%ROOT_PATH%Connection-Service" cmd /k "docker build -t %DOCKER_USER%/connection-service:%VERSION% -t %DOCKER_USER%/connection-service:latest . && docker push %DOCKER_USER%/connection-service --all-tags"

pause