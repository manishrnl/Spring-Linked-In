### Run the bat file "Build and push to dockerHub.bat"  present inside root directory via cmd and waith for its build to finish

### Once image is build and is uploaded to Docker Hub , run the below command inside the terminal

```

docker-compose down --remove-orphans
docker volume prune -f
cls
docker-compose up

```