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

---
---
---
---

## To use Notepad as an Editor instead of editing inside terminal

```
    git config --global core.editor "notepad"
```

## Now follow below steps to merge commits with same name into one commit

1. First make sure you have committed all changes and pushed to remote repository
2. paste the below command into terminal inside the root folder only

```
    git rebase -i --root
```

3. Now change the text in notepad for same name from "Pick" to s .
   ex ->

```
pick a1b2c3d Part 1
      pick e4f5g6h Part 1
      pick e4f5g6h Part 1
      pick e4f5g6h Part 1
      pick i7j8k9l Part 2
      pick i7j8k9l Part 2
      pick i7j8k9l Part 2
      pick i7j8k9l Part 2
      pick m0n1o2p Part 2
```

- After changing ->

```
      pick a1b2c3d Part 1
      s e4f5g6h Part 1
      s e4f5g6h Part 1
      s e4f5g6h Part 1
      pick i7j8k9l Part 2
      s i7j8k9l Part 2
      s i7j8k9l Part 2
      s i7j8k9l Part 2
      s m0n1o2p Part 2
```

4. Now Save the Notepad Edits
5. now run below command

```
    git push origin main --force-with-lease to commit changes
```

6. All commits with same name is merged into one
7. Finally push all the code to gitHub by running below commands

```
    git add .
    git commit -m "Merging Similar Name"
    git push origin main
```