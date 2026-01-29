## Forcefully remove the git history and start fresh

```
    Remove-Item -Recurse -Force ".git"
    
```

## To push code into gitHub repository for the first time

```
    git init
    git add .
    git commit -m "Part 1"
    git branch -M main
    git remote add origin https://github.com/manishrnl/Spring-Linked-In.git
    git push -u origin main
```

## To start postgres sql in termux

```
    pg_ctl -D $PREFIX/var/lib/postgresql start
    
```

## To kill task like port 8080 , tun following command in cmd as an administration

```
    for /f "tokens=5" %a in ('netstat -aon ^| findstr :8080 ^| findstr LISTENING') do taskkill /PID %a /F
    for /f "tokens=5" %a in ('netstat -aon ^| findstr :8081 ^| findstr LISTENING') do taskkill /PID %a /F
    for /f "tokens=5" %a in ('netstat -aon ^| findstr :8082 ^| findstr LISTENING') do taskkill /PID %a /F
    for /f "tokens=5" %a in ('netstat -aon ^| findstr :8888 ^| findstr LISTENING') do taskkill /PID %a /F
    for /f "tokens=5" %a in ('netstat -aon ^| findstr :8761 ^| findstr LISTENING') do taskkill /PID %a /F
```

## To kill all Application that is being used by Java on termux

```
    pkill -9 java
```

## To verify if 8089 port was killed in termux

```
ps -A | grep java

```