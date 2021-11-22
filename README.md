# RandomPasswordGenerator

Working on Linux systems, this script generates a random password and encrypt it with gpg. 
if you want to use the program more than once, you will have to decrypt the file with your passwords and export its contents to another file. Then, when running the app, enter the file path as the path to the decrypted file. This ensures that the next password will not be the same as the previous one(s)
  
  How do i decrypt the file??

Open a terminal, and use the following command:
  gpg --decrypt /path/to/file.txt.asc> /file/uncriptted/fileuncriptted.txt


It's all!
