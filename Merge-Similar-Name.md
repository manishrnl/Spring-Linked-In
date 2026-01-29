## To use Notepad as an Editor inseatd of editing inside terminal

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